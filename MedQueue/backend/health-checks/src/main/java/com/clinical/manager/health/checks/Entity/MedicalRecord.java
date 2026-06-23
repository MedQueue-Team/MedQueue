package com.clinical.manager.health.checks.Entity;

import java.time.LocalDateTime;

import com.clinical.manager.health.checks.enums.RecordStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Patient
     */
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Users patient;

    /*
     * Doctor
     */
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Users doctor;

    /*
     * Diagnosis
     */
    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    /*
     * Prescription
     */
    @Column(columnDefinition = "TEXT")
    private String prescription;

    /*
     * Lab results
     */
    @Column(columnDefinition = "TEXT")
    private String labResults;

    /*
     * Doctor notes
     */
    @Column(columnDefinition = "TEXT")
    private String notes;

    /*
     * Record status
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordStatus status;

    /*
     * Created time
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        if (status == null) {

            status = RecordStatus.ACTIVE;
        }
    }

    public Long getId() {
        return id;
    }

    public Users getPatient() {
        return patient;
    }

    public void setPatient(Users patient) {
        this.patient = patient;
    }

    public Users getDoctor() {
        return doctor;
    }

    public void setDoctor(Users doctor) {
        this.doctor = doctor;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getLabResults() {
        return labResults;
    }

    public void setLabResults(String labResults) {
        this.labResults = labResults;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
