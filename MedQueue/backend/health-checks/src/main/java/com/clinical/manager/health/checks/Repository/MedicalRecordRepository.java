package com.clinical.manager.health.checks.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinical.manager.health.checks.Entity.MedicalRecord;
import com.clinical.manager.health.checks.Entity.Users;

public interface MedicalRecordRepository
        extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatient(
            Users patient
    );
}