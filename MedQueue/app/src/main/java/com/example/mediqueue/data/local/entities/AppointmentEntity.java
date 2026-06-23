package com.example.mediqueue.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "appointments")
public class AppointmentEntity {
    
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    @ColumnInfo(name = "appointment_id")
    public String appointmentId;
    
    @ColumnInfo(name = "patient_id")
    public String patientId;
    
    @ColumnInfo(name = "doctor_id")
    public String doctorId;
    
    @ColumnInfo(name = "doctor_name")
    public String doctorName;
    
    @ColumnInfo(name = "department")
    public String department;
    
    @ColumnInfo(name = "appointment_date")
    public String appointmentDate;
    
    @ColumnInfo(name = "appointment_time")
    public String appointmentTime;
    
    @ColumnInfo(name = "status")
    public String status; // UPCOMING, COMPLETED, CANCELLED
    
    @ColumnInfo(name = "last_updated")
    public long lastUpdated;

    @ColumnInfo(name = "is_synced")
    public boolean isSynced = true;

    public int getId() { return id; }
    public String getPatientName() { return "Patient #" + patientId; }
    public String getTime() { return appointmentTime; }
    public String getReason() { return ""; }
    public String getNotes() { return ""; }
}
