package com.clinical.manager.health.checks.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinical.manager.health.checks.Entity.Queue;
import com.clinical.manager.health.checks.enums.PriorityLevel;
import com.clinical.manager.health.checks.enums.QueueStatus;

/*
 * Handles queue database operations.
 */
public interface QueueRepository
        extends JpaRepository<Queue, Long> {

    /*
     * Counts total queue entries.
     * Used for generating queue numbers.
     */
    long count();
    /*
 * Fetch all queue entries
 * by status.
 */
List<Queue> findByStatus(QueueStatus status);

/*
 * Fetch queue entry by ID.
 */
Optional<Queue> findById(Long id);

List<Queue> findByStatusOrderByPatientPriorityLevelAscCreatedAtAsc(
        QueueStatus status
);
/*
 * Counts queue entries by status.
 */
long countByStatus(QueueStatus status);

/*
 * Counts queues created after specific time.
 */
long countByCreatedAtAfter(LocalDateTime dateTime);


/*
 * Converts priority level
 * into numeric score.
 */
private int getPriorityScore(
        PriorityLevel level) {

    return switch (level) {

        case EMERGENCY -> 1;

        case URGENT -> 2;

        case NORMAL -> 3;
    };
}
}
