package com.clinical.manager.health.checks.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinical.manager.health.checks.DTO.CreateDoctorScheduleRequest;
import com.clinical.manager.health.checks.Entity.DoctorSchedule;
import com.clinical.manager.health.checks.Entity.Users;
import com.clinical.manager.health.checks.Exception.ResourceNotFoundException;
import com.clinical.manager.health.checks.Repository.DoctorScheduleRepository;
import com.clinical.manager.health.checks.Repository.UserRepository;

@Service
public class DoctorScheduleService {

    @Autowired
    private DoctorScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    /*
     * Admin creates doctor schedule
     */
    public String createSchedule(
            CreateDoctorScheduleRequest request
    ) {

        Users doctor = userRepository
                .findById(request.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"
                        ));

        DoctorSchedule schedule =
                new DoctorSchedule();

        schedule.setDoctor(doctor);

        schedule.setDay(request.getDay());

        schedule.setStartTime(
                request.getStartTime()
        );

        schedule.setEndTime(
                request.getEndTime()
        );

        scheduleRepository.save(schedule);

        return "Doctor schedule created successfully";
    }

    /*
     * Get doctor schedules
     */
    public List<DoctorSchedule> getDoctorSchedules(
            Long doctorId
    ) {

        Users doctor = userRepository
                .findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"
                        ));

        return scheduleRepository.findByDoctor(
                doctor
        );
    }
}