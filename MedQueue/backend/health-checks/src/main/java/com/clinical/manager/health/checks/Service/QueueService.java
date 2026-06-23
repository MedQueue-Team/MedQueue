package com.clinical.manager.health.checks.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinical.manager.health.checks.DTO.PatientRegistrationRequest;
import com.clinical.manager.health.checks.DTO.QueueResponse;
import com.clinical.manager.health.checks.Entity.Patient;
import com.clinical.manager.health.checks.Entity.Queue;
import com.clinical.manager.health.checks.Repository.PatientRepository;
import com.clinical.manager.health.checks.Repository.QueueRepository;
import com.clinical.manager.health.checks.enums.PriorityLevel;
import com.clinical.manager.health.checks.enums.QueueStatus;

import lombok.RequiredArgsConstructor;

/*
 * Handles patient registration
 * and queue creation logic.
 */
@RequiredArgsConstructor
@Service

public class QueueService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private QueueRepository queueRepository;

    private final QueueNotificationService  
      notificationService;
     

    /*
     * Registers patient and creates queue entry.
     */
    public QueueResponse registerPatient(
            PatientRegistrationRequest request) {

        /*
         * Create patient entity.
         */
        Patient patient = new Patient();

        patient.setFullName(request.getFullName());

        patient.setDateOfBirth(request.getDateOfBirth());

        patient.setGender(request.getGender());

        patient.setSymptoms(request.getSymptoms());

        patient.setDepartment(request.getDepartment());

        patient.setPhone(request.getPhone());

        PriorityLevel priorityLevel =
                request.getPriorityLevel() != null
                        ? request.getPriorityLevel()
                        : determinePriority(
                                request.getSymptoms()
                        );

        patient.setPriorityLevel(
                priorityLevel
        );
        

        /*
         * Save patient first.
         */
        Patient savedPatient =
                patientRepository.save(patient);

        /*
         * Generate queue number.
         */
        String queueNumber =
                String.format(
                        "A%03d",
                        savedPatient.getId()
                );

        /*
         * Create queue entry.
         */
        Queue queue = new Queue();

        queue.setQueueNumber(queueNumber);

        queue.setStatus(QueueStatus.WAITING);

        queue.setPatient(savedPatient);

        /*
         * Save queue entry.
         */
        queueRepository.save(queue);
         notificationService.sendQueueUpdate(
        "New patient added to queue"
);

        /*
         * Return response DTO.
         */
        return new QueueResponse(

                queueNumber,

                savedPatient.getFullName(),

                savedPatient.getDepartment(),

                savedPatient.getPriorityLevel(),

                QueueStatus.WAITING,

                "Patient registered successfully"

                
        );
    }

    /*
     * Determines priority based on symptoms.
     */
    private PriorityLevel determinePriority(String symptoms) {
        if (symptoms == null || symptoms.trim().isEmpty()) {
            return PriorityLevel.NORMAL;
        }
        String lowerSymptoms = symptoms.toLowerCase();
        if (lowerSymptoms.contains("chest pain") || lowerSymptoms.contains("difficulty breathing") ||
            lowerSymptoms.contains("severe pain") || lowerSymptoms.contains("unconscious")) {
            return PriorityLevel.EMERGENCY;
        } else if (lowerSymptoms.contains("fever") || lowerSymptoms.contains("high blood pressure") ||
                   lowerSymptoms.contains("infection") || lowerSymptoms.contains("broken bone")) {
            return PriorityLevel.URGENT;
        } else {
            return PriorityLevel.NORMAL;
        }
    }

}
