package com.example.mediqueue.ui.admin;

public class StaffMember {
    private String name;
    private int load;
    private String department;
    private int patientsToday;
    private int avatarRes;

    private String status;
    private String statusMessage;

    public StaffMember(String name, int load, String department, int patientsToday, int avatarRes, String status, String statusMessage) {
        this.name = name;
        this.load = load;
        this.department = department;
        this.patientsToday = patientsToday;
        this.avatarRes = avatarRes;
        this.status = status;
        this.statusMessage = statusMessage;
    }

    public String getName() { return name; }
    public int getLoad() { return load; }
    public String getDepartment() { return department; }
    public int getPatientsToday() { return patientsToday; }
    public int getAvatarRes() { return avatarRes; }
    public String getStatus() { return status; }
    public String getStatusMessage() { return statusMessage; }
}
