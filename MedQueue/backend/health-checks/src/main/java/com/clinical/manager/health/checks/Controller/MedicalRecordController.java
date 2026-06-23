package com.clinical.manager.health.checks.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.clinical.manager.health.checks.DTO.ApiResponse;
import com.clinical.manager.health.checks.DTO.CreateMedicalRecordRequest;
import com.clinical.manager.health.checks.DTO.MedicalRecordResponse;
import com.clinical.manager.health.checks.Service.MedicalRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Medical Record APIs")
@RequestMapping("/api/v1/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(
            MedicalRecordService medicalRecordService
    ) {

        this.medicalRecordService = medicalRecordService;
    }

    /*
     * Doctor creates record
     */
    @Operation(
            summary = "Create medical record",
            description = "Allows doctors to create a medical record for a patient consultation"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Medical record created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid medical record request or validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Patient or consultation resource not found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Medical record already exists for this consultation"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createMedicalRecord(
            @Valid @RequestBody CreateMedicalRecordRequest request,
            Authentication authentication
    ) {

        String result = medicalRecordService.createRecord(
                request,
                authentication
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Medical record created successfully",
                        result
                )
        );
    }

    /*
     * Patient views own records
     */
    @Operation(
            summary = "Get my medical records",
            description = "Returns medical records belonging to the currently authenticated patient"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Medical records retrieved successfully"
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
    public ResponseEntity<ApiResponse<List<MedicalRecordResponse>>> getMyRecords(
            Authentication authentication
    ) {

        List<MedicalRecordResponse> records =
                medicalRecordService.getMyRecords(authentication);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Medical records retrieved successfully",
                        records
                )
        );
    }

    /*
     * View patient history
     */
    @Operation(
            summary = "Get patient medical records",
            description = "Returns medical record history for the selected patient"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Patient medical records retrieved successfully"
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
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<MedicalRecordResponse>>> getPatientRecords(
            @PathVariable Long patientId
    ) {

        List<MedicalRecordResponse> records =
                medicalRecordService.getPatientRecords(patientId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Patient medical records retrieved successfully",
                        records
                )
        );
    }

    /*
     * Complete record
     */
    @Operation(
            summary = "Complete medical record",
            description = "Marks a medical record as completed by its record ID"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Medical record completed successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Medical record cannot be completed in its current state"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Medical record not found"
            )
    })
    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<String>> completeRecord(
            @PathVariable Long id
    ) {

        String result = medicalRecordService.completeRecord(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Medical record completed successfully",
                        result
                )
        );
    }
}
