package com.example.mediqueue.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mediqueue.data.local.entities.QueueEntity;

import java.util.List;

@Dao
public interface QueueDao {
    
    @Query("SELECT * FROM queue_status WHERE patient_id = :patientId AND status != 'COMPLETED'")
    LiveData<QueueEntity> getActiveQueueForPatient(String patientId);
    
    @Query("SELECT * FROM queue_status WHERE department = :department ORDER BY position ASC")
    LiveData<List<QueueEntity>> getQueueForDepartment(String department);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQueue(QueueEntity queue);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQueues(List<QueueEntity> queues);
    
    @Delete
    void deleteQueue(QueueEntity queue);
    
    @Query("DELETE FROM queue_status")
    void deleteAllQueues();

    @Query("SELECT * FROM queue_status WHERE status = 'WAITING' ORDER BY position ASC")
    LiveData<List<QueueEntity>> getAllWaitingPatients();

    @Query("SELECT * FROM queue_status WHERE is_synced = 0")
    List<QueueEntity> getUnsyncedQueue();

    @Query("UPDATE queue_status SET is_synced = 1 WHERE queue_id = :queueId")
    void markAsSynced(String queueId);

    @Query("UPDATE queue_status SET status = :status WHERE queue_id = :queueId")
    void updateQueueStatus(String queueId, String status);
}
