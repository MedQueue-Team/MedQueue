package com.clinical.manager.health.checks.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinical.manager.health.checks.DTO.QueueItemResponse;
import com.clinical.manager.health.checks.Service.DoctorQueueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/*
 * Handles doctor queue APIs.
 */
@RestController
@Tag(name = "Doctor APIs")
@RequestMapping("/api/v1/doctor")
@CrossOrigin("*")
public class DoctorQueueController {

    /*
     * Doctor queue service dependency.
     *
     * Constructor injection is preferred
     * over field injection because:
     * - dependencies become immutable
     * - easier testing
     * - cleaner architecture
     */
    private final DoctorQueueService doctorQueueService;

    /*
     * Constructor injection.
     */
    public DoctorQueueController(
            DoctorQueueService doctorQueueService
    ) {

        this.doctorQueueService =
                doctorQueueService;
    }

    /*
     * Fetch waiting queue.
     *
     * GET /api/v1/doctor/queue
     */
    @PreAuthorize("hasRole('DOCTOR')")
    @Operation(
            summary = "Get waiting queue",
            description = "Returns the list of queued patients waiting for the authenticated doctor"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Waiting queue retrieved successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            )
    })
    @GetMapping("/queue")
    public ResponseEntity<List<QueueItemResponse>>
    getWaitingQueue() {

        List<QueueItemResponse> queueList =
                doctorQueueService.getWaitingQueue();

        return ResponseEntity.ok(queueList);
    }

    /*
     * Call patient.
     *
     * PUT /api/v1/doctor/call/{id}
     */
    @PreAuthorize("hasRole('DOCTOR')")
    @Operation(
            summary = "Call patient",
            description = "Marks a queued patient as called by the authenticated doctor"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Patient called successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Queue item cannot be called in its current state"
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
    @PutMapping("/call/{id}")
    public ResponseEntity<Map<String, Object>>
    callPatient(
            @PathVariable Long id
    ) {

        String message =
                doctorQueueService.callPatient(id);

        /*
         * Standard API response.
         */
        Map<String, Object> response =
                new HashMap<>();

        response.put("success", true);

        response.put("message", message);

        return ResponseEntity.ok(response);
    }

    /*
     * Start consultation.
     *
     * PUT /api/v1/doctor/start/{id}
     */
    @PreAuthorize("hasRole('DOCTOR')")
    @Operation(
            summary = "Start consultation",
            description = "Starts a consultation for the selected queued patient"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Consultation started successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Consultation cannot be started in the current queue state"
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
    @PutMapping("/start/{id}")
    public ResponseEntity<Map<String, Object>>
    startConsultation(
            @PathVariable Long id
    ) {

        String message =
                doctorQueueService
                        .startConsultation(id);

        /*
         * Standard API response.
         */
        Map<String, Object> response =
                new HashMap<>();

        response.put("success", true);

        response.put("message", message);

        return ResponseEntity.ok(response);
    }

    /*
     * Complete consultation.
     *
     * PUT /api/v1/doctor/complete/{id}
     */
    @PreAuthorize("hasRole('DOCTOR')")
    @Operation(
            summary = "Complete consultation",
            description = "Completes the consultation and updates the patient's queue status"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Consultation completed successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Consultation cannot be completed in the current queue state"
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
    @PutMapping("/complete/{id}")
    public ResponseEntity<Map<String, Object>>
    completeConsultation(
            @PathVariable Long id
    ) {

        String message =
                doctorQueueService
                        .completeConsultation(id);

        /*
         * Standard API response.
         */
        Map<String, Object> response =
                new HashMap<>();

        response.put("success", true);

        response.put("message", message);

        return ResponseEntity.ok(response);
    }
}
