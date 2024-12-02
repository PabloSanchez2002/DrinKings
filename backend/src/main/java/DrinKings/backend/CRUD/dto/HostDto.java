package com.tfg.backend.CRUD.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HostDto {
    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "IP is mandatory")
    @NotNull(message = "IP is mandatory")
    private String ip;

    @NotBlank(message = "User is mandatory")
    @NotNull(message = "User is mandatory")
    private String user = "delonia";

    public HostDto(){
    }

    public HostDto( String name, String ip, String user){
        this.name = name;
        this.ip = ip;
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
}

