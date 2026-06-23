package com.clinical.manager.health.checks.Entity;

import java.time.LocalDateTime;

import com.clinical.manager.health.checks.enums.QueueStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "patient_queue")
public class PatientQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Patient
     */
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Users patient;

    /*
     * Doctor
     */
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Users doctor;

    /*
     * Queue number
     */
    private Integer queueNumber;

    /*
     * Queue status
     */
    @Enumerated(EnumType.STRING)
    private QueueStatus status;

    /*
     * Time joined queue
     */
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        if (status == null) {

            status = QueueStatus.WAITING;
        }
    }

    public Long getId() {
        return id;
    }

    public Users getPatient() {
        return patient;
    }

    public void setPatient(Users patient) {
        this.patient = patient;
    }

    public Users getDoctor() {
        return doctor;
    }

    public void setDoctor(Users doctor) {
        this.doctor = doctor;
    }

    public Integer getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(Integer queueNumber) {
        this.queueNumber = queueNumber;
    }

    public QueueStatus getStatus() {
        return status;
    }

    public void setStatus(QueueStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}