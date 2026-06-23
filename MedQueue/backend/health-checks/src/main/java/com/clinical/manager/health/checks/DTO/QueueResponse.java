package com.clinical.manager.health.checks.DTO;



import com.clinical.manager.health.checks.enums.PriorityLevel;
import com.clinical.manager.health.checks.enums.QueueStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * DTO returned after queue creation.
 */
@Getter
@Setter
@AllArgsConstructor
public class QueueResponse {

    @Schema(example = "Q-001")
    private String queueNumber;

    @Schema(example = "John Doe")
    private String patientName;

    @Schema(example = "Dental")
    private String department;

    @Schema(example = "NORMAL")
    private PriorityLevel priorityLevel;

    @Schema(example = "WAITING")
    private QueueStatus status;

    @Schema(example = "Patient registered and added to queue successfully")
    private String message;
}
