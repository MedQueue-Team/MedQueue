package com.example.mediqueue.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mediqueue.R;
import com.example.mediqueue.data.local.AppDatabase;
import com.example.mediqueue.data.local.MedicalHistoryDao;
import com.example.mediqueue.data.local.entities.MedicalHistory;

import java.util.ArrayList;
import java.util.List;

public class MedicalHistoryRepository {

    private final MedicalHistoryDao medicalHistoryDao;
    private final LiveData<List<MedicalHistory>> allHistory;

    public MedicalHistoryRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        medicalHistoryDao = db.medicalHistoryDao();
        allHistory = medicalHistoryDao.getAllHistory();
    }

    public LiveData<List<MedicalHistory>> getAllHistory() {
        return allHistory;
    }

    public void refreshHistory() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // In a real app, you'd fetch from API here
            List<MedicalHistory> mockData = getMockMedicalHistory();
            medicalHistoryDao.insertAll(mockData);
        });
    }

    private List<MedicalHistory> getMockMedicalHistory() {
        List<MedicalHistory> historyList = new ArrayList<>();
        
        historyList.add(new MedicalHistory("12 Oct 2023", "General Consultation", "Completed", 
                "City Central Clinic", "Dr. Sarah Mensah", 
                R.drawable.ic_local_hospital, R.drawable.ic_medical_services,
                false, "View Summary", true));

        historyList.add(new MedicalHistory("28 Sep 2023", "Vaccination", "Completed", 
                "Hope Wellness Center", "Nurse John Doe", 
                R.drawable.ic_domain, R.drawable.ic_person,
                false, "View Summary", false));

        historyList.add(new MedicalHistory("15 Nov 2023", "Upcoming Checkup", "Scheduled", 
                "Metro Heart Hospital", "Dr. Michael Chen", 
                R.drawable.ic_apartment, R.drawable.ic_notes,
                true, "Prepare Notes", true));

        historyList.add(new MedicalHistory("02 Jul 2023", "Routine Lab Test", "Completed", 
                "Precision Labs", "Lab Technician", 
                R.drawable.ic_biotech, R.drawable.ic_biotech,
                false, "View Results", false));

        historyList.add(new MedicalHistory("10 May 2023", "Dermatology Follow-up", "Cancelled", 
                "Dermatology Plus", "Dr. Anita Ray", 
                R.drawable.ic_stethoscope, R.drawable.ic_person,
                false, "Rebook Appointment", false));

        return historyList;
    }
}
