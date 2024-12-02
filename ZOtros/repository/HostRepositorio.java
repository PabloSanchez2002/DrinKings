package com.tfg.backend.CRUD.repository;

import com.tfg.backend.CRUD.entity.Host;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HostRepositorio extends MongoRepository<Host, Integer> {
    boolean existsByName(String name);
    Optional <Host> findByName(String name);
    Optional <Host> findByUser(String user);
    Optional <Host> findByIp(String ip);
}
