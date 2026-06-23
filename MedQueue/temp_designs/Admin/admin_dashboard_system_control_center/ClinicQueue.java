package com.example.mediqueue.ui.admin;

public class ClinicQueue {
    public enum Status {
        ACTIVE,
        SUSPENDED,
        CLOSED
    }

    private final String id;
    private final String name;
    private final String doctorName;
    private final String prefix;
    private int currentToken;
    private int queueCount;
    private Status status;

    public ClinicQueue(String id, String name, String doctorName, String prefix, int currentToken, int queueCount, Status status) {
        this.id = id;
        this.name = name;
        this.doctorName = doctorName;
        this.prefix = prefix;
        this.currentToken = currentToken;
        this.queueCount = queueCount;
        this.status = status;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDoctorName() { return doctorName; }
    public String getPrefix() { return prefix; }
    
    public int getCurrentToken() { return currentToken; }
    public void setCurrentToken(int currentToken) { this.currentToken = currentToken; }
    
    public int getQueueCount() { return queueCount; }
    public void setQueueCount(int queueCount) { this.queueCount = queueCount; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getCurrentTokenDisplay() {
        if (status == Status.CLOSED || currentToken == 0) return "--";
        return prefix + "-" + String.format("%03d", currentToken);
    }

    public String getNextTokenDisplay() {
        if (status == Status.CLOSED || queueCount <= 0) return "--";
        return prefix + "-" + String.format("%03d", currentToken + 1);
    }
}
