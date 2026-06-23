package com.clinical.manager.health.checks.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinical.manager.health.checks.DTO.QueueItemResponse;
import com.clinical.manager.health.checks.DTO.UpdateDepartmentRequest;
import com.clinical.manager.health.checks.DTO.UpdatePriorityRequest;
import com.clinical.manager.health.checks.DTO.UpdateQueueNumberRequest;
import com.clinical.manager.health.checks.Entity.Patient;
import com.clinical.manager.health.checks.Entity.Queue;
import com.clinical.manager.health.checks.Exception.ResourceNotFoundException;
import com.clinical.manager.health.checks.Repository.QueueRepository;

@Service
public class ReceptionistQueueService {

    @Autowired
    private QueueRepository queueRepository;

    /*
     * View all queue entries
     */
    public List<QueueItemResponse> getQueue() {

        return queueRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /*
     * Update patient priority
     */
    public String updatePriority(
            Long id,
            UpdatePriorityRequest request
    ) {

        Queue queue = queueRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Queue entry not found"
                        ));

        queue.getPatient().setPriorityLevel(
                request.getPriorityLevel()
        );

        queueRepository.save(queue);

        return "Priority updated successfully";
    }

    /*
     * Update department
     */
    public String updateDepartment(
            Long id,
            UpdateDepartmentRequest request
    ) {

        Queue queue = queueRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Queue entry not found"
                        ));

        queue.getPatient().setDepartment(
                request.getDepartment()
        );

        queueRepository.save(queue);

        return "Department updated successfully";
    }

    /*
     * Manual queue number update
     */
    public String updateQueueNumber(
            Long id,
            UpdateQueueNumberRequest request
    ) {

        Queue queue = queueRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Queue entry not found"
                        ));

        queue.setQueueNumber(
                request.getQueueNumber()
        );

        queueRepository.save(queue);

        return "Queue number updated successfully";
    }

    /*
     * Delete queue entry
     */
    public String deleteQueue(Long id) {

        Queue queue = queueRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Queue entry not found"
                        ));

        queueRepository.delete(queue);

        return "Queue entry deleted successfully";
    }

    /*
     * Convert Queue entity to DTO
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
