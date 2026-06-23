package com.example.mediqueue.data.repository;

import android.app.Application;
import com.example.mediqueue.data.local.AppDatabase;
import com.example.mediqueue.data.local.dao.ConsultationDao;
import com.example.mediqueue.data.local.entities.ConsultationEntity;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.hilt.android.qualifiers.ApplicationContext;
import android.content.Context;

@Singleton
public class ConsultationRepository extends BaseRepository {
    private final ConsultationDao consultationDao;

    @Inject
    public ConsultationRepository(ConsultationDao consultationDao) {
        this.consultationDao = consultationDao;
    }

    public void saveConsultationOffline(ConsultationEntity consultation) {
        consultation.isSynced = false;
        AppDatabase.databaseWriteExecutor.execute(() -> {
            consultationDao.insertConsultation(consultation);
        });
    }

    public List<ConsultationEntity> getUnsyncedConsultations() {
        // For simplicity, returning on main thread or using a synchronous call in a Worker
        // In real app, this should be wrapped in a blocking call for the SyncWorker
        return null; // Placeholder, will implement a synchronous version for Worker
    }
    
    public void markAsSynced(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            ConsultationEntity entity = new ConsultationEntity();
            entity.id = id;
            entity.isSynced = true;
            consultationDao.updateConsultation(entity);
        });
    }
}
