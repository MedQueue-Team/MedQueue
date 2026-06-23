package com.clinical.manager.health.checks.Entity;

import java.time.LocalDateTime;

import com.clinical.manager.health.checks.enums.QueueStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Represents a patient queue entry.
 */
@Entity
@Table(name = "queue_entries")
@Getter
@Setter
@NoArgsConstructor
public class Queue {

    /*
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Queue number shown in clinic.
     * Example:
     * A001
     * A002
     */
    @Column(nullable = false, unique = true)
    private String queueNumber;

    /*
     * Current queue status.
     */
    @Enumerated(EnumType.STRING)
    private QueueStatus status;

    /*
     * Queue creation timestamp.
     */
    private LocalDateTime createdAt;

    /*
     * Time consultation completed.
     */
    private LocalDateTime completedAt;

    /*
     * Patient assigned to this queue.
     *
     * One queue entry belongs to one patient.
     */
    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    /*
     * Doctor handling this queue.
     *
     * Many queue entries can belong to one doctor.
     */
    @JsonIgnore
@ManyToOne
@JoinColumn(name = "doctor_id")
private Users doctor;

    /*
     * Automatically set queue creation time.
     */
    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
    }
}
