package com.example.mediqueue.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mediqueue.data.local.entities.AppointmentEntity;

import java.util.List;

@Dao
public interface AppointmentDao {
    
    @Query("SELECT * FROM appointments WHERE patient_id = :patientId ORDER BY appointment_date ASC, appointment_time ASC")
    LiveData<List<AppointmentEntity>> getAppointmentsForPatient(String patientId);
    
    @Query("SELECT * FROM appointments WHERE doctor_id = :doctorId ORDER BY appointment_date ASC, appointment_time ASC")
    LiveData<List<AppointmentEntity>> getAppointmentsForDoctor(String doctorId);
    
    @Query("SELECT * FROM appointments WHERE appointment_id = :appointmentId")
    LiveData<AppointmentEntity> getAppointment(String appointmentId);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAppointment(AppointmentEntity appointment);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAppointments(List<AppointmentEntity> appointments);
    
    @Delete
    void deleteAppointment(AppointmentEntity appointment);
    
    @Query("SELECT * FROM appointments WHERE status = 'PENDING'")
    LiveData<List<AppointmentEntity>> getPendingAppointments();

    @Query("SELECT * FROM appointments WHERE is_synced = 0")
    List<AppointmentEntity> getUnsyncedAppointments();

    @Query("UPDATE appointments SET is_synced = 1 WHERE appointment_id = :appointmentId")
    void markAsSynced(String appointmentId);

    @Query("UPDATE appointments SET status = :status WHERE appointment_id = :appointmentId")
    void updateAppointmentStatus(String appointmentId, String status);
}
