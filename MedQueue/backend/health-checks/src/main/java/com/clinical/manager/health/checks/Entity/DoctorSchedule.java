package com.clinical.manager.health.checks.Entity;

import java.time.LocalTime;

import com.clinical.manager.health.checks.enums.DayOfWeekEnum;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctor_schedules")

@Getter
@Setter
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Doctor owner
     */
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Users doctor;

    /*
     * Day
     */
    @Enumerated(EnumType.STRING)
    private DayOfWeekEnum day;

    /*
     * Start time
     */
    private LocalTime startTime;

    /*
     * End time
     */
    private LocalTime endTime;

    /*
     * Active schedule
     */
    private boolean active = true;
}