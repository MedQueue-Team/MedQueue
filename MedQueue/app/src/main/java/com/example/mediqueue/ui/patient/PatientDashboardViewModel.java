package com.example.mediqueue.ui.patient;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mediqueue.data.local.entities.AppointmentEntity;
import com.example.mediqueue.data.local.entities.MedicalHistory;
import com.example.mediqueue.data.local.entities.PatientEntity;
import com.example.mediqueue.data.local.entities.QueueEntity;
import com.example.mediqueue.data.repository.AppointmentRepository;
import com.example.mediqueue.data.repository.MedicalHistoryRepository;
import com.example.mediqueue.data.repository.PatientRepository;
import com.example.mediqueue.data.repository.QueueRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class PatientDashboardViewModel extends ViewModel {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final QueueRepository queueRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;

    private final String defaultPatientId = "MQ-8821";

    @Inject
    public PatientDashboardViewModel(
            Application application,
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository,
            QueueRepository queueRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.queueRepository = queueRepository;
        this.medicalHistoryRepository = new MedicalHistoryRepository(application);

        // Prepopulate database with mock patient/appointments if empty
        prepopulateMockData();
    }

    public LiveData<PatientEntity> getPatient() {
        return patientRepository.getPatient(defaultPatientId);
    }

    public LiveData<List<AppointmentEntity>> getUpcomingAppointments() {
        return appointmentRepository.getAppointmentsForPatient(defaultPatientId);
    }

    public LiveData<QueueEntity> getActiveQueue() {
        return queueRepository.getActiveQueueForPatient(defaultPatientId);
    }

    public LiveData<List<MedicalHistory>> getMedicalHistory() {
        return medicalHistoryRepository.getAllHistory();
    }

    private void prepopulateMockData() {
        // Pre-populating of Room database for demo and seamless UX
        new Thread(() -> {
            try {
                // Check if patient exists, otherwise create
                // Note: repository triggers can insert defaults
                medicalHistoryRepository.refreshHistory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
