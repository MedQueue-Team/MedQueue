package com.example.mediqueue.ui.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SystemControlCenterViewModel extends ViewModel {

    private final MutableLiveData<Boolean> emergencyPause = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> maintenanceMode = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> autoCallEnabled = new MutableLiveData<>(true);
    
    private final MutableLiveData<String> activeBroadcast = new MutableLiveData<>("");
    
    private final MutableLiveData<List<ClinicQueue>> clinicQueues = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<SystemLog>> systemLogs = new MutableLiveData<>(new ArrayList<>());
    
    private final MutableLiveData<Integer> serverCpuLoad = new MutableLiveData<>(24);
    private final MutableLiveData<Double> serverMemoryAllocated = new MutableLiveData<>(4.2);
    private final MutableLiveData<Integer> dbLatency = new MutableLiveData<>(12);
    private final MutableLiveData<Boolean> backupLoading = new MutableLiveData<>(false);

    private ScheduledExecutorService scheduler;
    private final Random random = new Random();

    public SystemControlCenterViewModel() {
        initializeClinics();
        initializeLogs();
        startSimulatedMetrics();
    }

    private void initializeClinics() {
        List<ClinicQueue> list = new ArrayList<>();
        list.add(new ClinicQueue("opd", "General OPD", "Dr. Phiri", "A", 24, 18, ClinicQueue.Status.ACTIVE));
        list.add(new ClinicQueue("cardio", "Cardiology", "Dr. Lungu", "C", 104, 6, ClinicQueue.Status.ACTIVE));
        list.add(new ClinicQueue("peds", "Pediatrics", "Dr. Banda", "P", 55, 12, ClinicQueue.Status.ACTIVE));
        list.add(new ClinicQueue("derm", "Dermatology", "Dr. Kapuya", "D", 12, 4, ClinicQueue.Status.ACTIVE));
        list.add(new ClinicQueue("ortho", "Orthopedics", "Dr. Chisamba", "O", 0, 0, ClinicQueue.Status.CLOSED));
        clinicQueues.setValue(list);
    }

    private void initializeLogs() {
        List<SystemLog> logs = new ArrayList<>();
        logs.add(new SystemLog(SystemLog.Type.SYSTEM, "Application boot completed. Active database nodes: 3."));
        logs.add(new SystemLog(SystemLog.Type.SUCCESS, "Security protocol synchronized successfully with Admin Panel."));
        logs.add(new SystemLog(SystemLog.Type.WARNING, "Heartbeat latency for clinic \"Orthopedics\" timed out. Auto-suspending queue."));
        systemLogs.setValue(logs);
    }

    private void startSimulatedMetrics() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            if (Boolean.TRUE.equals(emergencyPause.getValue()) || Boolean.TRUE.equals(maintenanceMode.getValue())) {
                return;
            }

            // Fluctuate CPU
            int currentCpu = 20 + random.nextInt(20);
            serverCpuLoad.postValue(currentCpu);

            // Fluctuate Latency
            int currentLatency = 10 + random.nextInt(6);
            dbLatency.postValue(currentLatency);

            // Periodically check-in random patients
            if (random.nextInt(3) == 0) {
                postValuePatientCheckIn();
            }

        }, 5, 8, TimeUnit.SECONDS);
    }

    private void postValuePatientCheckIn() {
        List<ClinicQueue> currentList = clinicQueues.getValue();
        if (currentList == null || currentList.isEmpty()) return;

        // Choose random open clinic
        List<ClinicQueue> activeClinics = new ArrayList<>();
        for (ClinicQueue c : currentList) {
            if (c.getStatus() == ClinicQueue.Status.ACTIVE) {
                activeClinics.add(c);
            }
        }

        if (!activeClinics.isEmpty()) {
            ClinicQueue chosen = activeClinics.get(random.nextInt(activeClinics.size()));
            chosen.setQueueCount(chosen.getQueueCount() + 1);
            clinicQueues.postValue(new ArrayList<>(currentList));

            String ticket = chosen.getPrefix() + "-" + String.format("%03d", chosen.getCurrentToken() + chosen.getQueueCount());
            addLog(SystemLog.Type.INFO, "Patient checks in via Portal kiosk. Token " + ticket + " generated for " + chosen.getName() + ".");
        }
    }

    // View Model Actions
    public void toggleEmergencyPause(boolean activate) {
        emergencyPause.setValue(activate);
        if (activate) {
            addLog(SystemLog.Type.ERROR, "EMERGENCY SHUTDOWN SIGNALLED. Automatic ticketing paused globally.");
        } else {
            addLog(SystemLog.Type.SUCCESS, "Safety pause deactivated. Normal check-in services resumed.");
        }
    }

    public void toggleMaintenanceMode(boolean activate) {
        maintenanceMode.setValue(activate);
        if (activate) {
            addLog(SystemLog.Type.WARNING, "Server transitioning to read-only mode for upgrades.");
        } else {
            addLog(SystemLog.Type.SUCCESS, "Server transaction channels re-established successfully.");
        }
    }

    public void toggleAutoCall(boolean enable) {
        autoCallEnabled.setValue(enable);
        addLog(SystemLog.Type.INFO, "Auto-Admit AI mode changed to: " + (enable ? "ENABLED" : "DISABLED"));
    }

    public void publishBroadcast(String message) {
        activeBroadcast.setValue(message);
        addLog(SystemLog.Type.INFO, "Broadcasting System Banner: \"" + message + "\"");
    }

    public void dismissBroadcast() {
        activeBroadcast.setValue("");
        addLog(SystemLog.Type.INFO, "Broadcast alert cancelled by admin dispatcher.");
    }

    public void runDatabaseBackup() {
        backupLoading.setValue(true);
        addLog(SystemLog.Type.INFO, "Initiating structural backup task for tables: Users, Appointments, Queues...");

        // Simulate network / processing lag
        new Thread(() -> {
            try {
                Thread.sleep(1800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            backupLoading.postValue(false);
            addLog(SystemLog.Type.SUCCESS, "Encrypted database backup bundle (24.2MB) uploaded to secure S3 storage node.");
        }).start();
    }

    public void callNext(ClinicQueue clinic) {
        if (Boolean.TRUE.equals(emergencyPause.getValue())) {
            addLog(SystemLog.Type.WARNING, "Call next rejected: system emergency pause is active.");
            return;
        }

        if (clinic.getStatus() != ClinicQueue.Status.ACTIVE) return;
        if (clinic.getQueueCount() <= 0) {
            addLog(SystemLog.Type.WARNING, "Call Next clicked for empty clinic: " + clinic.getName());
            return;
        }

        clinic.setCurrentToken(clinic.getCurrentToken() + 1);
        clinic.setQueueCount(clinic.getQueueCount() - 1);
        
        // Push update to LiveData observer
        List<ClinicQueue> list = clinicQueues.getValue();
        clinicQueues.setValue(new ArrayList<>(list));

        String ticket = clinic.getCurrentTokenDisplay();
        addLog(SystemLog.Type.INFO, "Clinic \"" + clinic.getName() + "\" dispatched token " + ticket + ". Patients in waiting list: " + clinic.getQueueCount() + ".");
    }

    public void toggleClinicStatus(ClinicQueue clinic) {
        List<ClinicQueue> list = clinicQueues.getValue();
        if (list == null) return;

        if (clinic.getStatus() == ClinicQueue.Status.ACTIVE) {
            clinic.setStatus(ClinicQueue.Status.SUSPENDED);
            addLog(SystemLog.Type.WARNING, "Clinic \"" + clinic.getName() + "\" queue is suspended.");
        } else {
            if (clinic.getId().equals("ortho") && clinic.getQueueCount() == 0) {
                clinic.setQueueCount(5);
                clinic.setCurrentToken(1);
            }
            clinic.setStatus(ClinicQueue.Status.ACTIVE);
            addLog(SystemLog.Type.SUCCESS, "Clinic \"" + clinic.getName() + "\" queue is fully operational.");
        }

        clinicQueues.setValue(new ArrayList<>(list));
    }

    public void refreshMetrics() {
        addLog(SystemLog.Type.INFO, "Manual refresh triggered. Syncing system registers...");
        int cpuVal = 18 + random.nextInt(15);
        serverCpuLoad.setValue(cpuVal);
        dbLatency.setValue(10 + random.nextInt(5));
    }

    public void addLog(SystemLog.Type type, String message) {
        List<SystemLog> currentLogs = systemLogs.getValue();
        if (currentLogs == null) currentLogs = new ArrayList<>();
        
        // Keep logs bounded to 50 entries
        if (currentLogs.size() > 50) {
            currentLogs.remove(0);
        }
        currentLogs.add(new SystemLog(type, message));
        systemLogs.postValue(new ArrayList<>(currentLogs));
    }

    public void clearLogs() {
        List<SystemLog> logs = new ArrayList<>();
        logs.add(new SystemLog(SystemLog.Type.INFO, "Console log flushed. System active..."));
        systemLogs.setValue(logs);
    }

    // Getters for View Layer
    public LiveData<Boolean> getEmergencyPause() { return emergencyPause; }
    public LiveData<Boolean> getMaintenanceMode() { return maintenanceMode; }
    public LiveData<Boolean> getAutoCallEnabled() { return autoCallEnabled; }
    public LiveData<String> getActiveBroadcast() { return activeBroadcast; }
    public LiveData<List<ClinicQueue>> getClinicQueues() { return clinicQueues; }
    public LiveData<List<SystemLog>> getSystemLogs() { return systemLogs; }
    
    public LiveData<Integer> getServerCpuLoad() { return serverCpuLoad; }
    public LiveData<Double> getServerMemoryAllocated() { return serverMemoryAllocated; }
    public LiveData<Integer> getDbLatency() { return dbLatency; }
    public LiveData<Boolean> getBackupLoading() { return backupLoading; }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}
