package com.clinical.manager.health.checks.DTO;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorResponse {

    @Schema(example = "2026-05-20T10:00:00")
    private LocalDateTime timestamp;

    @Schema(example = "400")
    private int status;

    @Schema(example = "Bad Request")
    private String error;

    @Schema(example = "Validation failed")
    private String message;

    public ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message
    ) {

        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
