package com.clinical.manager.health.checks.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clinical.manager.health.checks.DTO.ApiResponse;
import com.clinical.manager.health.checks.DTO.PatientProfileResponse;
import com.clinical.manager.health.checks.Service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Patient Profile APIs")
@RequestMapping("/api/v1/patient")
@CrossOrigin("*")
public class PatientProfileController {

    @Autowired
    private PatientService patientService;

    /*
     * Get logged-in patient profile
     */
    @PreAuthorize("hasRole('PATIENT')")
    @Operation(
            summary = "Get my patient profile",
            description = "Returns profile details for the currently authenticated patient"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Patient profile retrieved successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Patient profile not found"
            )
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<PatientProfileResponse>>
    getMyProfile(Authentication authentication) {

        PatientProfileResponse profile =
                patientService.getMyProfile(authentication);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Patient profile retrieved successfully",
                        profile
                )
        );
    }

    /*
     * Upload profile picture
     */
    @PreAuthorize("hasRole('PATIENT')")
    @Operation(
            summary = "Upload profile picture",
            description = "Allows the authenticated patient to upload or update their profile picture"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Profile picture uploaded successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Missing, empty, or invalid profile picture upload"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Patient profile not found"
            )
    })
    @PostMapping(
            value = "/profile-picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<String>> uploadProfilePicture(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) throws IOException {

        String result = patientService.uploadProfilePicture(
                file,
                authentication
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Profile picture uploaded successfully",
                        result
                )
        );
    }
}
