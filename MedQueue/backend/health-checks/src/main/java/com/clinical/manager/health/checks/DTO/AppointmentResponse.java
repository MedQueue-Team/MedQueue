package com.clinical.manager.health.checks.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.clinical.manager.health.checks.enums.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public class AppointmentResponse {

    @Schema(example = "10")
    private Long id;

    @Schema(example = "John Doe")
    private String patientName;

    @Schema(example = "Dr Jane Smith")
    private String doctorName;

    @Schema(example = "Dental")
    private String department;

    @Schema(example = "Tooth pain and swelling")
    private String reason;

    @Schema(example = "2026-05-20")
    private LocalDate appointmentDate;

    @Schema(example = "10:00:00")
    private LocalTime appointmentTime;

    @Schema(example = "PENDING")
    private AppointmentStatus status;

    public AppointmentResponse(
            Long id,
            String patientName,
            String doctorName,
            String department,
            String reason,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            AppointmentStatus status
    ) {

        this.id = id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.department = department;
        this.reason = reason;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
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

    public String getDepartment() {
        return department;
    }

    public String getReason() {
        return reason;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }
}
