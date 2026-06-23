package com.clinical.manager.health.checks.DTO;



import com.clinical.manager.health.checks.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//DTO returned after successful login.
 
@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    // Indicates login success.

    @Schema(example = "true")
    private boolean success;

    // JWT token generated after login.

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50QGdtYWlsLmNvbSJ9.signature")
    private String token;

    /*
     * User role returned to frontend.
     * Used for role-based navigation.
     */
    @Schema(example = "PATIENT")
    private Role role;

    /*
     * Optional response message.
     */
    @Schema(example = "Login successful")
    private String message;
}
