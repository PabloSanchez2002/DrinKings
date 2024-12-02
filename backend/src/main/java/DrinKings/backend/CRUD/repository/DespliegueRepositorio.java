package com.tfg.backend.CRUD.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.tfg.backend.CRUD.entity.Despliegue;
import java.util.Optional;

@Repository
public interface DespliegueRepositorio extends MongoRepository<Despliegue, Integer> {
    boolean existsByName(String name);
    Optional<Despliegue> findByName(String name);
    Optional<Despliegue> findByGroup(String group);
    Optional<Despliegue> findByHostId(String hostId);
    
}