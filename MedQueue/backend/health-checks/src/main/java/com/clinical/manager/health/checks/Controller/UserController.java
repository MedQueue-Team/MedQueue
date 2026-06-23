package com.clinical.manager.health.checks.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinical.manager.health.checks.DTO.RegisterUserRequest;
import com.clinical.manager.health.checks.DTO.UserResponse;
import com.clinical.manager.health.checks.Service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/*
 * Handles user management APIs.
 */
@RestController
@Tag(name = "User APIs")
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    /*
     * Public patient registration
     *
     * URL:
     * POST /api/v1/users/register
     */
    @Operation(
            summary = "Register user",
            description = "Creates a new user account from the submitted registration details"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid registration request or validation error"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "User already exists"
            )
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(

            @Valid
            @RequestBody
            RegisterUserRequest request) {

        /*
         * Delegate business logic to service.
         */
        UserResponse response =
                userService.registerUser(request);

        return ResponseEntity.ok(response);
    }
}
