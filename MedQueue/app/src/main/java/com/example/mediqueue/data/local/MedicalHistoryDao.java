package com.example.mediqueue.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mediqueue.data.local.entities.MedicalHistory;

import java.util.List;

@Dao
public interface MedicalHistoryDao {

    @Query("SELECT * FROM medical_history ORDER BY id DESC")
    LiveData<List<MedicalHistory>> getAllHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MedicalHistory> history);

    @Query("DELETE FROM medical_history")
    void deleteAll();
}
