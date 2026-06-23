package com.example.mediqueue.ui.receptionist;

public class NurseRecord {

    public enum RecordType { ALL, TRIAGE, CONSULTATION, INPATIENT }

    private final String patientId;
    private final String patientName;
    private final int    age;
    private final String gender;
    private final RecordType recordType;    // Inpatient / Outpatient / Triage / Consultation
    private final String assignedDoctor;
    private final String lastVisitDate;
    private final String ward;              // For inpatients
    private final String triagePriority;   // e.g. CRITICAL, HIGH, MEDIUM, LOW

    public NurseRecord(String patientId, String patientName, int age, String gender,
                       RecordType recordType, String assignedDoctor,
                       String lastVisitDate, String ward, String triagePriority) {
        this.patientId      = patientId;
        this.patientName    = patientName;
        this.age            = age;
        this.gender         = gender;
        this.recordType     = recordType;
        this.assignedDoctor = assignedDoctor;
        this.lastVisitDate  = lastVisitDate;
        this.ward           = ward;
        this.triagePriority = triagePriority;
    }

    public String     getPatientId()      { return patientId; }
    public String     getPatientName()    { return patientName; }
    public int        getAge()            { return age; }
    public String     getGender()         { return gender; }
    public RecordType getRecordType()     { return recordType; }
    public String     getAssignedDoctor() { return assignedDoctor; }
    public String     getLastVisitDate()  { return lastVisitDate; }
    public String     getWard()           { return ward; }
    public String     getTriagePriority() { return triagePriority; }
}
