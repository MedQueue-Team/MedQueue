package com.clinical.manager.health.checks.DTO;



import com.clinical.manager.health.checks.enums.PriorityLevel;
import com.clinical.manager.health.checks.enums.QueueStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * DTO used when displaying
 * queue information to doctors.
 */
@Getter
@Setter
@AllArgsConstructor
public class QueueItemResponse {

    @Schema(example = "5")
    private Long queueId;

    @Schema(example = "Q-001")
    private String queueNumber;

    @Schema(example = "John Doe")
    private String patientName;

    @Schema(example = "30")
    private int age;

    @Schema(example = "Male")
    private String gender;

    @Schema(example = "Fever and headache")
    private String symptoms;

    @Schema(example = "Dental")
    private String department;

    @Schema(example = "NORMAL")
    private PriorityLevel priorityLevel;

    @Schema(example = "WAITING")
    private QueueStatus status;
}
