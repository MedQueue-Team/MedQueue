package com.example.mediqueue.data.repository;

import androidx.lifecycle.LiveData;

import com.example.mediqueue.api.ApiService;
import com.example.mediqueue.data.local.dao.PatientDao;
import com.example.mediqueue.data.local.entities.PatientEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PatientRepository extends BaseRepository {

    private final PatientDao patientDao;
    private final ApiService apiService;

    @Inject
    public PatientRepository(PatientDao patientDao, ApiService apiService) {
        this.patientDao = patientDao;
        this.apiService = apiService;
    }

    public LiveData<PatientEntity> getPatient(String patientId) {
        // Here you would typically fetch from API and cache in DB
        // For now, return the DB LiveData
        return patientDao.getPatient(patientId);
    }
    
    public LiveData<List<PatientEntity>> getAllPatients() {
        return patientDao.getAllPatients();
    }

    public List<PatientEntity> getUnsyncedPatients() {
        return patientDao.getUnsyncedPatients();
    }

    public void markAsSynced(String patientId) {
        patientDao.markAsSynced(patientId);
    }
}
