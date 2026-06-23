package com.example.mediqueue.ui.receptionist;

public class QueuePatient {
    private String id;
    private String name;
    private String priority; // EMERGENCY, HIGH, MEDIUM, ROUTINE
    private String waitTime;
    private String status; // WAITING, IN_CONSULTATION
    private String chiefComplaint;

    public QueuePatient(String id, String name, String priority, String waitTime, String status, String chiefComplaint) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.waitTime = waitTime;
        this.status = status;
        this.chiefComplaint = chiefComplaint;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPriority() { return priority; }
    public String getWaitTime() { return waitTime; }
    public String getStatus() { return status; }
    public String getChiefComplaint() { return chiefComplaint; }
    
    public String getQueueNumber() { return id; }
    public String getDepartment() { return "General"; } // Dummy for UI
    public String getRegistrationTime() { return "10:00 AM"; } // Dummy for UI
}
