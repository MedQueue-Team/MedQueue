package com.clinical.manager.health.checks.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateMedicalRecordRequest {

    @Schema(example = "3")
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @Schema(example = "Acute sinusitis")
    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    @Schema(example = "Amoxicillin 500mg three times daily for 7 days")
    private String prescription;

    @Schema(example = "CBC normal; no abnormal findings")
    private String labResults;

    @Schema(example = "Patient advised to rest and increase fluid intake")
    private String notes;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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
}
