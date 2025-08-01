package com.example.loganalyzer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente une commande de produit (Product Order).
 */
public class ProductOrder {
    private String id;
    private String name;
    private String status;
    private List<WorkOrder> workOrders = new ArrayList<>();

    // Constructeur par défaut
    public ProductOrder() {
    }

    // Constructeur complet
    public ProductOrder(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    // Getters et Setters
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

    public List<WorkOrder> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }
}