package com.example.loganalyzer.model;

/**
 * Cette classe représente une commande de travail (Work Order).
 */
public class WorkOrder {
    private String id;
    private String name;
    private String status;
    private String rawLogTimestamp;
    private String logLevel;
    private String instanceName;
    private long processingTimeMicros;

    // Constructeur par défaut
    public WorkOrder() {
    }

    // Constructeur complet
    public WorkOrder(String id, String name, String status, String rawLogTimestamp, String logLevel, String instanceName, long processingTimeMicros) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.rawLogTimestamp = rawLogTimestamp;
        this.logLevel = logLevel;
        this.instanceName = instanceName;
        this.processingTimeMicros = processingTimeMicros;
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

    public String getRawLogTimestamp() {
        return rawLogTimestamp;
    }

    public void setRawLogTimestamp(String rawLogTimestamp) {
        this.rawLogTimestamp = rawLogTimestamp;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public long getProcessingTimeMicros() {
        return processingTimeMicros;
    }

    public void setProcessingTimeMicros(long processingTimeMicros) {
        this.processingTimeMicros = processingTimeMicros;
    }
}