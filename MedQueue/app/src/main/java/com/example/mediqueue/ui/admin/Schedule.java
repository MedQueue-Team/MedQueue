package com.example.mediqueue.ui.admin;

public class Schedule {
    public String doctorName;
    public String department;
    public String days;
    public String timeRange;

    public Schedule(String doctorName, String department, String days, String timeRange) {
        this.doctorName = doctorName;
        this.department = department;
        this.days = days;
        this.timeRange = timeRange;
    }

    public String getDoctorName() { return doctorName; }
    public String getDepartment() { return department; }
    public String getDays() { return days; }
    public String getTime() { return timeRange; }
}
