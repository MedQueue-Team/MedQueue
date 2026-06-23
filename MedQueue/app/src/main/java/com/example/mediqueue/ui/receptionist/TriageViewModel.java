package com.example.mediqueue.ui.receptionist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mediqueue.data.repository.TriageRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TriageViewModel extends ViewModel {

    private final TriageRepository repository;
    private final LiveData<List<TriagePatient>> triageQueue;

    @Inject
    public TriageViewModel(TriageRepository repository) {
        this.repository = repository;
        this.triageQueue = repository.getTriageQueue();
    }

    public LiveData<List<TriagePatient>> getTriageQueue() {
        return triageQueue;
    }

    public LiveData<TriagePatient> getPatientById(String patientId) {
        return repository.getPatientById(patientId);
    }

    public void updatePatient(TriagePatient patient) {
        repository.updatePatient(patient);
    }

    public void addPatient(TriagePatient patient) {
        repository.addPatient(patient);
    }

    public void refresh() {
        repository.refreshQueue();
    }
}
