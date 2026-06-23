package com.example.mediqueue.ui.receptionist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mediqueue.data.local.entities.QueueEntity;
import java.util.ArrayList;
import java.util.List;

public class ReceptionistDashboardViewModel extends ViewModel {
    private MutableLiveData<Integer> totalWaitingCount = new MutableLiveData<>(24);
    private MutableLiveData<Integer> pendingAppointmentsCount = new MutableLiveData<>(8);
    private MutableLiveData<List<QueueEntity>> queuePreview = new MutableLiveData<>();
    private MutableLiveData<List<DepartmentStatus>> departmentStatuses = new MutableLiveData<>();

    public ReceptionistDashboardViewModel() {
        loadMockData();
    }

    private void loadMockData() {
        List<QueueEntity> preview = new ArrayList<>();
        // Add some mock patients here if needed
        queuePreview.setValue(preview);

        List<DepartmentStatus> depts = new ArrayList<>();
        depts.add(new DepartmentStatus("General OPD", 8, "20m avg", 3, 75));
        depts.add(new DepartmentStatus("Pediatrics", 3, "5m avg", 1, 30));
        depts.add(new DepartmentStatus("Cardiology", 5, "15m avg", 2, 50));
        departmentStatuses.setValue(depts);
    }

    public LiveData<Integer> getTotalWaitingCount() { return totalWaitingCount; }
    public LiveData<Integer> getPendingAppointmentsCount() { return pendingAppointmentsCount; }
    public LiveData<List<QueueEntity>> getQueuePreview() { return queuePreview; }
    public LiveData<List<DepartmentStatus>> getDepartmentStatuses() { return departmentStatuses; }
}
