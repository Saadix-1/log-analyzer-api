package com.example.loganalyzer.model;

public class WorkOrder {
    private String id;
    private String name;
    private String status;
    private String rawLogTimestamp; // To store the raw timestamp string from the log line
    private String logLevel;        // To store the log level (INFO, ERROR, etc.)
    private String instanceName;
    private Long processingTimeMicros;

    public WorkOrder(String id, String status) {
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

    public Long getProcessingTimeMicros() {
        return processingTimeMicros;
    }

    public void setProcessingTimeMicros(Long processingTimeMicros) {
        this.processingTimeMicros = processingTimeMicros;
    }
}
