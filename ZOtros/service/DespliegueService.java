package com.tfg.backend.CRUD.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.backend.CRUD.repository.DespliegueRepositorio;
import com.tfg.backend.global.exceptions.AttributeException;
import com.tfg.backend.global.exceptions.ResourceNotFoundException;
import com.tfg.backend.CRUD.dto.DespliegueDto;
import com.tfg.backend.CRUD.entity.Despliegue;

@Service
public class DespliegueService {

    @Autowired
    private DespliegueRepositorio DespliegueRepositorio;

    public List<Despliegue> getAllDespliegues() {
        return DespliegueRepositorio.findAll();
    }

    public Despliegue getDespliegueById(int id) throws ResourceNotFoundException {
        return DespliegueRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Despliegue no encontrado."));
    }

    public Despliegue getDespliegueByName(String name) throws ResourceNotFoundException {
        return DespliegueRepositorio.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Despliegue no encontrado con el nombre " + name + "."));
    }

    public Despliegue getDespliegueByGroup(String group) throws ResourceNotFoundException {
        return DespliegueRepositorio.findByGroup(group).orElseThrow(() -> new ResourceNotFoundException("Despliegue no encontrado con el grupo " + group + "."));
    }

    public Despliegue addDespliegue(DespliegueDto dto) throws AttributeException{
        if(DespliegueRepositorio.existsByName(dto.getName())) {
            throw new AttributeException("El despliegue con ese nombre ya existe.");
        }

        int id = autoIncrementId();
        Despliegue Despliegue = new Despliegue(id,dto.getGroup(), dto.getName(), dto.getHostId(), dto.getDescription(), dto.getProjectId(), dto.getDevState(), dto.getDeployType(), dto.getDeployDate(), dto.getDeployedBy(), dto.getDeployStatus(), dto.getDeployLog());
        return DespliegueRepositorio.save(Despliegue);
    }

    public Despliegue updateDespliegue(int id, DespliegueDto dto) throws ResourceNotFoundException, AttributeException {
        Despliegue Despliegue = DespliegueRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Despliegue no encontrado."));;
        /// Si el nombre del Despliegue es diferente al que se quiere cambiar y ya existe un Despliegue con ese id
        if(DespliegueRepositorio.existsByName(dto.getName()) && DespliegueRepositorio.findByName(dto.getName()).get().getId() != id){
            throw new AttributeException("El Despliegue con ese nombre ya existe.");
        }

        //TO DO comprobar que nuevo host existe
        Despliegue.setGroup(dto.getGroup());
        Despliegue.setName(dto.getName());
        Despliegue.setHostId(dto.getHostId());
        Despliegue.setDescription(dto.getDescription());
        Despliegue.setProjectId(dto.getProjectId());
        Despliegue.setDevState(dto.getDevState());
        Despliegue.setDeployType(dto.getDeployType());
        Despliegue.setDeployDate(dto.getDeployDate());
        Despliegue.setDeployedBy(dto.getDeployedBy());
        Despliegue.setDeployStatus(dto.getDeployStatus());
        Despliegue.setDeployLog(dto.getDeployLog());
        
        return DespliegueRepositorio.save(Despliegue);
    }

    public void deleteDespliegue(int id) throws ResourceNotFoundException {
        Despliegue Despliegue = DespliegueRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Despliegue no encontrado."));;
        DespliegueRepositorio.delete(Despliegue);
    }

    //private methods
    private int autoIncrementId() {
        List<Despliegue> Despliegues = DespliegueRepositorio.findAll();
        return Despliegues.isEmpty() ? 1 : Despliegues.stream().max(Comparator.comparing(Despliegue::getId)).get().getId() + 1;
    }
}