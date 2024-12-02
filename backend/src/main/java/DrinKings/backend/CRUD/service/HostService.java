package com.tfg.backend.CRUD.service;

import java.util.Comparator;
import java.util.List;

import com.tfg.backend.CRUD.dto.ProyectoDto;
import com.tfg.backend.CRUD.entity.Host;
import com.tfg.backend.CRUD.dto.HostDto;
import com.tfg.backend.CRUD.entity.Proyecto;
import com.tfg.backend.CRUD.repository.HostRepositorio;
import com.tfg.backend.global.exceptions.AttributeException;
import com.tfg.backend.global.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostService {

    @Autowired
    private HostRepositorio hostRepositorio;

    public List<Host> getAllHosts() { return hostRepositorio.findAll(); }

    public Host getHostById(int id) throws ResourceNotFoundException {
        return hostRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Host no encontrado."));
    }

    public Host getHostByName(String name) throws ResourceNotFoundException {
        return hostRepositorio.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Host no encontrado con el nombre " + name + "."));
    }

    public Host getHostByIp(String ip) throws ResourceNotFoundException {
        return hostRepositorio.findByIp(ip).orElseThrow(() -> new ResourceNotFoundException("Host no encontrado con la ip " + ip + "."));
    }

    public Host getHostByUser(String user) throws ResourceNotFoundException {
        return hostRepositorio.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Host no encontrado con el usuario " + user + "."));
    }

    public Host addHost(HostDto dto) throws AttributeException {
        if(hostRepositorio.existsByName(dto.getName())) {
            throw new AttributeException("El host con ese nombre ya existe.");
        }
        int id = autoIncrementId();
        Host host = new Host(id, dto.getName(), dto.getIp(), dto.getUser());
        return hostRepositorio.save(host);
    }

    public Host updateHost(int id, HostDto dto) throws ResourceNotFoundException, AttributeException {
        Host host = hostRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Host no encontrado."));;
        /// Si el nombre del host es diferente al que se quiere cambiar y ya existe un host con ese id
        if(hostRepositorio.existsByName(dto.getName()) && hostRepositorio.findByName(dto.getName()).get().getId() != id){
            throw new AttributeException("El host con ese nombre ya existe.");
        }
        host.setName(dto.getName());
        host.setIp(dto.getIp());
        host.setUser(dto.getUser());
        return hostRepositorio.save(host);
    }

    public void deleteHost(int id) throws ResourceNotFoundException {
        Host host = hostRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Host no encontrado."));;
        hostRepositorio.delete(host);
    }

    //private methods
    private int autoIncrementId() {
        List<Host> hosts = hostRepositorio.findAll();
        return hosts.isEmpty() ? 1 : hosts.stream().max(Comparator.comparing(Host::getId)).get().getId() + 1;
    }
}
