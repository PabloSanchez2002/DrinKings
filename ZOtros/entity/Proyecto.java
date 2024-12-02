package com.tfg.backend.CRUD.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "proyectos")
public class Proyecto {
    @Id
    private int id;
    private String name;
    private String description;
    private String repository;
    private List<String> types;

    public Proyecto() {
    }
    
    public Proyecto(int id, String name, String description, String repository, List<String> types) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.repository = repository;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}