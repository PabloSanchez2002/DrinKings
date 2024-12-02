package com.tfg.backend.CRUD.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hosts")
public class Host {
    @Id
    private int id;
    private String name;
    private String ip;
    private String user = "delonia";

    public Host(){
    }

    public Host(int id, String name, String ip, String user){
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
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
