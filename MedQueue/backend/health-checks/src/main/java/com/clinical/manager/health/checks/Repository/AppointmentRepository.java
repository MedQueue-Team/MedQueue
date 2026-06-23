package com.clinical.manager.health.checks.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinical.manager.health.checks.Entity.Appointment;
import com.clinical.manager.health.checks.Entity.Users;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    /*
     * Patient appointments
     */
    List<Appointment> findByPatient(
            Users patient
    );

    /*
     * Doctor appointments
     */
    List<Appointment> findByDoctor(
            Users doctor
    );

    /*
     * Prevent overlapping appointments
     */
    boolean existsByDoctorAndAppointmentDateAndAppointmentTime(
            Users doctor,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    );
}