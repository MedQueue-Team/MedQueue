package com.clinical.manager.health.checks.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.clinical.manager.health.checks.DTO.ApiResponse;
import com.clinical.manager.health.checks.DTO.AppointmentResponse;
import com.clinical.manager.health.checks.DTO.BookAppointmentRequest;
import com.clinical.manager.health.checks.Service.AppointmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(
        name = "Appointment APIs",
        description = "Handles appointment booking, retrieval, approval, and rejection"
)
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    /*
     * Patient books appointment
     */
    @Operation(
            summary = "Book appointment",
            description = "Allows authenticated patients to book appointments with available doctors"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Appointment booked successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid appointment request or validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Doctor or patient resource not found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Appointment conflicts with an existing booking"
            )
    })
    @PostMapping("/book")
    public ResponseEntity<ApiResponse<String>> bookAppointment(
            @Valid @RequestBody BookAppointmentRequest request,
            Authentication authentication
    ) {

        String result = appointmentService.bookAppointment(
                request,
                authentication
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        result,
                        null
                )
        );
    }

    /*
     * Patient views own appointments
     */
    @Operation(
            summary = "Get my appointments",
            description = "Returns appointments belonging to the currently authenticated patient"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Appointments retrieved successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Patient resource not found"
            )
    })
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getMyAppointments(
            Authentication authentication
    ) {

        List<AppointmentResponse> appointments =
                appointmentService.getMyAppointments(authentication);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Appointments retrieved successfully",
                        appointments
                )
        );
    }

    /*
     * Receptionist/Admin views all appointments
     */
    @Operation(
            summary = "Get all appointments",
            description = "Returns all appointments for receptionist or admin review"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Appointments retrieved successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            )
    })
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAllAppointments() {

        List<AppointmentResponse> appointments =
                appointmentService.getAllAppointments();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Appointments retrieved successfully",
                        appointments
                )
        );
    }

    /*
     * Approve appointment
     */
    @Operation(
            summary = "Approve appointment",
            description = "Approves a pending appointment by its appointment ID"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Appointment approved successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Appointment cannot be approved in its current state"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Appointment not found"
            )
    })
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<String>> approveAppointment(
            @PathVariable Long id
    ) {

        String result = appointmentService.approveAppointment(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        result,
                        null
                )
        );
    }

    /*
     * Reject appointment
     */
    @Operation(
            summary = "Reject appointment",
            description = "Rejects a pending appointment by its appointment ID"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Appointment rejected successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Appointment cannot be rejected in its current state"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Appointment not found"
            )
    })
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<String>> rejectAppointment(
            @PathVariable Long id
    ) {

        String result = appointmentService.rejectAppointment(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        result,
                        null
                )
        );
    }

    /*
     * Doctor views own appointments
     */
    @Operation(
            summary = "Get doctor appointments",
            description = "Returns appointments assigned to the currently authenticated doctor"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Doctor appointments retrieved successfully"
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
    @GetMapping("/doctor")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getDoctorAppointments(
            Authentication authentication
    ) {

        List<AppointmentResponse> appointments =
                appointmentService.getDoctorAppointments(authentication);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Doctor appointments retrieved successfully",
                        appointments
                )
        );
    }
}
