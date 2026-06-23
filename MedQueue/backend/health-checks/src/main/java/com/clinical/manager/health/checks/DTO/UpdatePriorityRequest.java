package com.clinical.manager.health.checks.DTO;

import com.clinical.manager.health.checks.enums.PriorityLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class UpdatePriorityRequest {

    @Schema(example = "URGENT")
    @NotNull(message = "Priority level is required")
    private PriorityLevel priorityLevel;

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(
            PriorityLevel priorityLevel
    ) {
        this.priorityLevel = priorityLevel;
    }
}
