package com.tfg.backend.CRUD.entity;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "despliegue")
public class Despliegue {
    @Id
    private int id;
    private String group;
    private String name;
    private int hostId;
    private String description;
    private int projectId;
    private String devState;
    private String deployType;
    private LocalDateTime deployDate;
    private String deployedBy;
    private String deployStatus;
    private String deployLog;

    public Despliegue() {
    }

    public Despliegue(int id, String group, String name, int hostId, String description, int projectId, String devState, String deployType, LocalDateTime deployDate, String deployedBy, String deployStatus, String deployLog) {
        this.id = id;
        this.group = group;
        this.name = name;
        this.hostId = hostId;
        this.description = description;
        this.projectId = projectId;
        this.devState = devState;
        this.deployType = deployType;
        this.deployDate = deployDate;
        this.deployedBy = deployedBy;
        this.deployStatus = deployStatus;
        this.deployLog = deployLog;

    }

    public int getId() {
        return id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getDevState() {
        return devState;
    }

    public void setDevState(String devState) {
        this.devState = devState;
    }

    public String getDeployType() {
        return deployType;
    }

    public void setDeployType(String deployType) {
        this.deployType = deployType;
    }
    
    public LocalDateTime getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(LocalDateTime deployDate) {
        this.deployDate = deployDate;
    }

    public String getDeployedBy() {
        return deployedBy;
    }

    public void setDeployedBy(String deployedBy) {
        this.deployedBy = deployedBy;
    }

    public String getDeployStatus() {
        return deployStatus;
    }

    public void setDeployStatus(String deployStatus) {
        this.deployStatus = deployStatus;
    }   

    public String getDeployLog() {
        return deployLog;
    }

    public void setDeployLog(String deployLog) {
        this.deployLog = deployLog;
    }

}