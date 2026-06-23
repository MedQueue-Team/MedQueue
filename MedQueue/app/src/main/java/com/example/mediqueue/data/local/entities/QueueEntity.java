package com.example.mediqueue.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "queue_status")
public class QueueEntity {
    
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    @ColumnInfo(name = "queue_id")
    public String queueId;
    
    @ColumnInfo(name = "patient_id")
    public String patientId;
    
    @ColumnInfo(name = "department")
    public String department;
    
    @ColumnInfo(name = "position")
    public int position;
    
    @ColumnInfo(name = "estimated_wait_time_mins")
    public int estimatedWaitTimeMins;
    
    @ColumnInfo(name = "status")
    public String status; // WAITING, IN_PROGRESS, COMPLETED
    
    @ColumnInfo(name = "last_updated")
    public long lastUpdated;

    @ColumnInfo(name = "is_synced")
    public boolean isSynced = true;

    public String getName() { return "Patient #" + patientId; }
    public String getQueueNumber() { return String.valueOf(position); }
    public String getDepartment() { return department; }
    public String getRegistrationTime() { return ""; }
    public int getWaitTime() { return estimatedWaitTimeMins; }
    public String getPriority() { return status; }
}
