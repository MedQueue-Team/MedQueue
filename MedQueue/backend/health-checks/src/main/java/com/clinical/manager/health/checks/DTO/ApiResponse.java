package com.clinical.manager.health.checks.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public class ApiResponse<T> {

    @Schema(example = "true")
    private boolean success;

    @Schema(example = "Request processed successfully")
    private String message;

    @Schema(description = "Response payload")
    private T data;

    public ApiResponse(
            boolean success,
            String message,
            T data
    ) {

        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
