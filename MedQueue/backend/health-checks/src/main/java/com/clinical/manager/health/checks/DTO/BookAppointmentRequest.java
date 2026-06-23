package com.clinical.manager.health.checks.DTO;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class BookAppointmentRequest {

    @Schema(example = "2")
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @Schema(example = "Dental")
    @NotBlank(message = "Department is required")
    private String department;

    @Schema(example = "Tooth pain and swelling")
    @NotBlank(message = "Reason is required")
    private String reason;

    @Schema(example = "2026-05-20T10:00:00")
    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment must be in the future")
    private LocalDateTime appointmentDate;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(
            LocalDateTime appointmentDate
    ) {
        this.appointmentDate = appointmentDate;
    }
}
