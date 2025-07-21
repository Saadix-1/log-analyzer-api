package com.example.loganalyzer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class LogAnalysisResult {
    private String requestId;
    private String accountCaller;
    private String operationType;
    // Changé en List<String> pour stocker les détails du rollback (chaque ligne de log pertinente)
    private List<String> rollbackDetails = new ArrayList<>();

    private List<ServiceOrder> serviceOrders = new ArrayList<>();
    private List<ProductOrder> productOrders = new ArrayList<>();
    // Cette map n'est plus strictement nécessaire si les WOs sont imbriqués sous les POs
    // Mais on peut la garder pour une liste de tous les WOs si besoin
    private Map<String, List<WorkOrder>> workOrders = new HashMap<>();


    public LogAnalysisResult(String requestId) {
        this.requestId = requestId;
    }

    // --- Getters et Setters ---

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAccountCaller() {
        return accountCaller;
    }

    public void setAccountCaller(String accountCaller) {
        this.accountCaller = accountCaller;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public List<String> getRollbackDetails() {
        return rollbackDetails;
    }

    public void setRollbackDetails(List<String> rollbackDetails) {
        this.rollbackDetails = rollbackDetails;
    }

    public void addRollbackDetail(String detail) { // Nouvelle méthode pour ajouter un détail de rollback
        this.rollbackDetails.add(detail);
    }

    // Permet de vérifier facilement s'il y a eu un rollback
    public boolean isRollbackDetected() {
        return !rollbackDetails.isEmpty();
    }


    public List<ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }

    public void setServiceOrders(List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    public void addServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrders.add(serviceOrder);
    }

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    public void addProductOrder(ProductOrder productOrder) {
        this.productOrders.add(productOrder);
    }

    public Map<String, List<WorkOrder>> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(Map<String, List<WorkOrder>> workOrders) {
        this.workOrders = workOrders;
    }
}
