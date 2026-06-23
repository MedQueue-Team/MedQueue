package com.clinical.manager.health.checks.Controller;



import com.clinical.manager.health.checks.DTO.ApiResponse;
import com.clinical.manager.health.checks.DTO.LoginRequest;
import com.clinical.manager.health.checks.DTO.LoginResponse;
import com.clinical.manager.health.checks.Service.AuthService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Handles authentication endpoints.
 */
@Tag(
    name = "Authentication APIs",
    description = "Handles user authentication and JWT login"
)
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    /*
     * Login endpoint.
     *
     * URL:
     * POST /api/v1/auth/login
     */
    @Operation(
            summary = "Login user",
            description = "Authenticates user and returns JWT token"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Login successful"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid login request or validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Invalid email or password"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(

            @Valid @RequestBody LoginRequest request) {

        /*
         * Delegate authentication to service layer.
         */
        LoginResponse response =
                authService.login(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        response
                )
        );
    }
}
