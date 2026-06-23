package com.clinical.manager.health.checks.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.clinical.manager.health.checks.DTO.CreateMedicalRecordRequest;
import com.clinical.manager.health.checks.DTO.MedicalRecordResponse;
import com.clinical.manager.health.checks.Entity.MedicalRecord;
import com.clinical.manager.health.checks.Entity.Users;
import com.clinical.manager.health.checks.Exception.ResourceNotFoundException;
import com.clinical.manager.health.checks.Repository.MedicalRecordRepository;
import com.clinical.manager.health.checks.Repository.UserRepository;
import com.clinical.manager.health.checks.enums.RecordStatus;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private UserRepository userRepository;

    /*
     * Doctor creates medical record
     */
    public String createRecord(
            CreateMedicalRecordRequest request,
            Authentication authentication
    ) {

        String doctorEmail =
                authentication.getName();

        Users doctor = userRepository
                .findByEmail(doctorEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"
                        ));

        Users patient = userRepository
                .findById(request.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"
                        ));

        MedicalRecord record =
                new MedicalRecord();

        /*
         * Link doctor & patient
         */
        record.setDoctor(doctor);

        record.setPatient(patient);

        /*
         * Clinical information
         */
        record.setDiagnosis(
                request.getDiagnosis()
        );

        record.setPrescription(
                request.getPrescription()
        );

        record.setNotes(
                request.getNotes()
        );

        record.setLabResults(
                request.getLabResults()
        );

        /*
         * Auto status
         */
        record.setStatus(
                RecordStatus.ACTIVE
        );

        medicalRecordRepository.save(record);

        return "Medical record created successfully";
    }

    /*
     * Patient views own records
     */
    public List<MedicalRecordResponse>
    getMyRecords(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        Users patient = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"
                        ));

        List<MedicalRecord> records =
                medicalRecordRepository
                        .findByPatient(patient);

        return records.stream()

                .map(this::mapToResponse)

                .collect(Collectors.toList());
    }

    /*
     * Doctor views patient records
     */
    public List<MedicalRecordResponse>
    getPatientRecords(Long patientId) {

        Users patient = userRepository
                .findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"
                        ));

        List<MedicalRecord> records =
                medicalRecordRepository
                        .findByPatient(patient);

        return records.stream()

                .map(this::mapToResponse)

                .collect(Collectors.toList());
    }

    /*
     * Complete record
     */
    public String completeRecord(Long id) {

        MedicalRecord record =
                medicalRecordRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Medical record not found"
                                ));

        record.setStatus(
                RecordStatus.COMPLETED
        );

        medicalRecordRepository.save(record);

        return "Medical record completed";
    }

    /*
     * Convert entity -> DTO
     */
    private MedicalRecordResponse
    mapToResponse(MedicalRecord record) {

        return new MedicalRecordResponse(

                record.getId(),

                record.getPatient()
                        .getFullName(),

                record.getDoctor()
                        .getFullName(),

                record.getDiagnosis(),

                record.getPrescription(),

                record.getNotes(),

                record.getLabResults(),

                null,

                record.getStatus(),

                record.getCreatedAt()
        );
    }
}
