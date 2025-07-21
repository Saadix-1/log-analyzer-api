package com.example.loganalyzer.model;

import java.util.ArrayList;
import java.util.List;

public class ProductOrder {
    private String id;
    private String name;
    private String status;
    private List<WorkOrder> workOrders;

    public ProductOrder(String id, String status) {
        this.id = id;
        this.status = status;
        this.workOrders = new ArrayList<>();
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

    public List<WorkOrder> getWorkOrders() {
        return workOrders;
    }

    public void addWorkOrder(WorkOrder workOrder) {
        this.workOrders.add(workOrder);
    }
}
