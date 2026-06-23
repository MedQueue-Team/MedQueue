package com.example.mediqueue.ui.receptionist;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ReceptionistOperationsViewModel extends AndroidViewModel {
    private final MutableLiveData<String> facilityStatus = new MutableLiveData<>("Operational");
    private final MutableLiveData<String> statusDesc = new MutableLiveData<>("All reception desks are currently staffed.");

    public ReceptionistOperationsViewModel(Application application) {
        super(application);
    }

    public LiveData<String> getFacilityStatus() {
        return facilityStatus;
    }

    public LiveData<String> getStatusDesc() {
        return statusDesc;
    }

    public void setFacilityStatus(boolean operational) {
        facilityStatus.setValue(operational ? "Operational" : "Limited Capacity");
        statusDesc.setValue(operational ? "All reception desks are currently staffed." : "Some desks are currently unavailable.");
    }
}
