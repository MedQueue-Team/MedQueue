package com.example.mediqueue.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mediqueue.data.local.entities.PatientEntity;

import java.util.List;

@Dao
public interface PatientDao {
    
    @Query("SELECT * FROM patients WHERE patient_id = :patientId")
    LiveData<PatientEntity> getPatient(String patientId);
    
    @Query("SELECT * FROM patients")
    LiveData<List<PatientEntity>> getAllPatients();
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPatient(PatientEntity patient);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPatients(List<PatientEntity> patients);
    
    @Delete
    void deletePatient(PatientEntity patient);
    
    @Query("DELETE FROM patients")
    void deleteAllPatients();

    @Query("SELECT * FROM patients WHERE is_synced = 0")
    List<PatientEntity> getUnsyncedPatients();

    @Query("UPDATE patients SET is_synced = 1 WHERE patient_id = :patientId")
    void markAsSynced(String patientId);
}
