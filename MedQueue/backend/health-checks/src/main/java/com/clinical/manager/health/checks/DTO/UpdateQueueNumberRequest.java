package com.clinical.manager.health.checks.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class UpdateQueueNumberRequest {

    @Schema(example = "Q-010")
    @NotBlank(message = "Queue number is required")
    private String queueNumber;

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(
            String queueNumber
    ) {
        this.queueNumber = queueNumber;
    }
}
