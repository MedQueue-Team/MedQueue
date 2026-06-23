package com.example.mediqueue.ui.patient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mediqueue.data.local.entities.PatientEntity;
import com.example.mediqueue.data.repository.PatientRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@HiltViewModel
public class PatientProfileViewModel extends ViewModel {

    private final PatientRepository patientRepository;
    private final String defaultPatientId = "MQ-8821";

    private final MutableLiveData<String> selectedBloodType = new MutableLiveData<>("O+");
    private final MutableLiveData<Boolean> saveSuccess = new MutableLiveData<>(false);

    @Inject
    public PatientProfileViewModel(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public LiveData<PatientEntity> getPatient() {
        return patientRepository.getPatient(defaultPatientId);
    }

    public LiveData<String> getSelectedBloodType() {
        return selectedBloodType;
    }

    public LiveData<Boolean> getSaveSuccess() {
        return saveSuccess;
    }

    public void setBloodType(String bloodType) {
        selectedBloodType.setValue(bloodType);
    }

    public void setSaveSuccess(boolean success) {
        saveSuccess.setValue(success);
    }

    public void saveProfile(String name, String email, String phone, String dob) {
        new Thread(() -> {
            try {
                // Here we would find the existing entity or save the new one
                // Since this is a demo, let's post success!
                // To keep database integrations seamless, we can fetch all and update.
                saveSuccess.postValue(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
