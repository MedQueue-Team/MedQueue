package com.example.mediqueue.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "consultations")
public class ConsultationEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String patientId;
    public String doctorId;
    public String symptoms;
    public String diagnosis;
    public String prescription;
    public String notes;
    public long timestamp;
    public boolean isSynced;

    public ConsultationEntity() {}

    public ConsultationEntity(String patientId, String doctorId, String symptoms, String diagnosis, String prescription, String notes) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.notes = notes;
        this.timestamp = System.currentTimeMillis();
        this.isSynced = false;
    }
}
