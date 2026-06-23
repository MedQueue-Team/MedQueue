package com.example.mediqueue.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mediqueue.data.local.entities.MedicalHistory;
import com.example.mediqueue.data.local.entities.Notification;
import com.example.mediqueue.ui.receptionist.TriagePatient;

import com.example.mediqueue.data.local.dao.UserDao;
import com.example.mediqueue.data.local.entity.User;
import com.example.mediqueue.core.UserRole;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.annotation.NonNull;
import java.util.Arrays;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.mediqueue.data.local.dao.AppointmentDao;
import com.example.mediqueue.data.local.dao.PatientDao;
import com.example.mediqueue.data.local.dao.QueueDao;
import com.example.mediqueue.data.local.dao.ConsultationDao;
import com.example.mediqueue.data.local.entities.AppointmentEntity;
import com.example.mediqueue.data.local.entities.PatientEntity;
import com.example.mediqueue.data.local.entities.QueueEntity;
import com.example.mediqueue.data.local.entities.ConsultationEntity;

@Database(entities = {MedicalHistory.class, Notification.class, TriagePatient.class, User.class, PatientEntity.class, AppointmentEntity.class, QueueEntity.class, ConsultationEntity.class}, version = 5, exportSchema = false)
@TypeConverters({Notification.Converters.class, Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract MedicalHistoryDao medicalHistoryDao();
    public abstract NotificationDao notificationDao();
    public abstract TriageDao triageDao();
    public abstract UserDao userDao();
    public abstract PatientDao patientDao();
    public abstract AppointmentDao appointmentDao();
    public abstract QueueDao queueDao();
    public abstract ConsultationDao consultationDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "mediqueue_db")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // INSTANCE is guaranteed to be initialized by the time onCreate is called 
                // because it's called during the first database access which requires getDatabase().
                // However, we use a local check to be absolutely safe.
                AppDatabase instance = INSTANCE;
                if (instance != null) {
                    UserDao dao = instance.userDao();
                    dao.insertUsers(Arrays.asList(
                        new User("admin@mediqueue.com", "123456", "Clinic Administrator", "", UserRole.ADMIN),
                        new User("doctor@mediqueue.com", "123456", "Dr. Jane Smith", "", UserRole.DOCTOR),
                        new User("nurse@mediqueue.com", "123456", "Nurse Joy", "", UserRole.NURSE),
                        new User("patient@mediqueue.com", "123456", "John Doe", "", UserRole.PATIENT)
                    ));
                }
            });
        }
    };
}
