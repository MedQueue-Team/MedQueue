package com.example.mediqueue.ui.receptionist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

public class NurseNotificationsViewModel extends ViewModel {

    private final MutableLiveData<List<NurseNotification>> recentNotifications = new MutableLiveData<>();
    private final MutableLiveData<List<NurseNotification>> earlierNotifications = new MutableLiveData<>();
    private final MutableLiveData<Integer> unreadCount = new MutableLiveData<>(0);

    public NurseNotificationsViewModel() {
        seedData();
    }

    private void seedData() {
        List<NurseNotification> recent = Arrays.asList(
            new NurseNotification(
                "Emergency Case Assigned",
                "Patient #8291 assigned to triage. Immediate assessment required for chest pain.",
                "Just now",
                NurseNotification.Type.TRIAGE_ALERT,
                true,
                "#8291",
                "Accept Case",
                "View Details"
            ),
            new NurseNotification(
                "Patient Transfer Request",
                "Dr. Mwangi has requested patient Amara Okafor be transferred to Ward 4B.",
                "3 min ago",
                NurseNotification.Type.PATIENT_TRANSFER,
                true,
                null,
                "Approve",
                "Decline"
            ),
            new NurseNotification(
                "Queue Updated",
                "2 new patients added to the triage queue. Current wait time: 18 minutes.",
                "12 min ago",
                NurseNotification.Type.QUEUE_UPDATE,
                false
            ),
            new NurseNotification(
                "Emergency Alert",
                "Code Blue in Ward 3A. All available staff report immediately.",
                "25 min ago",
                NurseNotification.Type.EMERGENCY,
                true,
                null,
                "Acknowledge",
                null
            )
        );

        List<NurseNotification> earlier = Arrays.asList(
            new NurseNotification(
                "Triage Assessment Complete",
                "Patient Catherine Chen (LSK-KNH-011452) assessment finalized. Priority: HIGH.",
                "Yesterday, 4:32 PM",
                NurseNotification.Type.TRIAGE_ALERT,
                false
            ),
            new NurseNotification(
                "Queue Position Updated",
                "Patient David Kamau moved to position #2. Estimated wait: 8 minutes.",
                "Yesterday, 2:15 PM",
                NurseNotification.Type.QUEUE_UPDATE,
                false
            ),
            new NurseNotification(
                "Shift Handover Reminder",
                "Your shift ends in 30 minutes. Please complete all pending assessments.",
                "Yesterday, 1:30 PM",
                NurseNotification.Type.SYSTEM,
                false
            ),
            new NurseNotification(
                "Patient Discharged",
                "Patient James Odhiambo (LSK-KNH-009871) has been successfully discharged.",
                "Yesterday, 11:05 AM",
                NurseNotification.Type.PATIENT_TRANSFER,
                false
            )
        );

        recentNotifications.setValue(recent);
        earlierNotifications.setValue(earlier);
        unreadCount.setValue(3);
    }

    public LiveData<List<NurseNotification>> getRecentNotifications()  { return recentNotifications; }
    public LiveData<List<NurseNotification>> getEarlierNotifications() { return earlierNotifications; }
    public LiveData<Integer> getUnreadCount() { return unreadCount; }

    public void markAllRead() {
        unreadCount.setValue(0);
    }
}
