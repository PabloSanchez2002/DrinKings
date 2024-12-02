package com.tfg.backend.CRUD.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;

public class ProyectoDto {

    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    private String name;
    
    private String description;

    @NotBlank(message = "Repository is mandatory")
    @NotNull(message = "Repository is mandatory")
    private String repository;

    @NotNull(message = "At least one type is mandatory")
    @Size(min = 1, message = "At least one type is mandatory")
    private List< @NotBlank(message = "Type can't be empty") String> types;

    public ProyectoDto() {
    }

    public ProyectoDto(String name, String description, List<String> types) {
        this.name = name;
        this.description = description;
        this.types = types;
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