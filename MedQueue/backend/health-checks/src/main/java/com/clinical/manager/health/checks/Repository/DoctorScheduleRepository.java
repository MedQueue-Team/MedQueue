package com.clinical.manager.health.checks.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinical.manager.health.checks.Entity.DoctorSchedule;
import com.clinical.manager.health.checks.Entity.Users;

public interface DoctorScheduleRepository
        extends JpaRepository<DoctorSchedule, Long> {

    List<DoctorSchedule> findByDoctor(
            Users doctor
    );

    Optional<DoctorSchedule> findByDoctorAndDay(
            Users doctor,
            DayOfWeek day
    );
}