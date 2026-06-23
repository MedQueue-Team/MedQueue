package com.example.mediqueue.ui.doctor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mediqueue.data.local.entities.ConsultationEntity;
import com.example.mediqueue.data.repository.ConsultationRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DoctorConsultationViewModel extends ViewModel {

    private final ConsultationRepository consultationRepository;

    public static class PatientConsultationInfo {
        public String id = "A-12";
        public String name = "Elena Rodriguez";
        public String gender = "Female";
        public int age = 32;
        public String bloodType = "O+ Positive";
        public String patientId = "LSK-KNH-004582";
        public String status = "Active";
    }

    private final MutableLiveData<PatientConsultationInfo> patientInfo = new MutableLiveData<>();
    private final MutableLiveData<Boolean> saveSuccess = new MutableLiveData<>();

    @Inject
    public DoctorConsultationViewModel(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
        patientInfo.setValue(new PatientConsultationInfo());
    }

    public LiveData<PatientConsultationInfo> getPatientInfo() {
        return patientInfo;
    }

    public LiveData<Boolean> getSaveSuccess() {
        return saveSuccess;
    }

    public void saveRecord(String symptoms, String diagnosis, String prescription, String notes) {
        PatientConsultationInfo info = patientInfo.getValue();
        if (info == null) return;

        ConsultationEntity entity = new ConsultationEntity(
                info.patientId,
                "doctor_id_001", // Placeholder doctor ID
                symptoms,
                diagnosis,
                prescription,
                notes
        );

        consultationRepository.saveConsultationOffline(entity);
        saveSuccess.setValue(true);
    }
}
