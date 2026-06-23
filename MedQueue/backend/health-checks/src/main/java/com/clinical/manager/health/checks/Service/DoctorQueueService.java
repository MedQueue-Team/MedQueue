package com.clinical.manager.health.checks.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinical.manager.health.checks.DTO.QueueItemResponse;
import com.clinical.manager.health.checks.Entity.Patient;
import com.clinical.manager.health.checks.Entity.Queue;
import com.clinical.manager.health.checks.Exception.ResourceNotFoundException;
import com.clinical.manager.health.checks.Repository.QueueRepository;
import com.clinical.manager.health.checks.enums.PriorityLevel;
import com.clinical.manager.health.checks.enums.QueueStatus;

/*
 * Handles doctor queue operations.
 */
@Service
public class DoctorQueueService {

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private QueueNotificationService notificationService;

    /*
     * Fetch all waiting patients.
     */
    public List<QueueItemResponse> getWaitingQueue() {

        /*
         * Fetch queue entries with WAITING status.
         */
        List<Queue> queues =
                queueRepository.findByStatus(
                        QueueStatus.WAITING
                );

        /*
         * Sort queue by:
         * 1. Priority level
         * 2. Arrival time
         */
        queues.sort(

                Comparator.<Queue>comparingInt(

                        queue -> getPriorityWeight(
                                queue.getPatient()
                                        .getPriorityLevel()
                        )

                ).thenComparing(
                        Queue::getCreatedAt
                )
        );

        /*
         * Convert entities into DTOs.
         */
        return queues.stream()

                .map(this::mapToResponse)

                .collect(Collectors.toList());
    }

    /*
     * Marks patient as CALLED.
     */
    public String callPatient(Long queueId) {

        Queue queue = queueRepository
                .findById(queueId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Queue entry not found"
                        ));

        /*
         * Only waiting patients can be called.
         */
        if (queue.getStatus() != QueueStatus.WAITING) {

            throw new RuntimeException(
                    "Only waiting patients can be called"
            );
        }

        /*
         * Update queue status.
         */
        queue.setStatus(
                QueueStatus.CALLED
        );

        queueRepository.save(queue);

        /*
         * Send live update notification.
         */
        notificationService.sendQueueUpdate(
                "Patient called"
        );

        return "Patient called successfully";
    }

    /*
     * Marks consultation as active.
     */
    public String startConsultation(Long queueId) {

        Queue queue = queueRepository
                .findById(queueId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Queue entry not found"
                        ));

        /*
         * Only called patients can start consultation.
         */
        if (queue.getStatus() != QueueStatus.CALLED) {

            throw new RuntimeException(
                    "Patient must be called first"
            );
        }

        /*
         * Update queue status.
         */
        queue.setStatus(
                QueueStatus.IN_CONSULTATION
        );

        queueRepository.save(queue);

        /*
         * Send live update notification.
         */
        notificationService.sendQueueUpdate(
                "Consultation started"
        );

        return "Consultation started";
    }

    /*
     * Completes patient consultation.
     */
    public String completeConsultation(Long queueId) {

        Queue queue = queueRepository
                .findById(queueId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Queue entry not found"
                        ));

        /*
         * Only active consultations can be completed.
         */
        if (queue.getStatus() != QueueStatus.IN_CONSULTATION) {

            throw new RuntimeException(
                    "Consultation has not started"
            );
        }

        /*
         * Update queue status.
         */
        queue.setStatus(
                QueueStatus.COMPLETED
        );

        /*
         * Store completion timestamp.
         */
        queue.setCompletedAt(
                LocalDateTime.now()
        );

        queueRepository.save(queue);

        /*
         * Send live update notification.
         */
        notificationService.sendQueueUpdate(
                "Consultation completed"
        );

        return "Consultation completed successfully";
    }

    /*
     * Converts priority levels into sorting weights.
     *
     * Lower number = higher priority.
     */
    private int getPriorityWeight(
            PriorityLevel priorityLevel
    ) {

        if (priorityLevel == null) {

            return 3;
        }

        return switch (priorityLevel) {

            case EMERGENCY -> 1;

            case URGENT -> 2;

            case NORMAL -> 3;
        };
    }

    /*
     * Converts Queue entity into response DTO.
     */
    private QueueItemResponse mapToResponse(
            Queue queue
    ) {

        Patient patient = queue.getPatient();

        return new QueueItemResponse(

                queue.getId(),

                queue.getQueueNumber(),

                patient.getFullName(),

                calculateAge(patient.getDateOfBirth()),

                patient.getGender(),

                patient.getSymptoms(),

                patient.getDepartment(),

                patient.getPriorityLevel(),

                queue.getStatus()
        );
    }

    private int calculateAge(LocalDate dateOfBirth) {

        if (dateOfBirth == null) {

            return 0;
        }

        return Period.between(
                dateOfBirth,
                LocalDate.now()
        ).getYears();
    }
}
