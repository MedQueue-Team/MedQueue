package com.example.mediqueue.ui.patient;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.example.mediqueue.data.local.entities.MedicalHistory;
import com.example.mediqueue.data.repository.MedicalHistoryRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class PatientMedicalRecordsViewModel extends ViewModel {

    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");

    @Inject
    public PatientMedicalRecordsViewModel(Application application) {
        this.medicalHistoryRepository = new MedicalHistoryRepository(application);
    }

    public LiveData<List<MedicalHistory>> getMedicalHistory() {
        LiveData<List<MedicalHistory>> allRecords = medicalHistoryRepository.getAllHistory();

        return Transformations.switchMap(searchQuery, query ->
            Transformations.map(allRecords, list -> {
                List<MedicalHistory> result = new ArrayList<>();
                if (list == null) return result;

                String cleanQuery = query.toLowerCase().trim();
                for (MedicalHistory record : list) {
                    boolean match = cleanQuery.isEmpty()
                            || (record.getDiagnosis() != null && record.getDiagnosis().toLowerCase().contains(cleanQuery))
                            || (record.getDoctorName() != null && record.getDoctorName().toLowerCase().contains(cleanQuery))
                            || (record.getClinicName() != null && record.getClinicName().toLowerCase().contains(cleanQuery));

                    if (match) {
                        result.add(record);
                    }
                }
                return result;
            })
        );
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }
}
