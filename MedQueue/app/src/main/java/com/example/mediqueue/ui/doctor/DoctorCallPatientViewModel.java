package com.example.mediqueue.ui.doctor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DoctorCallPatientViewModel extends ViewModel {

    public static class CallPatientDetails {
        public String id = "A-12";
        public String name = "Elena Rodriguez";
        public String gender = "Female";
        public int age = 32;
        public String priority = "Emergency";
        public String room = "Room 104";
        public String symptoms = "Acute abdominal pain, nausea, and fever for 2 days. History of hypertension.";
        public String apptType = "Urgent Consultation";
        public String department = "General Medicine";
        public String status = "Awaiting Call";
    }

    private final MutableLiveData<CallPatientDetails> patientDetails = new MutableLiveData<>();
    private final MutableLiveData<String> callStatus = new MutableLiveData<>("Awaiting Call");

    public DoctorCallPatientViewModel() {
        patientDetails.setValue(new CallPatientDetails());
    }

    public LiveData<CallPatientDetails> getPatientDetails() {
        return patientDetails;
    }

    public LiveData<String> getCallStatus() {
        return callStatus;
    }

    public void startConsultation() {
        callStatus.setValue("In Consultation");
        CallPatientDetails details = patientDetails.getValue();
        if (details != null) {
            details.status = "In Consultation";
            patientDetails.setValue(details);
        }
    }

    public void skipPatient() {
        callStatus.setValue("Skipped");
        CallPatientDetails details = patientDetails.getValue();
        if (details != null) {
            details.status = "Skipped";
            patientDetails.setValue(details);
        }
    }

    public void markAbsent() {
        callStatus.setValue("Marked Absent");
        CallPatientDetails details = patientDetails.getValue();
        if (details != null) {
            details.status = "Absent";
            patientDetails.setValue(details);
        }
    }
}
