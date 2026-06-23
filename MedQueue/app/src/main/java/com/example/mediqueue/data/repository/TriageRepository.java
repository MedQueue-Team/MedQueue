package com.example.mediqueue.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mediqueue.data.local.AppDatabase;
import com.example.mediqueue.data.local.TriageDao;
import com.example.mediqueue.ui.receptionist.TriagePatient;

import java.util.ArrayList;
import java.util.List;

public class TriageRepository {

    private final TriageDao triageDao;
    private final LiveData<List<TriagePatient>> triageQueue;

    @javax.inject.Inject
    public TriageRepository(com.example.mediqueue.data.local.TriageDao triageDao) {
        this.triageDao = triageDao;
        this.triageQueue = triageDao.getPriorityQueue();
    }

    public LiveData<List<TriagePatient>> getTriageQueue() {
        return triageQueue;
    }

    public LiveData<TriagePatient> getPatientById(String patientId) {
        return triageDao.getPatientById(patientId);
    }

    public void updatePatient(TriagePatient patient) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            triageDao.update(patient);
        });
    }

    public void addPatient(TriagePatient patient) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            triageDao.insert(patient);
        });
    }

    public void refreshQueue() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<TriagePatient> existing = triageDao.getPriorityQueueRaw(); // Need to add this
            if (existing == null || existing.isEmpty()) {
                List<TriagePatient> mockData = getMockTriageData();
                triageDao.insertAll(mockData);
            }
        });
    }

    private List<TriagePatient> getMockTriageData() {
        List<TriagePatient> list = new ArrayList<>();
        list.add(new TriagePatient("Amara Okafor", "MQ-8821", "34Y", "Female", "EMERGENCY", false, "04:12"));
        list.add(new TriagePatient("Kofi Mensah", "MQ-9104", "12Y", "Male", "HIGH", false, "12:45"));
        list.add(new TriagePatient("Zainab Al-Farsi", "MQ-7729", "58Y", "Female", "MEDIUM", true, "In Progress"));
        list.add(new TriagePatient("Samuel Otieno", "MQ-5512", "45Y", "Male", "LOW", false, "45:10"));
        return list;
    }
}
