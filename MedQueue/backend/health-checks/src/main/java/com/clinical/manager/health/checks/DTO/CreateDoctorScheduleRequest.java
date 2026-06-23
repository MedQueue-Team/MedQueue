package com.clinical.manager.health.checks.DTO;

import java.time.LocalTime;

import com.clinical.manager.health.checks.enums.DayOfWeekEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDoctorScheduleRequest {

    @Schema(example = "2")
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @Schema(example = "MONDAY")
    @NotNull(message = "Day is required")
    private DayOfWeekEnum day;

    @Schema(example = "08:00:00")
    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @Schema(example = "16:00:00")
    @NotNull(message = "End time is required")
    private LocalTime endTime;
}
