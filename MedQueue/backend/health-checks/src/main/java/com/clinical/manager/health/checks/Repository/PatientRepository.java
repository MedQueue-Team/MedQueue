package com.clinical.manager.health.checks.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinical.manager.health.checks.Entity.Patient;

/*
 * Repository for patient operations.
 */
public interface PatientRepository
        extends JpaRepository<Patient, Long> {
}
