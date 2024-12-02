package com.tfg.backend.CRUD.controller;

import javax.annotation.processing.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;
import com.tfg.backend.CRUD.dto.HostDto;
import com.tfg.backend.CRUD.dto.NewHostDto;
import com.tfg.backend.CRUD.entity.Host;
import com.tfg.backend.CRUD.service.HostService;
import com.tfg.backend.global.exceptions.AttributeException;
import com.tfg.backend.global.exceptions.ResourceNotFoundException;
import com.tfg.backend.BashCommandRunner;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/host")
public class HostController {
    
    @Autowired
    HostService hostService;

    @GetMapping
    public ResponseEntity<List<Host>> getAllHosts() {
        return ResponseEntity.ok(hostService.getAllHosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Host> get(@PathVariable("id") int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(hostService.getHostById(id));
    }

    @PostMapping
    public ResponseEntity<Host> addHost (@Valid @RequestBody NewHostDto newDto) throws AttributeException {
        int status = 0;
        
        // Check if the host already exists by name
        HostDto dto = new HostDto(newDto.getName(), newDto.getIp(), newDto.getUser());
        try {
            if (hostService.getHostByName(dto.getName()) != null) {
                throw new AttributeException("El host con ese nombre ya existe.");
            }
        } catch (ResourceNotFoundException e) {}

        // Now we start checking for available connection
        try {
            status = BashCommandRunner.runCommand("check_password", newDto.getIp(), newDto.getUser(), newDto.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (status != 0) {
            return new ResponseEntity("No se ha podido establecer la conexión con el host", HttpStatus.BAD_REQUEST);
        }else {

            // Now we start setting up the server
            try {
                status = BashCommandRunner.runCommand("setup_server", newDto.getIp(), newDto.getUser(), newDto.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (status != 0) {
                return new ResponseEntity("No se ha podido configurar correctamente el host", HttpStatus.BAD_REQUEST);
            } else {

                // Now we add the host to the database
                return ResponseEntity.ok(hostService.addHost(dto));
            }
        }
    }

    @PostMapping("/force")
    public ResponseEntity<Host> addHostForce (@Valid @RequestBody HostDto dto) throws AttributeException {
        return ResponseEntity.ok(hostService.addHost(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Host> updateHost (@PathVariable("id") int id, @RequestBody HostDto dto) throws ResourceNotFoundException, AttributeException {
        return ResponseEntity.ok(hostService.updateHost(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Host> delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        hostService.deleteHost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Host> getByName(@PathVariable("name") String name) throws ResourceNotFoundException {
        return ResponseEntity.ok(hostService.getHostByName(name));
    }

    @GetMapping("/ip/{ip}")
    public ResponseEntity<Host> getByIp(@PathVariable("ip") String ip) throws ResourceNotFoundException {
        return ResponseEntity.ok(hostService.getHostByIp(ip));
    }

    @GetMapping("/user/{user}")
    public ResponseEntity<Host> getByUser(@PathVariable("user") String user) throws ResourceNotFoundException {
        return ResponseEntity.ok(hostService.getHostByUser(user));
    }

    // @GetMapping("/test/{id}")
    // public ResponseEntity<String> testHost(@PathVariable("id") int id) throws ResourceNotFoundException {
        
    // }
}
