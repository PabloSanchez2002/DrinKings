package com.tfg.backend.CRUD.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.backend.CRUD.repository.ProyectoRepositorio;
import com.tfg.backend.global.exceptions.AttributeException;
import com.tfg.backend.global.exceptions.ResourceNotFoundException;
import com.tfg.backend.CRUD.dto.ProyectoDto;
import com.tfg.backend.CRUD.entity.Proyecto;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepositorio proyectoRepositorio;

    public List<Proyecto> getAllProyectos() {
        return proyectoRepositorio.findAll();
    }

    public Proyecto getProyectoById(int id) throws ResourceNotFoundException {
        return proyectoRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado."));
    }

    public Proyecto getProyectoByName(String name) throws ResourceNotFoundException {
        return proyectoRepositorio.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con el nombre " + name + "."));
    }

    public Proyecto addProyecto(ProyectoDto dto) throws AttributeException{
        if(proyectoRepositorio.existsByName(dto.getName())) {
            throw new AttributeException("El proyecto con ese nombre ya existe.");
        }
        int id = autoIncrementId();
        Proyecto proyecto = new Proyecto(id, dto.getName(), dto.getDescription(), dto.getRepository(), dto.getTypes());
        return proyectoRepositorio.save(proyecto);
    }

    public Proyecto updateProyecto(int id, ProyectoDto dto) throws ResourceNotFoundException, AttributeException {
        Proyecto proyecto = proyectoRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado."));;
        /// Si el nombre del proyecto es diferente al que se quiere cambiar y ya existe un proyecto con ese id
        if(proyectoRepositorio.existsByName(dto.getName()) && proyectoRepositorio.findByName(dto.getName()).get().getId() != id){
            throw new AttributeException("El proyecto con ese nombre ya existe.");
        }
        proyecto.setName(dto.getName());
        proyecto.setDescription(dto.getDescription());        
        proyecto.setRepository(dto.getRepository());
        proyecto.setTypes(dto.getTypes());
        
        return proyectoRepositorio.save(proyecto);
    }

    public void deleteProyecto(int id) throws ResourceNotFoundException {
        Proyecto proyecto = proyectoRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado."));;
        proyectoRepositorio.delete(proyecto);
    }

    //private methods
    private int autoIncrementId() {
        List<Proyecto> proyectos = proyectoRepositorio.findAll();
        return proyectos.isEmpty() ? 1 : proyectos.stream().max(Comparator.comparing(Proyecto::getId)).get().getId() + 1;
    }
}