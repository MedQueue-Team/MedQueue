package com.example.mediqueue.ui.receptionist;

import androidx.lifecycle.LiveData;

import com.example.mediqueue.data.local.entities.AppointmentEntity;
import com.example.mediqueue.data.repository.AppointmentRepository;
import com.example.mediqueue.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class AppointmentApprovalViewModel extends BaseViewModel {
    private final AppointmentRepository appointmentRepository;

    @Inject
    public AppointmentApprovalViewModel(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public LiveData<List<AppointmentEntity>> getPendingAppointments() {
        return appointmentRepository.getPendingAppointments();
    }

    public void approveAppointment(String appointmentId) {
        appointmentRepository.updateAppointmentStatus(appointmentId, "APPROVED");
    }

    public void rejectAppointment(String appointmentId) {
        appointmentRepository.updateAppointmentStatus(appointmentId, "REJECTED");
    }
}
