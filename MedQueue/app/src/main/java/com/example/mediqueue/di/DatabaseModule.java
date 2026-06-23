package com.example.mediqueue.di;

import android.content.Context;
import com.example.mediqueue.data.local.AppDatabase;
import com.example.mediqueue.data.local.dao.UserDao;
import com.example.mediqueue.data.local.dao.PatientDao;
import com.example.mediqueue.data.local.dao.AppointmentDao;
import com.example.mediqueue.data.local.dao.QueueDao;
import com.example.mediqueue.data.local.TriageDao;
import com.example.mediqueue.data.local.MedicalHistoryDao;
import com.example.mediqueue.data.local.NotificationDao;
import com.example.mediqueue.data.local.dao.ConsultationDao;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * Hilt module for providing Room database and DAO dependencies
 */
@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return AppDatabase.getDatabase(context);
    }

    @Provides
    @Singleton
    public UserDao provideUserDao(AppDatabase database) {
        return database.userDao();
    }

    @Provides
    @Singleton
    public PatientDao providePatientDao(AppDatabase database) {
        return database.patientDao();
    }

    @Provides
    @Singleton
    public AppointmentDao provideAppointmentDao(AppDatabase database) {
        return database.appointmentDao();
    }

    @Provides
    @Singleton
    public QueueDao provideQueueDao(AppDatabase database) {
        return database.queueDao();
    }

    @Provides
    @Singleton
    public TriageDao provideTriageDao(AppDatabase database) {
        return database.triageDao();
    }

    @Provides
    @Singleton
    public MedicalHistoryDao provideMedicalHistoryDao(AppDatabase database) {
        return database.medicalHistoryDao();
    }

    @Provides
    @Singleton
    public NotificationDao provideNotificationDao(AppDatabase database) {
        return database.notificationDao();
    }

    @Provides
    @Singleton
    public ConsultationDao provideConsultationDao(AppDatabase database) {
        return database.consultationDao();
    }
}
