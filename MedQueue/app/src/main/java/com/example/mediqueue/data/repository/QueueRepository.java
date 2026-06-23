package com.example.mediqueue.data.repository;

import androidx.lifecycle.LiveData;

import com.example.mediqueue.api.ApiService;
import com.example.mediqueue.data.local.dao.QueueDao;
import com.example.mediqueue.data.local.entities.QueueEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class QueueRepository extends BaseRepository {

    private final QueueDao queueDao;
    private final ApiService apiService;

    @Inject
    public QueueRepository(QueueDao queueDao, ApiService apiService) {
        this.queueDao = queueDao;
        this.apiService = apiService;
    }

    public LiveData<QueueEntity> getActiveQueueForPatient(String patientId) {
        return queueDao.getActiveQueueForPatient(patientId);
    }

    public LiveData<List<QueueEntity>> getQueueForDepartment(String department) {
        return queueDao.getQueueForDepartment(department);
    }

    public LiveData<List<QueueEntity>> getAllWaitingPatients() {
        return queueDao.getAllWaitingPatients();
    }

    public List<QueueEntity> getUnsyncedQueue() {
        return queueDao.getUnsyncedQueue();
    }

    public void markAsSynced(String queueId) {
        queueDao.markAsSynced(queueId);
    }

    public void updateQueueStatus(String queueId, String status) {
        queueDao.updateQueueStatus(queueId, status);
    }

    public void insertQueue(QueueEntity queue) {
        queueDao.insertQueue(queue);
    }
}
