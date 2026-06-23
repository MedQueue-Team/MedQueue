package com.clinical.manager.health.checks.DTO;


import com.clinical.manager.health.checks.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/*
 * DTO used when creating
 * doctors or receptionists.
 */
@Getter
@Setter
public class RegisterUserRequest {

    /*
     * Full name of the user.
     */
    @Schema(example = "Dr Jane Smith")
    @NotBlank(message = "full name is required")
    private String fullName;

    /*
     * User email.
     */
    @Schema(example = "doctor@gmail.com")
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    /*
     * Plain text password from request.
     * Will later be encrypted.
     */
    @Schema(example = "Password@123")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    /*
     * User role.
     * Allowed:
     * DOCTOR
     * RECEPTIONIST
     */
    @Schema(example = "DOCTOR")
    @NotNull(message = "Role is required")
    private Role role;

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
}
