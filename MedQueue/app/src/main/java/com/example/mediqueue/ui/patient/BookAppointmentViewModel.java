package com.example.mediqueue.ui.patient;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mediqueue.data.local.entities.AppointmentEntity;
import com.example.mediqueue.data.repository.AppointmentRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.UUID;
import javax.inject.Inject;

@HiltViewModel
public class BookAppointmentViewModel extends ViewModel {

    private final AppointmentRepository appointmentRepository;

    private final MutableLiveData<String> selectedDepartment = new MutableLiveData<>("General OPD");
    private final MutableLiveData<String> selectedDoctor = new MutableLiveData<>("Dr. Sarah Jenkins");
    private final MutableLiveData<String> selectedDate = new MutableLiveData<>("Oct 19, 2023");
    private final MutableLiveData<String> selectedTime = new MutableLiveData<>("10:00 AM");
    private final MutableLiveData<String> selectedPriority = new MutableLiveData<>("NORMAL");
    private final MutableLiveData<Boolean> bookingSuccess = new MutableLiveData<>(false);

    @Inject
    public BookAppointmentViewModel(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public LiveData<String> getSelectedDepartment() { return selectedDepartment; }
    public LiveData<String> getSelectedDoctor() { return selectedDoctor; }
    public LiveData<String> getSelectedDate() { return selectedDate; }
    public LiveData<String> getSelectedTime() { return selectedTime; }
    public LiveData<String> getSelectedPriority() { return selectedPriority; }
    public LiveData<Boolean> getBookingSuccess() { return bookingSuccess; }

    public void setDepartment(String dept) { selectedDepartment.setValue(dept); }
    public void setDoctor(String doc) { selectedDoctor.setValue(doc); }
    public void setDate(String date) { selectedDate.setValue(date); }
    public void setTime(String time) { selectedTime.setValue(time); }
    public void setPriority(String priority) { selectedPriority.setValue(priority); }
    public void setBookingSuccess(boolean success) { bookingSuccess.setValue(success); }

    public void bookAppointment(String reason) {
        new Thread(() -> {
            try {
                AppointmentEntity appt = new AppointmentEntity();
                appt.appointmentId = "APT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                appt.patientId = "MQ-8821";
                appt.doctorId = "DOC-001";
                appt.doctorName = selectedDoctor.getValue();
                appt.department = selectedDepartment.getValue();
                appt.appointmentDate = selectedDate.getValue();
                appt.appointmentTime = selectedTime.getValue();
                appt.status = "APPROVED"; // Instantly approved for demo
                appt.lastUpdated = System.currentTimeMillis();
                appt.isSynced = false;

                appointmentRepository.insertAppointment(appt);
                bookingSuccess.postValue(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
