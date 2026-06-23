package com.example.mediqueue.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.mediqueue.data.local.entities.ConsultationEntity;
import java.util.List;

@Dao
public interface ConsultationDao {
    @Insert
    void insertConsultation(ConsultationEntity consultation);

    @Query("SELECT * FROM consultations WHERE isSynced = 0")
    List<ConsultationEntity> getUnsyncedConsultations();

    @Update
    void updateConsultation(ConsultationEntity consultation);

    @Query("SELECT * FROM consultations WHERE patientId = :patientId")
    List<ConsultationEntity> getConsultationsForPatient(String patientId);
}
