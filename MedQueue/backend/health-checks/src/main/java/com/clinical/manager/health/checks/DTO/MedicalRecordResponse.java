package com.clinical.manager.health.checks.DTO;

import java.time.LocalDateTime;

import com.clinical.manager.health.checks.enums.RecordStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public class MedicalRecordResponse {

    @Schema(example = "15")
    private Long id;

    @Schema(example = "John Doe")
    private String patientName;

    @Schema(example = "Dr Jane Smith")
    private String doctorName;

    @Schema(example = "Acute sinusitis")
    private String diagnosis;

    @Schema(example = "Amoxicillin 500mg three times daily for 7 days")
    private String prescription;

    @Schema(example = "Patient advised to rest and increase fluid intake")
    private String notes;

    @Schema(example = "Follow up if symptoms continue after 7 days")
    private String treatmentPlan;

    @Schema(example = "2026-05-27T09:00:00")
    private LocalDateTime followUpDate;

    @Schema(example = "ACTIVE")
    private RecordStatus status;

    @Schema(example = "2026-05-20T10:30:00")
    private LocalDateTime createdAt;

    public MedicalRecordResponse(
            Long id,
            String patientName,
            String doctorName,
            String diagnosis,
            String prescription,
            String notes,
            String treatmentPlan,
            LocalDateTime followUpDate,
            RecordStatus status,
            LocalDateTime createdAt
    ) {

        this.id = id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.notes = notes;
        this.treatmentPlan = treatmentPlan;
        this.followUpDate = followUpDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getNotes() {
        return notes;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public LocalDateTime getFollowUpDate() {
        return followUpDate;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
