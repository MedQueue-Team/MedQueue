package com.example.mediqueue.ui.doctor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class DoctorPatientsListViewModel extends ViewModel {

    public static class WaitingPatient {
        public String id;
        public String name;
        public String department;
        public String priority;
        public String waitingTime;
        public String initials;

        public WaitingPatient(String id, String name, String department, String priority, String waitingTime, String initials) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.priority = priority;
            this.waitingTime = waitingTime;
            this.initials = initials;
        }
    }

    private final MutableLiveData<List<WaitingPatient>> waitingPatients = new MutableLiveData<>();
    private final MutableLiveData<Integer> activeCount = new MutableLiveData<>();
    private final MutableLiveData<String> avgWait = new MutableLiveData<>();
    private final MutableLiveData<String> successRate = new MutableLiveData<>();

    public DoctorPatientsListViewModel() {
        List<WaitingPatient> patients = new ArrayList<>();
        patients.add(new WaitingPatient("#1", "John Banda", "General OPD", "High", "10 mins", "JB"));
        patients.add(new WaitingPatient("#2", "Mary Phiri", "Pediatrics", "Medium", "25 mins", "MP"));
        patients.add(new WaitingPatient("#3", "Samuel Zulu", "General OPD", "Low", "40 mins", "SZ"));
        patients.add(new WaitingPatient("#4", "Alice Mumba", "Cardiology", "High", "15 mins", "AM"));
        patients.add(new WaitingPatient("#5", "David Lungu", "General OPD", "Medium", "30 mins", "DL"));
        
        waitingPatients.setValue(patients);
        activeCount.setValue(patients.size());
        avgWait.setValue("28m");
        successRate.setValue("94%");
    }

    public LiveData<List<WaitingPatient>> getWaitingPatients() { return waitingPatients; }
    public LiveData<Integer> getActiveCount() { return activeCount; }
    public LiveData<String> getAvgWait() { return avgWait; }
    public LiveData<String> getSuccessRate() { return successRate; }
}
