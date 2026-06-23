package com.example.mediqueue.ui.admin;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AdminOperationsViewModel extends AndroidViewModel {
    private final MutableLiveData<String> systemHealth = new MutableLiveData<>("Optimal");
    private final MutableLiveData<Integer> serverLoad = new MutableLiveData<>(35);

    public AdminOperationsViewModel(Application application) {
        super(application);
    }

    public LiveData<String> getSystemHealth() {
        return systemHealth;
    }

    public LiveData<Integer> getServerLoad() {
        return serverLoad;
    }

    public void triggerSystemCheck() {
        // Simulate system check
        serverLoad.setValue((int)(Math.random() * 100));
        systemHealth.setValue(serverLoad.getValue() > 80 ? "Warning" : "Optimal");
    }
}
