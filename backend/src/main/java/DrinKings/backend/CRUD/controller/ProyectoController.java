package com.tfg.backend.CRUD.controller;

import com.tfg.backend.CRUD.dto.ProyectoDto;
import com.tfg.backend.CRUD.entity.Proyecto;
import com.tfg.backend.CRUD.service.*;
import com.tfg.backend.global.dto.MessageDto;
import com.tfg.backend.global.exceptions.AttributeException;
import com.tfg.backend.global.exceptions.ResourceNotFoundException;

import java.util.List;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/proyecto")
public class ProyectoController {

    @Autowired
    ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<Proyecto>> getAllProyectos() {
        return ResponseEntity.ok(proyectoService.getAllProyectos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> get(@PathVariable("id") int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(proyectoService.getProyectoById(id));
    }

    @PostMapping
    public ResponseEntity<MessageDto> addProyecto (@RequestBody @Valid ProyectoDto dto) throws AttributeException{

        Proyecto proyecto = proyectoService.addProyecto(dto);
        String message = "Proyecto " + proyecto.getName() + " creado correctamente";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateProyecto (@PathVariable("id") int id, @Valid @RequestBody ProyectoDto dto) throws ResourceNotFoundException, AttributeException {
        Proyecto proyecto = proyectoService.updateProyecto(id, dto);
        String message = "Proyecto " + proyecto.getName() + " actualizado correctamente";        
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        proyectoService.deleteProyecto(id);
        String message = "Proyecto eliminado correctamente";  
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Proyecto> getByName(@PathVariable("name") String name) throws ResourceNotFoundException {
        return ResponseEntity.ok(proyectoService.getProyectoByName(name));
    }

    // @GetMapping("/group/{group}")
    // public ResponseEntity<Proyecto> getByGroup(@PathVariable("group") String group) throws ResourceNotFoundException {
    //     return ResponseEntity.ok(proyectoService.getProyectoByGroup(group));
    // }
}