package com.example.mediqueue.ui.admin;

public class Appointment {
    public enum Status { PENDING, APPROVED, REJECTED, COMPLETED }
    
    public String patientName;
    public String doctorName;
    public String department;
    public String time;
    public Status status;

    public Appointment(String patientName, String doctorName, String department, String time, Status status) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.department = department;
        this.time = time;
        this.status = status;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDepartment() {
        return department;
    }

    public String getTime() {
        return time;
    }

    public Status getStatus() {
        return status;
    }
}
