

package com.clinical.manager.health.checks.DTO;

import java.time.LocalDate;

import com.clinical.manager.health.checks.enums.PriorityLevel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/*
 * DTO used when registering a patient.
 */
@Getter
@Setter
public class PatientRegistrationRequest {

    /*
     * Patient full name.
     */
    @Schema(example = "John Doe")
    @NotBlank(message = "Full name is required")
    private String fullName;

    /*
     * Patient date of birth.
     */
    @Schema(example = "1995-08-15")
    private LocalDate dateOfBirth;

    /*
     * Gender.
     */
    @Schema(example = "Male")
    private String gender;

    /*
     * Symptoms described by patient.
     */
    @Schema(example = "Fever and headache")
    private String symptoms;

    /*
     * Department assignment.
     */
    @Schema(example = "Dental")
    @NotBlank(message = "Department is required")
    private String department;

    /*
     * Phone number.
     */
    @Schema(example = "+263771234567")
    @NotBlank(message = "Phone number is required")
    private String phone;

    /*
     * Priority level.
     */
    @Schema(example = "NORMAL")
    private PriorityLevel priorityLevel;
}
