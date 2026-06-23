package com.clinical.manager.health.checks.DTO;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * DTO used for admin dashboard statistics.
 */
@Getter
@Setter
@AllArgsConstructor
public class AdminDashboardResponse {

    /*
     * Total patients registered today.
     */
    @Schema(example = "24")
    private long totalPatientsToday;

    /*
     * Queue entries currently active.
     */
    @Schema(example = "8")
    private long activeQueues;

    /*
     * Completed consultations.
     */
    @Schema(example = "16")
    private long completedConsultations;

    /*
     * Average waiting time in minutes.
     */
    @Schema(example = "35")
    private long averageWaitingTime;

    /*
     * Total doctors.
     */
    @Schema(example = "6")
    private long totalDoctors;

    /*
     * Total receptionists.
     */
    @Schema(example = "3")
    private long totalReceptionists;
}
