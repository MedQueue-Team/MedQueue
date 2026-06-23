package com.example.mediqueue.ui.receptionist;

/**
 * Model class for a medical encounter in the patient history timeline.
 */
public class MedicalEncounter {
    private String dateTime;
    private String title;
    private String facility;
    private String provider;
    private String summary;
    private String status; // "Ongoing", "Completed"

    public MedicalEncounter(String dateTime, String title, String facility,
                            String provider, String summary, String status) {
        this.dateTime = dateTime;
        this.title = title;
        this.facility = facility;
        this.provider = provider;
        this.summary = summary;
        this.status = status;
    }

    public String getDateTime() { return dateTime; }
    public String getTitle() { return title; }
    public String getFacility() { return facility; }
    public String getProvider() { return provider; }
    public String getSummary() { return summary; }
    public String getStatus() { return status; }
    public boolean isOngoing() { return "Ongoing".equals(status); }
}
