package com.tfg.backend.CRUD.controller;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.Valid;

import com.tfg.backend.BashCommandRunner;
import com.tfg.backend.CRUD.dto.DespliegueDto;
import com.tfg.backend.CRUD.entity.Despliegue;
import com.tfg.backend.CRUD.service.*;
import com.tfg.backend.global.exceptions.AttributeException;
import com.tfg.backend.global.exceptions.ResourceNotFoundException;
import com.tfg.backend.CRUD.service.DespliegueService;
import com.tfg.backend.CRUD.service.HostService;
import com.tfg.backend.CRUD.service.ProyectoService;
import com.tfg.backend.global.utils.Tuple;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/despliegue")
public class DespliegueController {

    @Autowired
    DespliegueService despliegueService;
    @Autowired
    HostService hostService;
    @Autowired
    ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<Despliegue>> getAllDespliegues() {
        return ResponseEntity.ok(despliegueService.getAllDespliegues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despliegue> get(@PathVariable("id") int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(despliegueService.getDespliegueById(id));
    }

    @PostMapping
    public ResponseEntity<Despliegue> addDespliegue (@Valid @RequestBody DespliegueDto dto) throws AttributeException {
        return ResponseEntity.ok(despliegueService.addDespliegue(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despliegue> updateDespliegue (@PathVariable("id") int id, @RequestBody DespliegueDto dto) throws ResourceNotFoundException, AttributeException {
        return ResponseEntity.ok(despliegueService.updateDespliegue(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Despliegue> delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        despliegueService.deleteDespliegue(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Despliegue> getByName(@PathVariable("name") String name) throws ResourceNotFoundException {
        return ResponseEntity.ok(despliegueService.getDespliegueByName(name));
    }

    @PostMapping("/deploy/{id}")
    public ResponseEntity deploy(@PathVariable("id") int id, @RequestBody String deployedBy) throws ResourceNotFoundException{
    
        try {
            Despliegue deploy = despliegueService.getDespliegueById(id);
            despliegueService.updateDespliegue(id, new DespliegueDto(deploy.getGroup(), deploy.getName(), deploy.getHostId(), deploy.getDescription(), deploy.getProjectId(), deploy.getDevState(), deploy.getDeployType(), deploy.getDeployDate(), deployedBy, "Deploying...", deploy.getDeployLog()));
            
            String hostIP = hostService.getHostById(deploy.getHostId()).getIp();
            String username = hostService.getHostById(deploy.getHostId()).getUser();
            String repo = proyectoService.getProyectoById(deploy.getProjectId()).getRepository();

            // Execute the deployment command
            Tuple<Integer, String> result =BashCommandRunner.startDeployment(hostIP, username, repo, deploy.getDeployType());
            
            int exitCode = result.getFirst();
            String output = result.getSecond();

            // Update the deployment log and state
            String newState = exitCode == 0 ? "Deployed" : "Failed";
            LocalDateTime deployDate = LocalDateTime.now();
            deploy = despliegueService.getDespliegueById(id);
            despliegueService.updateDespliegue(id, new DespliegueDto(deploy.getGroup(), deploy.getName(), deploy.getHostId(), deploy.getDescription(), deploy.getProjectId(), deploy.getDevState(), deploy.getDeployType(), deployDate, deploy.getDeployedBy(), newState, output));

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Deployment failed: " + e.getMessage());
        }

    }
}