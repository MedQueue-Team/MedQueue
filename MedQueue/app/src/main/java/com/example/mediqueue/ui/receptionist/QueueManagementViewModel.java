package com.example.mediqueue.ui.receptionist;

import androidx.lifecycle.LiveData;

import com.example.mediqueue.data.local.entities.QueueEntity;
import com.example.mediqueue.data.repository.QueueRepository;
import com.example.mediqueue.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class QueueManagementViewModel extends BaseViewModel {
    private final QueueRepository queueRepository;

    @Inject
    public QueueManagementViewModel(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    public LiveData<List<QueueEntity>> getAllWaitingPatients() {
        return queueRepository.getAllWaitingPatients();
    }

    public void callPatient(String queueId) {
        queueRepository.updateQueueStatus(queueId, "CALLED");
    }

    public void markAsConsulting(String queueId) {
        queueRepository.updateQueueStatus(queueId, "IN_PROGRESS");
    }

    public void markAsCompleted(String queueId) {
        queueRepository.updateQueueStatus(queueId, "COMPLETED");
    }
}
