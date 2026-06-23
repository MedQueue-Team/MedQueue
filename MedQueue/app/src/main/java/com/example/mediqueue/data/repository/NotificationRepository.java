package com.example.mediqueue.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mediqueue.data.local.AppDatabase;
import com.example.mediqueue.data.local.NotificationDao;
import com.example.mediqueue.data.local.entities.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {

    private final NotificationDao notificationDao;
    private final LiveData<List<Notification>> allNotifications;

    public NotificationRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        notificationDao = db.notificationDao();
        allNotifications = notificationDao.getAllNotifications();
    }

    public LiveData<List<Notification>> getAllNotifications() {
        return allNotifications;
    }

    public void refreshNotifications() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Notification> mockData = getMockNotifications();
            notificationDao.insertAll(mockData);
        });
    }

    public void markAllAsRead() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            notificationDao.markAllAsRead();
        });
    }

    private List<Notification> getMockNotifications() {
        List<Notification> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        list.add(new Notification(
                "Queue Update", 
                "You are next in line. Please proceed to the waiting area near Room 402.", 
                Notification.Type.QUEUE, 
                now, 
                true, 
                true, 
                null));
        list.add(new Notification(
                "Health data synced", 
                "Your biometric data from Apple Health has been successfully imported and shared with Dr. Aris.", 
                Notification.Type.SYNC, 
                now - 7200000, 
                false, 
                false, 
                null));
        list.add(new Notification(
                "Consultation summary ready", 
                "The notes and prescription details from your morning consultation are now available in your records.", 
                Notification.Type.SUMMARY, 
                now - 14400000, 
                false, 
                false, 
                "Download PDF"));
        list.add(new Notification(
                "Upcoming Vaccination", 
                "Your vaccination is scheduled for tomorrow at 09:30 AM.", 
                Notification.Type.EVENT, 
                now - 86400000, 
                false, 
                true, 
                null));
        list.add(new Notification(
                "Security Update", 
                "Two-factor authentication has been enabled for your account.", 
                Notification.Type.SECURITY, 
                now - 86400000, 
                false, 
                false, 
                null));
        return list;
    }
}
