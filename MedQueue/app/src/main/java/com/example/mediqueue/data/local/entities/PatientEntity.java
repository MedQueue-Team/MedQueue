package com.example.mediqueue.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "patients")
public class PatientEntity {
    
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    @ColumnInfo(name = "patient_id")
    public String patientId;
    
    @ColumnInfo(name = "full_name")
    public String fullName;
    
    @ColumnInfo(name = "email")
    public String email;
    
    @ColumnInfo(name = "date_of_birth")
    public String dateOfBirth;
    
    @ColumnInfo(name = "phone_number")
    public String phoneNumber;
    
    @ColumnInfo(name = "last_updated")
    public long lastUpdated;

    @ColumnInfo(name = "is_synced")
    public boolean isSynced = true;
}
