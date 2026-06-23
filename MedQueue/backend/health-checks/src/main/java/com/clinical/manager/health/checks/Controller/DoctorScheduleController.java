package com.clinical.manager.health.checks.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.clinical.manager.health.checks.DTO.ApiResponse;
import com.clinical.manager.health.checks.DTO.CreateDoctorScheduleRequest;
import com.clinical.manager.health.checks.Entity.DoctorSchedule;
import com.clinical.manager.health.checks.Service.DoctorScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(
        name = "Doctor Schedule APIs",
        description = "Manages doctor availability schedules and admin schedule operations"
)
@RequestMapping("/api/v1/admin/schedules")
@CrossOrigin("*")
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService scheduleService;

    /*
     * Admin creates schedule
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create doctor schedule",
            description = "Allows admins to create availability schedules for doctors"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Doctor schedule created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid schedule request or validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Doctor resource not found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Schedule conflicts with an existing schedule"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createSchedule(
            @Valid @RequestBody
            CreateDoctorScheduleRequest request
    ) {

        String result = scheduleService.createSchedule(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        result,
                        null
                )
        );
    }

    /*
     * View doctor schedules
     */
    @Operation(
            summary = "Get doctor schedules",
            description = "Returns availability schedules for the selected doctor"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Doctor schedules retrieved successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Doctor resource not found"
            )
    })
    @GetMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<List<DoctorSchedule>>>
    getSchedules(
            @PathVariable Long doctorId
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Doctor schedules retrieved successfully",
                        scheduleService.getDoctorSchedules(doctorId)
                )
        );
    }
}
