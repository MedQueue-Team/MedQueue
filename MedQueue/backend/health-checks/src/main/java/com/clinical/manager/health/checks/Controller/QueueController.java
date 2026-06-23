package com.clinical.manager.health.checks.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinical.manager.health.checks.DTO.ApiResponse;
import com.clinical.manager.health.checks.DTO.PatientRegistrationRequest;
import com.clinical.manager.health.checks.DTO.QueueResponse;
import com.clinical.manager.health.checks.Service.QueueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(
        name = "Queue APIs",
        description = "Handles patient queue registration and queue state management"
)
@RequestMapping("/api/v1/queue")
@CrossOrigin("*")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {

        this.queueService = queueService;
    }

    /*
     * Register patient and add them to the queue.
     */
    @Operation(
            summary = "Register patient in queue",
            description = "Registers a patient and adds them to the clinic queue"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Patient registered and added to queue successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid patient registration request or validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Patient is already registered in the queue"
            )
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<QueueResponse>> registerPatient(
            @Valid @RequestBody PatientRegistrationRequest request
    ) {

        QueueResponse response = queueService.registerPatient(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Patient registered and added to queue successfully",
                        response
                )
        );
    }
}
