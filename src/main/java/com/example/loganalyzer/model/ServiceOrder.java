package com.example.loganalyzer.model;

/**
 * Cette classe représente une commande de service (Service Order).
 */
public class ServiceOrder {
    private String id;
    private String name;
    private String status;

    // Constructeur par défaut requis par certains frameworks
    public ServiceOrder() {
    }

    // Constructeur utilisé par LogAnalysisService avec tous les champs
    public ServiceOrder(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    // Constructeur utilisé par LogAnalysisService avec deux champs
    public ServiceOrder(String id, String status) {
        this.id = id;
        this.status = status;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}