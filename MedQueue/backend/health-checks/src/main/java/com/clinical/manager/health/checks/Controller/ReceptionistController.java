package com.clinical.manager.health.checks.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.clinical.manager.health.checks.DTO.ApiResponse;
import com.clinical.manager.health.checks.DTO.AppointmentResponse;
import com.clinical.manager.health.checks.DTO.QueueItemResponse;
import com.clinical.manager.health.checks.DTO.UpdateDepartmentRequest;
import com.clinical.manager.health.checks.DTO.UpdatePriorityRequest;
import com.clinical.manager.health.checks.DTO.UpdateQueueNumberRequest;
import com.clinical.manager.health.checks.Service.AppointmentService;
import com.clinical.manager.health.checks.Service.ReceptionistQueueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Receptionist APIs")
@RequestMapping("/api/v1/receptionist")
@CrossOrigin("*")
public class ReceptionistController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ReceptionistQueueService receptionistQueueService;

    /*
     * View all appointments
     */
    @PreAuthorize(
            "hasRole('RECEPTIONIST') or hasRole('ADMIN')"
    )
    @Operation(
            summary = "Get receptionist appointments",
            description = "Returns all appointments for receptionist or admin appointment management"
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
    @GetMapping("/appointments")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>>
    getAllAppointments() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Appointments retrieved successfully",
                        appointmentService.getAllAppointments()
                )
        );
    }

    /*
     * Approve appointment
     */
    @PreAuthorize(
            "hasRole('RECEPTIONIST') or hasRole('ADMIN')"
    )
    @Operation(
            summary = "Approve appointment",
            description = "Allows receptionists or admins to approve a pending appointment"
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
    @PutMapping("/appointments/{id}/approve")
    public ResponseEntity<ApiResponse<String>>
    approveAppointment(
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
    @PreAuthorize(
            "hasRole('RECEPTIONIST') or hasRole('ADMIN')"
    )
    @Operation(
            summary = "Reject appointment",
            description = "Allows receptionists or admins to reject a pending appointment"
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
    @PutMapping("/appointments/{id}/reject")
    public ResponseEntity<ApiResponse<String>>
    rejectAppointment(
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
     * View queue
     */
    @PreAuthorize(
            "hasRole('RECEPTIONIST') or hasRole('ADMIN')"
    )
    @Operation(
            summary = "Get queue",
            description = "Returns the clinic queue for receptionist or admin queue management"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Queue retrieved successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            )
    })
    @GetMapping("/queue")
    public ResponseEntity<List<QueueItemResponse>>
    getQueue() {

        return ResponseEntity.ok(
                receptionistQueueService.getQueue()
        );
    }

    /*
     * Update priority
     */
    @PreAuthorize(
            "hasRole('RECEPTIONIST') or hasRole('ADMIN')"
    )
    @Operation(
            summary = "Update queue priority",
            description = "Updates the priority level for a selected queue item"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Queue priority updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid priority request or validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Queue item not found"
            )
    })
    @PutMapping("/queue/{id}/priority")
    public ResponseEntity<ApiResponse<String>>
    updatePriority(
            @PathVariable Long id,
            @Valid @RequestBody
            UpdatePriorityRequest request
    ) {

        String result = receptionistQueueService
                .updatePriority(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        result,
                        null
                )
        );
    }

    /*
     * Update department
     */
    @PreAuthorize(
            "hasRole('RECEPTIONIST') or hasRole('ADMIN')"
    )
    @Operation(
            summary = "Update queue department",
            description = "Updates the assigned department for a selected queue item"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Queue department updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid department request or validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Queue item not found"
            )
    })
    @PutMapping("/queue/{id}/department")
    public ResponseEntity<ApiResponse<String>>
    updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody
            UpdateDepartmentRequest request
    ) {

        String result = receptionistQueueService
                .updateDepartment(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        result,
                        null
                )
        );
    }

    /*
     * Manual queue number
     */
    @PreAuthorize(
            "hasRole('RECEPTIONIST') or hasRole('ADMIN')"
    )
    @Operation(
            summary = "Update queue number",
            description = "Manually updates the queue number for a selected queue item"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Queue number updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid queue number request or validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Queue item not found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Queue number conflicts with an existing queue item"
            )
    })
    @PutMapping("/queue/{id}/number")
    public ResponseEntity<ApiResponse<String>>
    updateQueueNumber(
            @PathVariable Long id,
            @Valid @RequestBody
            UpdateQueueNumberRequest request
    ) {

        String result = receptionistQueueService
                .updateQueueNumber(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        result,
                        null
                )
        );
    }

    /*
     * Remove queue
     */
    @PreAuthorize(
            "hasRole('RECEPTIONIST') or hasRole('ADMIN')"
    )
    @Operation(
            summary = "Delete queue item",
            description = "Removes a selected patient entry from the clinic queue"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Queue item deleted successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Queue item not found"
            )
    })
    @DeleteMapping("/queue/{id}")
    public ResponseEntity<ApiResponse<String>>
    deleteQueue(
            @PathVariable Long id
    ) {

        String result = receptionistQueueService.deleteQueue(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        result,
                        null
                )
        );
    }
}
