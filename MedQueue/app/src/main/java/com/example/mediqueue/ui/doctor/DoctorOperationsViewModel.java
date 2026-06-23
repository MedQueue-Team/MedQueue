package com.example.mediqueue.ui.doctor;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DoctorOperationsViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> isAvailable = new MutableLiveData<>(true);
    private final MutableLiveData<String> statusText = new MutableLiveData<>("Currently: Online & Available");

    public DoctorOperationsViewModel(Application application) {
        super(application);
    }

    public LiveData<Boolean> isAvailable() {
        return isAvailable;
    }

    public LiveData<String> getStatusText() {
        return statusText;
    }

    public void setAvailability(boolean available) {
        isAvailable.setValue(available);
        statusText.setValue(available ? "Currently: Online & Available" : "Currently: Offline / On Break");
    }
}
