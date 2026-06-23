package com.example.mediqueue.ui.receptionist;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Model class for a patient in the triage queue.
 */
@Entity(tableName = "triage_queue")
public class TriagePatient {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String gender;
    private String age;
    private String arrivalTime;
    private String priority; // EMERGENCY, HIGH, MEDIUM, LOW
    private boolean isTriageComplete;
    private String status;

    // New fields required by triage assessment, patient registration, and PDF export
    private String primaryComplaint;
    private String nrcId;
    private String dob;
    private String phone;
    private String nextOfKinName;
    private String nextOfKinRelationship;
    private String nextOfKinPhone;
    private boolean assessed;
    private String bloodPressure;
    private String temperature;
    private String pulseRate;

    // Room constructor
    public TriagePatient(@NonNull String id, String name, String gender, String age, 
                         String arrivalTime, String priority, boolean isTriageComplete, String status) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.isTriageComplete = isTriageComplete;
        this.status = status;
    }

    // 7-parameter constructor for PatientRegistrationActivity and TriageRepository
    @androidx.room.Ignore
    public TriagePatient(String name, String id, String age, String gender, String priority, boolean isTriageComplete, String status) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.priority = priority;
        this.isTriageComplete = isTriageComplete;
        this.status = status;
        this.arrivalTime = "";
    }

    @androidx.room.Ignore
    public TriagePatient() {
        this.id = "";
    }

    // Getters and Setters
    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public boolean isTriageComplete() { return isTriageComplete; }
    public void setTriageComplete(boolean triageComplete) { isTriageComplete = triageComplete; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Getters and setters for new fields
    public String getPrimaryComplaint() { return primaryComplaint; }
    public void setPrimaryComplaint(String primaryComplaint) { this.primaryComplaint = primaryComplaint; }

    public String getNrcId() { return nrcId; }
    public void setNrcId(String nrcId) { this.nrcId = nrcId; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getNextOfKinName() { return nextOfKinName; }
    public void setNextOfKinName(String nextOfKinName) { this.nextOfKinName = nextOfKinName; }

    public String getNextOfKinRelationship() { return nextOfKinRelationship; }
    public void setNextOfKinRelationship(String nextOfKinRelationship) { this.nextOfKinRelationship = nextOfKinRelationship; }

    public String getNextOfKinPhone() { return nextOfKinPhone; }
    public void setNextOfKinPhone(String nextOfKinPhone) { this.nextOfKinPhone = nextOfKinPhone; }

    public boolean isAssessed() { return assessed; }
    public void setAssessed(boolean assessed) { this.assessed = assessed; }

    public String getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }

    public String getTemperature() { return temperature; }
    public void setTemperature(String temperature) { this.temperature = temperature; }

    public String getPulseRate() { return pulseRate; }
    public void setPulseRate(String pulseRate) { this.pulseRate = pulseRate; }

    // Helper getter for TriageAdapter
    public String getWaitTimeText() { return status; }
}
