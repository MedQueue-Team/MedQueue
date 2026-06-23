package com.example.mediqueue.ui.doctor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class DoctorDashboardViewModel extends ViewModel {

    public static class DashboardStats {
        public int todayPatients = 18;
        public String patientsIncrease = "+3 since 8am";
        public int waitingQueue = 5;
        public int appointments = 12;
        public int completed = 7;
    }

    public static class QueuePatient {
        public String id = "#12";
        public String name = "John Banda";
        public String department = "General OPD";
        public String priority = "High Priority";
        public String waitingTime = "10 mins";
        public String initials = "JB";
    }

    public static class Appointment {
        public String name;
        public String reason;
        public String time;
        public String status;
        public String initials;

        public Appointment(String initials, String name, String reason, String time, String status) {
            this.initials = initials;
            this.name = name;
            this.reason = reason;
            this.time = time;
            this.status = status;
        }
    }

    private final MutableLiveData<DashboardStats> stats = new MutableLiveData<>();
    private final MutableLiveData<QueuePatient> livePatient = new MutableLiveData<>();
    private final MutableLiveData<List<Appointment>> appointments = new MutableLiveData<>();

    public DoctorDashboardViewModel() {
        stats.setValue(new DashboardStats());
        livePatient.setValue(new QueuePatient());
        
        List<Appointment> apptList = new ArrayList<>();
        apptList.add(new Appointment("MP", "Mary Phiri", "Headache", "10:30 AM", "APPROVED"));
        appointments.setValue(apptList);
    }

    public LiveData<DashboardStats> getStats() { return stats; }
    public LiveData<QueuePatient> getLivePatient() { return livePatient; }
    public LiveData<List<Appointment>> getAppointments() { return appointments; }
}
