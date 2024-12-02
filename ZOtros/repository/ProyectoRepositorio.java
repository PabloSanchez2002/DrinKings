package com.tfg.backend.CRUD.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.tfg.backend.CRUD.entity.Proyecto;
import java.util.Optional;

@Repository
public interface ProyectoRepositorio extends MongoRepository<Proyecto, Integer> {
    boolean existsByName(String name);
    Optional<Proyecto> findByName(String name);
}
