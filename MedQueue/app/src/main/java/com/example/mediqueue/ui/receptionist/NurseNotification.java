package com.example.mediqueue.ui.receptionist;

public class NurseNotification {

    public enum Type {
        TRIAGE_ALERT,    // Urgent: new case assigned requiring immediate triage
        QUEUE_UPDATE,    // Informational: patient queue position changes
        PATIENT_TRANSFER, // Action required: transfer request
        EMERGENCY,       // Critical: emergency alert
        SYSTEM           // System / admin message
    }

    private final String title;
    private final String message;
    private final String time;
    private final Type type;
    private final boolean isUrgent;
    private final String patientId;     // Optional – linked patient
    private final String primaryAction; // Optional – primary button label
    private final String secondaryAction; // Optional – secondary button label

    public NurseNotification(String title, String message, String time,
                             Type type, boolean isUrgent,
                             String patientId,
                             String primaryAction, String secondaryAction) {
        this.title = title;
        this.message = message;
        this.time = time;
        this.type = type;
        this.isUrgent = isUrgent;
        this.patientId = patientId;
        this.primaryAction = primaryAction;
        this.secondaryAction = secondaryAction;
    }

    // Convenience constructor (no actions / no patient)
    public NurseNotification(String title, String message, String time,
                             Type type, boolean isUrgent) {
        this(title, message, time, type, isUrgent, null, null, null);
    }

    public String getTitle()           { return title; }
    public String getMessage()         { return message; }
    public String getTime()            { return time; }
    public Type   getType()            { return type; }
    public boolean isUrgent()          { return isUrgent; }
    public String getPatientId()       { return patientId; }
    public String getPrimaryAction()   { return primaryAction; }
    public String getSecondaryAction() { return secondaryAction; }
    public boolean hasActions()        { return primaryAction != null; }
}
