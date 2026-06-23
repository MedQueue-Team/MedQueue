package com.example.mediqueue.data.repository;

import androidx.lifecycle.LiveData;

import com.example.mediqueue.api.ApiService;
import com.example.mediqueue.data.local.dao.AppointmentDao;
import com.example.mediqueue.data.local.entities.AppointmentEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppointmentRepository extends BaseRepository {

    private final AppointmentDao appointmentDao;
    private final ApiService apiService;

    @Inject
    public AppointmentRepository(AppointmentDao appointmentDao, ApiService apiService) {
        this.appointmentDao = appointmentDao;
        this.apiService = apiService;
    }

    public LiveData<List<AppointmentEntity>> getAppointmentsForPatient(String patientId) {
        return appointmentDao.getAppointmentsForPatient(patientId);
    }

    public LiveData<List<AppointmentEntity>> getAppointmentsForDoctor(String doctorId) {
        return appointmentDao.getAppointmentsForDoctor(doctorId);
    }

    public LiveData<List<AppointmentEntity>> getPendingAppointments() {
        return appointmentDao.getPendingAppointments();
    }

    public List<AppointmentEntity> getUnsyncedAppointments() {
        return appointmentDao.getUnsyncedAppointments();
    }

    public void markAsSynced(String appointmentId) {
        appointmentDao.markAsSynced(appointmentId);
    }

    public void updateAppointmentStatus(String appointmentId, String status) {
        appointmentDao.updateAppointmentStatus(appointmentId, status);
    }

    public void insertAppointment(AppointmentEntity appointment) {
        appointmentDao.insertAppointment(appointment);
    }
}
