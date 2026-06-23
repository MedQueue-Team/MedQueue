package com.clinical.manager.health.checks.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.clinical.manager.health.checks.DTO.AppointmentResponse;
import com.clinical.manager.health.checks.DTO.BookAppointmentRequest;
import com.clinical.manager.health.checks.Entity.Appointment;
import com.clinical.manager.health.checks.Entity.DoctorSchedule;
import com.clinical.manager.health.checks.Entity.Users;
import com.clinical.manager.health.checks.Exception.ResourceNotFoundException;
import com.clinical.manager.health.checks.Repository.AppointmentRepository;
import com.clinical.manager.health.checks.Repository.DoctorScheduleRepository;
import com.clinical.manager.health.checks.Repository.UserRepository;
import com.clinical.manager.health.checks.enums.AppointmentStatus;
import com.clinical.manager.health.checks.enums.Role;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    /*
     * Patient books appointment
     */
    public String bookAppointment(
            BookAppointmentRequest request,
            Authentication authentication
    ) {

        String patientEmail =
                authentication.getName();

        Users patient = userRepository
                .findByEmail(patientEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"
                        ));

        Users doctor = userRepository
                .findById(request.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"
                        ));

        /*
         * Ensure selected user is a doctor.
         */
        if (doctor.getRole() != Role.DOCTOR) {

            throw new RuntimeException(
                    "Selected user is not a doctor"
            );
        }

        LocalDateTime appointmentDateTime =
                request.getAppointmentDate();

        LocalDate appointmentDate =
                appointmentDateTime.toLocalDate();

        LocalTime appointmentTime =
                appointmentDateTime.toLocalTime();

        /*
         * Prevent past appointments
         */
        if (appointmentDateTime.isBefore(
                LocalDateTime.now()
        )) {

            throw new RuntimeException(
                    "Cannot book past appointment"
            );
        }

        /*
         * Validate doctor schedule
         */
        DayOfWeek bookingDay =
                appointmentDate.getDayOfWeek();

        List<DoctorSchedule> schedules =
                doctorScheduleRepository
                .findByDoctor(doctor);

        boolean validSchedule =
                schedules.stream().anyMatch(schedule ->

                        schedule.getDay().name().equals(
                                bookingDay.name()
                        )

                                && !appointmentTime
                                .isBefore(schedule.getStartTime())

                                && !appointmentTime
                                .isAfter(schedule.getEndTime())
                );

        if (!validSchedule) {

            throw new RuntimeException(
                    "Doctor is not available at selected time"
            );
        }

        boolean alreadyBooked =
                appointmentRepository
                .existsByDoctorAndAppointmentDateAndAppointmentTime(
                        doctor,
                        appointmentDate,
                        appointmentTime
                );

        if (alreadyBooked) {

            throw new RuntimeException(
                    "Doctor already has appointment at this time"
            );
        }

        Appointment appointment =
                new Appointment();

        appointment.setPatient(patient);

        appointment.setDoctor(doctor);

        appointment.setDepartment(
                request.getDepartment()
        );

        appointment.setReason(
                request.getReason()
        );

        appointment.setAppointmentDate(
                appointmentDate
        );

        appointment.setAppointmentTime(
                appointmentTime
        );

        /*
         * Default status
         */
        appointment.setStatus(
                AppointmentStatus.PENDING
        );

        appointmentRepository.save(appointment);

        return "Appointment booked successfully";
    }

    /*
     * Patient views own appointments
     */
    public List<AppointmentResponse>
    getMyAppointments(
            Authentication authentication
    ) {

        String patientEmail =
                authentication.getName();

        Users patient = userRepository
                .findByEmail(patientEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"
                        ));

        List<Appointment> appointments =
                appointmentRepository.findByPatient(
                        patient
                );

        return appointments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /*
     * Receptionist approves appointment
     */
    public String approveAppointment(Long id) {

        Appointment appointment =
                appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found"
                        ));

        appointment.setStatus(
                AppointmentStatus.APPROVED
        );

        appointmentRepository.save(appointment);

        return "Appointment approved";
    }

    /*
     * Receptionist rejects appointment
     */
    public String rejectAppointment(Long id) {

        Appointment appointment =
                appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found"
                        ));

        appointment.setStatus(
                AppointmentStatus.REJECTED
        );

        appointmentRepository.save(appointment);

        return "Appointment rejected";
    }

    /*
     * Receptionist/Admin views all appointments
     */
    public List<AppointmentResponse>
    getAllAppointments() {

        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /*
     * Convert entity to DTO.
     */
    private AppointmentResponse
    mapToResponse(Appointment appointment) {

        return new AppointmentResponse(

                appointment.getId(),

                appointment.getPatient()
                        .getFullName(),

                appointment.getDoctor()
                        .getFullName(),

                appointment.getDepartment(),

                appointment.getReason(),
appointment.getAppointmentDate(),

appointment.getAppointmentTime(),

appointment.getStatus()
        );
    }

    public List<AppointmentResponse>
getDoctorAppointments(
        Authentication authentication
) {

    String doctorEmail =
            authentication.getName();

    Users doctor = userRepository
            .findByEmail(doctorEmail)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Doctor not found"
                    ));

    List<Appointment> appointments =
            appointmentRepository.findByDoctor(
                    doctor
            );

    return appointments.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
}
}
