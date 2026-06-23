package com.example.mediqueue.ui.receptionist;

import androidx.lifecycle.LiveData;

import com.example.mediqueue.data.local.entities.PatientEntity;
import com.example.mediqueue.data.local.entities.QueueEntity;
import com.example.mediqueue.data.repository.PatientRepository;
import com.example.mediqueue.data.repository.QueueRepository;
import com.example.mediqueue.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NurseViewModel extends BaseViewModel {

    private final PatientRepository patientRepository;
    private final QueueRepository queueRepository;

    @Inject
    public NurseViewModel(PatientRepository patientRepository,
                          QueueRepository queueRepository) {
        this.patientRepository = patientRepository;
        this.queueRepository = queueRepository;
    }

    public LiveData<List<PatientEntity>> getAllPatients() {
        return patientRepository.getAllPatients();
    }

    public LiveData<List<QueueEntity>> getDepartmentQueue(String department) {
        return queueRepository.getQueueForDepartment(department);
    }
}
