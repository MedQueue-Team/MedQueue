package com.clinical.manager.health.checks.DTO;

import java.time.LocalDate;

import com.clinical.manager.health.checks.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatientProfileResponse {

    @Schema(example = "3")
    private Long id;

    @Schema(example = "John Doe")
    private String fullName;

    @Schema(example = "patient@gmail.com")
    private String email;

    @Schema(example = "+263771234567")
    private String phone;

    @Schema(example = "PATIENT")
    private Role role;

    /*
     * Patient details
     */
    @Schema(example = "Male")
    private String gender;

    @Schema(example = "1995-08-15")
    private LocalDate dateOfBirth;

    @Schema(example = "123 Samora Machel Avenue, Harare")
    private String address;

    @Schema(example = "+263772345678")
    private String emergencyContact;

    @Schema(example = "63-1234567A45")
    private String nationalId;

    @Schema(example = "/uploads/profile-pictures/patient-3.jpg")
    private String profilePicture;
}
