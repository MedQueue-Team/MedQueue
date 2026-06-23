package com.example.mediqueue.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "triage_queue")
public class TriagePatient {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String patientId;
    public String symptoms;
    public String priority;
    public String status;
}
