package com.example.mediqueue.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mediqueue.data.local.entities.Notification;

import java.util.List;

@Dao
public interface NotificationDao {

    @Query("SELECT * FROM notifications ORDER BY id DESC")
    LiveData<List<Notification>> getAllNotifications();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Notification> notifications);

    @Query("DELETE FROM notifications")
    void deleteAll();

    @Query("UPDATE notifications SET isRead = 1")
    void markAllAsRead();
}
