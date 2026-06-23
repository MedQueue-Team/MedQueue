package com.example.mediqueue.ui.receptionist;

public class DepartmentStatus {
    private String name;
    private int patientCount;
    private String avgWaitTime;
    private int activeDoctors;
    private int progress;

    public DepartmentStatus(String name, int patientCount, String avgWaitTime, int activeDoctors, int progress) {
        this.name = name;
        this.patientCount = patientCount;
        this.avgWaitTime = avgWaitTime;
        this.activeDoctors = activeDoctors;
        this.progress = progress;
    }

    public String getName() { return name; }
    public int getPatientCount() { return patientCount; }
    public String getAvgWaitTime() { return avgWaitTime; }
    public int getActiveDoctors() { return activeDoctors; }
    public int getProgress() { return progress; }
}
