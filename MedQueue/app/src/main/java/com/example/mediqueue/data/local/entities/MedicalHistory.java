package com.example.mediqueue.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medical_history")
public class MedicalHistory {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String date;
    private String title;
    private String status;
    private String location;
    private String provider;
    private int locationIconRes;
    private int providerIconRes;
    private boolean isSpecialist;
    private String primaryActionText;
    private boolean hasSecondaryAction;

    public MedicalHistory(String date, String title, String status, String location, String provider, 
                          int locationIconRes, int providerIconRes, boolean isSpecialist, 
                          String primaryActionText, boolean hasSecondaryAction) {
        this.date = date;
        this.title = title;
        this.status = status;
        this.location = location;
        this.provider = provider;
        this.locationIconRes = locationIconRes;
        this.providerIconRes = providerIconRes;
        this.isSpecialist = isSpecialist;
        this.primaryActionText = primaryActionText;
        this.hasSecondaryAction = hasSecondaryAction;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public int getLocationIconRes() { return locationIconRes; }
    public void setLocationIconRes(int locationIconRes) { this.locationIconRes = locationIconRes; }
    public int getProviderIconRes() { return providerIconRes; }
    public void setProviderIconRes(int providerIconRes) { this.providerIconRes = providerIconRes; }
    public boolean isSpecialist() { return isSpecialist; }
    public void setSpecialist(boolean specialist) { isSpecialist = specialist; }
    public String getPrimaryActionText() { return primaryActionText; }
    public void setPrimaryActionText(String primaryActionText) { this.primaryActionText = primaryActionText; }
    public boolean hasSecondaryAction() { return hasSecondaryAction; }
    public void setHasSecondaryAction(boolean hasSecondaryAction) { this.hasSecondaryAction = hasSecondaryAction; }

    public String getDiagnosis() { return title; }
    public String getDoctorName() { return provider; }
    public String getClinicName() { return location; }
}
