package com.clinical.manager.health.checks.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class UpdateDepartmentRequest {

    @Schema(example = "Dental")
    @NotBlank(message = "Department is required")
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
