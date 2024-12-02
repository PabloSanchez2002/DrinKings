package com.tfg.backend.CRUD.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NewHostDto {
    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "IP is mandatory")
    @NotNull(message = "IP is mandatory")
    private String ip;

    @NotBlank(message = "User is mandatory")
    @NotNull(message = "User is mandatory")
    private String user = "delonia";

    @NotBlank(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    private String password = "delonia";


    public NewHostDto(){
    }

    public NewHostDto( String name, String ip, String user, String password){
        this.name = name;
        this.ip = ip;
        this.user = user;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

