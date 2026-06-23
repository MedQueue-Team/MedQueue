package com.example.mediqueue.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mediqueue.ui.receptionist.TriagePatient;

import java.util.List;

@Dao
public interface TriageDao {

    @Query("SELECT * FROM triage_queue ORDER BY CASE priority " +
            "WHEN 'EMERGENCY' THEN 1 " +
            "WHEN 'HIGH' THEN 2 " +
            "WHEN 'MEDIUM' THEN 3 " +
            "WHEN 'LOW' THEN 4 ELSE 5 END")
    LiveData<List<TriagePatient>> getPriorityQueue();

    @Query("SELECT * FROM triage_queue")
    List<TriagePatient> getPriorityQueueRaw();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TriagePatient> patients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TriagePatient patient);

    @androidx.room.Update
    void update(TriagePatient patient);

    @Query("SELECT * FROM triage_queue WHERE id = :patientId LIMIT 1")
    LiveData<TriagePatient> getPatientById(String patientId);

    @Query("DELETE FROM triage_queue")
    void deleteAll();
}
