package com.clinical.manager.health.checks.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinical.manager.health.checks.DTO.AdminDashboardResponse;
import com.clinical.manager.health.checks.Service.AdminAnalyticsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/*
 * Handles admin dashboard APIs.
 */
@RestController
@Tag(
        name = "Admin Analytics APIs",
        description = "Provides admin dashboard analytics and statistics"
)
@RequestMapping("/api/v1/admin")
@CrossOrigin("*")
public class AdminAnalyticsController {

    @Autowired
    private AdminAnalyticsService analyticsService;

    /*
     * Fetch admin dashboard statistics.
     *
     * GET /api/v1/admin/dashboard
     */

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get admin dashboard",
            description = "Returns dashboard statistics and analytics for authenticated admin users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dashboard statistics retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            )
    })
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse>
    getDashboard() {

        AdminDashboardResponse response =
                analyticsService.getDashboardStats();

        return ResponseEntity.ok(response);
    }
}
