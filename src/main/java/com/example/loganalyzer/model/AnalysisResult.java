package com.example.loganalyzer.model; // Corrected package

import java.util.ArrayList;
import java.util.List;

public class AnalysisResult {
    private String requestId;
    private String accountCaller = "Non trouvé / Par défaut";
    private String operationType = "Non trouvé / Par défaut";
    private List<ServiceOrder> serviceOrders;
    private List<ProductOrder> productOrders;
    private boolean rollbackDetected;
    private List<String> rollbackDetails;

    public AnalysisResult() {
        this.serviceOrders = new ArrayList<>();
        this.productOrders = new ArrayList<>();
        this.rollbackDetails = new ArrayList<>();
    }

    // Getters and Setters

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

    public List<ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }

    public void setServiceOrders(List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    public boolean isRollbackDetected() {
        return rollbackDetected;
    }

    public void setRollbackDetected(boolean rollbackDetected) {
        this.rollbackDetected = rollbackDetected;
    }

    public List<String> getRollbackDetails() {
        return rollbackDetails;
    }

    public void setRollbackDetails(List<String> rollbackDetails) {
        this.rollbackDetails = rollbackDetails;
    }
}