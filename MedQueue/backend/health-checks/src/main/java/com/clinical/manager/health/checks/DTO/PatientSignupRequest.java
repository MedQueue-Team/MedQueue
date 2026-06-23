package com.clinical.manager.health.checks.DTO;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/*
 * Patient signup request
 */
@Getter
@Setter
public class PatientSignupRequest {

    @Schema(example = "John Doe")
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Schema(example = "patient@gmail.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(example = "Password@123")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Schema(example = "+263771234567")
    @NotBlank(message = "Phone number is required")
    private String phone;

    /*
     * Personal details
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
}
