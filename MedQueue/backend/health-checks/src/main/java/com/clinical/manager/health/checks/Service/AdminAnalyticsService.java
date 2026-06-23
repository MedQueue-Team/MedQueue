package com.clinical.manager.health.checks.Service;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinical.manager.health.checks.DTO.AdminDashboardResponse;
import com.clinical.manager.health.checks.Entity.Queue;
import com.clinical.manager.health.checks.Repository.QueueRepository;
import com.clinical.manager.health.checks.Repository.UserRepository;
import com.clinical.manager.health.checks.enums.QueueStatus;
import com.clinical.manager.health.checks.enums.Role;

/*
 * Handles admin dashboard analytics.
 */
@Service
public class AdminAnalyticsService {

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private UserRepository userRepository;

    /*
     * Fetch dashboard statistics.
     */
    public AdminDashboardResponse getDashboardStats() {

        /*
         * Start of today.
         */
        LocalDateTime today =
                LocalDate.now().atStartOfDay();

        /*
         * Total patients registered today.
         */
        long totalPatientsToday =
                queueRepository
                        .countByCreatedAtAfter(today);

        /*
         * Active queues.
         */
        long activeQueues =
                queueRepository
                        .countByStatus(
                                QueueStatus.WAITING
                        );

        /*
         * Completed consultations.
         */
        long completedConsultations =
                queueRepository
                        .countByStatus(
                                QueueStatus.COMPLETED
                        );

        /*
         * Total doctors.
         */
        long totalDoctors =
                userRepository
                        .countByRole(Role.DOCTOR);

        /*
         * Total receptionists.
         */
        long totalReceptionists =
                userRepository
                        .countByRole(
                                Role.RECEPTIONIST
                        );

        /*
         * Calculate average waiting time.
         */
        long averageWaitingTime =
                calculateAverageWaitingTime();

        /*
         * Return dashboard DTO.
         */
        return new AdminDashboardResponse(

                totalPatientsToday,

                activeQueues,

                completedConsultations,

                averageWaitingTime,

                totalDoctors,

                totalReceptionists
        );
    }

    /*
     * Calculates average consultation duration.
     */
    private long calculateAverageWaitingTime() {

        List<Queue> completedQueues =
                queueRepository.findByStatus(
                        QueueStatus.COMPLETED
                );

        /*
         * Prevent division by zero.
         */
        if (completedQueues.isEmpty()) {

            return 0;
        }

        long totalMinutes = 0;
        long completedWithTimestamps = 0;

        /*
         * Sum consultation durations.
         */
        for (Queue queue : completedQueues) {

            if (queue.getCompletedAt() != null &&
                    queue.getCreatedAt() != null) {

                long minutes = Duration.between(

                        queue.getCreatedAt(),

                        queue.getCompletedAt()

                ).toMinutes();

                totalMinutes += minutes;
                completedWithTimestamps++;
            }
        }

        if (completedWithTimestamps == 0) {

            return 0;
        }

        /*
         * Return average minutes.
         */
        return totalMinutes /
                completedWithTimestamps;
    }
}
