package com.example.mediqueue.ui.doctor;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.mediqueue.data.local.entities.Notification;
import com.example.mediqueue.data.repository.NotificationRepository;
import java.util.List;

public class DoctorNotificationsViewModel extends AndroidViewModel {
    private final NotificationRepository repository;
    private final LiveData<List<Notification>> allNotifications;

    public DoctorNotificationsViewModel(Application application) {
        super(application);
        repository = new NotificationRepository(application);
        allNotifications = repository.getAllNotifications();
    }

    public LiveData<List<Notification>> getAllNotifications() {
        return allNotifications;
    }

    public void markAllAsRead() {
        repository.markAllAsRead();
    }
}
