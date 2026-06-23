package com.clinical.manager.health.checks.Entity;



import java.time.LocalDate;
import java.time.LocalDateTime;

import com.clinical.manager.health.checks.enums.PriorityLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Represents a patient
 * registered into the clinic system.
 */
@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
public class Patient {

    /*
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Patient full name.
     */
    @Column(nullable = false)
    private String fullName;

    /*
     * Patient date of birth.
     */
    private LocalDate dateOfBirth;

    /*
     * Patient gender.
     */
    private String gender;

    /*
     * Symptoms described during registration.
     */
    @Column(columnDefinition = "TEXT")
    private String symptoms;

    /*
     * Department patient is assigned to.
     */
    private String department;

    /*
     * Patient phone number.
     */
    private String phone;

    /*
     * Patient priority level.
     */
    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel;

    /*
     * Registration timestamp.
     */
    private LocalDateTime createdAt;

    /*
     * Automatically set registration time.
     */
    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
    }
}
