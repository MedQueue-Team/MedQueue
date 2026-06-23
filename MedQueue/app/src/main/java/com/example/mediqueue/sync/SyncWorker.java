package com.example.mediqueue.sync;

import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mediqueue.api.ApiService;
import com.example.mediqueue.api.ApiModels;
import com.example.mediqueue.data.local.entities.AppointmentEntity;
import com.example.mediqueue.data.local.entities.PatientEntity;
import com.example.mediqueue.data.local.entities.QueueEntity;
import com.example.mediqueue.data.local.entities.ConsultationEntity;
import com.example.mediqueue.data.repository.AppointmentRepository;
import com.example.mediqueue.data.repository.PatientRepository;
import com.example.mediqueue.data.repository.QueueRepository;
import com.example.mediqueue.data.repository.ConsultationRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SyncWorker extends Worker {
    private static final String TAG = "SyncWorker";
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final QueueRepository queueRepository;
    private final ConsultationRepository consultationRepository;
    private final ApiService apiService;

    @Inject
    public SyncWorker(
            android.content.Context context,
            WorkerParameters workerParams,
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            QueueRepository queueRepository,
            ConsultationRepository consultationRepository,
            ApiService apiService
    ) {
        super(context, workerParams);
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.queueRepository = queueRepository;
        this.consultationRepository = consultationRepository;
        this.apiService = apiService;
    }

    @Override
    public Result doWork() {
        Log.d(TAG, "Starting background sync...");
        
        boolean allSynced = true;

        // 1. Sync Patients
        List<PatientEntity> unsyncedPatients = patientRepository.getUnsyncedPatients();
        if (unsyncedPatients != null && !unsyncedPatients.isEmpty()) {
            if (!syncPatients(unsyncedPatients)) {
                allSynced = false;
            }
        }

        // 2. Sync Appointments
        List<AppointmentEntity> unsyncedAppts = appointmentRepository.getUnsyncedAppointments();
        if (unsyncedAppts != null && !unsyncedAppts.isEmpty()) {
            if (!syncAppointments(unsyncedAppts)) {
                allSynced = false;
            }
        }

        // 3. Sync Queue
        List<QueueEntity> unsyncedQueue = queueRepository.getUnsyncedQueue();
        if (unsyncedQueue != null && !unsyncedQueue.isEmpty()) {
            if (!syncQueue(unsyncedQueue)) {
                allSynced = false;
            }
        }

        // 4. Sync Consultations
        allSynced = syncConsultations() && allSynced;

        return allSynced ? Result.success() : Result.retry();
    }

    private boolean syncPatients(List<PatientEntity> entities) {
        List<ApiModels.SyncPatientRequest> requests = new ArrayList<>();
        for (PatientEntity entity : entities) {
            ApiModels.SyncPatientRequest req = new ApiModels.SyncPatientRequest();
            req.setPatientId(entity.patientId);
            req.setFullName(entity.fullName);
            req.setEmail(entity.email);
            req.setDateOfBirth(entity.dateOfBirth);
            req.setPhoneNumber(entity.phoneNumber);
            req.setLastUpdated(entity.lastUpdated);
            requests.add(req);
        }

        try {
            retrofit2.Response<ApiModels.ApiResponse<Void>> response = apiService.syncPatients(requests).execute();
            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                for (PatientEntity entity : entities) {
                    patientRepository.markAsSynced(entity.patientId);
                }
                return true;
            }
        } catch (java.io.IOException e) {
            Log.e(TAG, "Patient sync failed", e);
        }
        return false;
    }

    private boolean syncAppointments(List<AppointmentEntity> entities) {
        List<ApiModels.SyncAppointmentRequest> requests = new ArrayList<>();
        for (AppointmentEntity entity : entities) {
            ApiModels.SyncAppointmentRequest req = new ApiModels.SyncAppointmentRequest();
            req.setAppointmentId(entity.appointmentId);
            req.setPatientId(entity.patientId);
            req.setDoctorId(entity.doctorId);
            req.setDoctorName(entity.doctorName);
            req.setDepartment(entity.department);
            req.setAppointmentDate(entity.appointmentDate);
            req.setAppointmentTime(entity.appointmentTime);
            req.setStatus(entity.status);
            req.setLastUpdated(entity.lastUpdated);
            requests.add(req);
        }

        try {
            retrofit2.Response<ApiModels.ApiResponse<Void>> response = apiService.syncAppointments(requests).execute();
            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                for (AppointmentEntity entity : entities) {
                    appointmentRepository.markAsSynced(entity.appointmentId);
                }
                return true;
            }
        } catch (java.io.IOException e) {
            Log.e(TAG, "Appointment sync failed", e);
        }
        return false;
    }

    private boolean syncQueue(List<QueueEntity> entities) {
        List<ApiModels.SyncQueueRequest> requests = new ArrayList<>();
        for (QueueEntity entity : entities) {
            ApiModels.SyncQueueRequest req = new ApiModels.SyncQueueRequest();
            req.setQueueId(entity.queueId);
            req.setPatientId(entity.patientId);
            req.setDepartment(entity.department);
            req.setPosition(entity.position);
            req.setEstimatedWaitTimeMins(entity.estimatedWaitTimeMins);
            req.setStatus(entity.status);
            req.setLastUpdated(entity.lastUpdated);
            requests.add(req);
        }

        try {
            retrofit2.Response<ApiModels.ApiResponse<Void>> response = apiService.syncQueue(requests).execute();
            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                for (QueueEntity entity : entities) {
                    queueRepository.markAsSynced(entity.queueId);
                }
                return true;
            }
        } catch (java.io.IOException e) {
            Log.e(TAG, "Queue sync failed", e);
        }
        return false;
    }

    private boolean syncConsultations() {
        Log.d(TAG, "Syncing consultations...");
        return true; 
    }
}
