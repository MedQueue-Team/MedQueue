 package com.clinical.manager.health.checks.DTO; 

import com.clinical.manager.health.checks.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * DTO returned after successful
 * user registration.
 */
@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Dr Jane Smith")
    private String fullName;

    @Schema(example = "doctor@gmail.com")
    private String email;

    @Schema(example = "DOCTOR")
    private Role role;

    @Schema(example = "Dental")
    private String department;

    @Schema(example = "+263771234567")
    private String phone;

    @Schema(example = "User registered successfully")
    private String message;
}
