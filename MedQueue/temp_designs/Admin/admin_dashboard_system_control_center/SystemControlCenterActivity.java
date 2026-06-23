package com.example.mediqueue.ui.admin;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.ArrayList;
import java.util.List;

public class SystemControlCenterActivity extends AppCompatActivity implements ClinicQueueAdapter.OnClinicActionListener {

    private SystemControlCenterViewModel viewModel;
    
    // UI References
    private View emergencyBanner;
    private Button btnResumeAll;
    private View broadcastSection;
    private TextView tvBroadcastMessage;
    private ImageView btnDismissBroadcast;
    
    // KPI Metrics
    private TextView tvActiveQueue;
    private TextView tvAvgWaitTime;
    private TextView tvClinicsOpen;
    private TextView tvDbLatency;
    
    // Load Indicators
    private TextView tvCpuValue;
    private ProgressBar pbCpuLoad;
    private TextView tvMemoryValue;
    private ProgressBar pbMemoryLoad;
    
    // System Controls
    private SwitchCompat switchEmergency;
    private SwitchCompat switchMaintenance;
    private SwitchCompat switchAutoCall;
    private Button btnBackup;
    private ProgressBar pbBackup;
    
    // Broadcast Section
    private EditText etBroadcastInput;
    private Button btnPublishBroadcast;
    private Button btnDelayTemplate, btnResetTemplate, btnWeatherTemplate;
    
    // Clinic Queue Recycler
    private EditText etClinicSearch;
    private RecyclerView rvClinics;
    private ClinicQueueAdapter clinicAdapter;
    private List<ClinicQueue> cachedClinics = new ArrayList<>();
    
    // System Logs Console
    private RecyclerView rvLogs;
    private SystemLogAdapter logAdapter;
    private Button btnClearLogs;
    
    private BottomNavigationView bottomNav;
    private ToneGenerator toneGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_control_center);
        
        initializeViews();
        setupAdapters();
        setupViewModel();
        setupClickListeners();
        setupSearchFilter();
        
        try {
            toneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, 70); // Elegant sound chimes
        } catch (Exception e) {
            toneGenerator = null;
        }
    }

    private void initializeViews() {
        emergencyBanner = findViewById(R.id.emergency_banner);
        btnResumeAll = findViewById(R.id.btn_resume_all);
        broadcastSection = findViewById(R.id.broadcast_section);
        tvBroadcastMessage = findViewById(R.id.tv_broadcast_message);
        btnDismissBroadcast = findViewById(R.id.btn_dismiss_broadcast);
        
        tvActiveQueue = findViewById(R.id.tv_active_queue_val);
        tvAvgWaitTime = findViewById(R.id.tv_wait_time_val);
        tvClinicsOpen = findViewById(R.id.tv_clinics_open_val);
        tvDbLatency = findViewById(R.id.tv_latency_val);
        
        tvCpuValue = findViewById(R.id.tv_cpu_val);
        pbCpuLoad = findViewById(R.id.pb_cpu_load);
        tvMemoryValue = findViewById(R.id.tv_memory_val);
        pbMemoryLoad = findViewById(R.id.pb_memory_load);
        
        switchEmergency = findViewById(R.id.switch_emergency);
        switchMaintenance = findViewById(R.id.switch_maintenance);
        switchAutoCall = findViewById(R.id.switch_autocall);
        btnBackup = findViewById(R.id.btn_backup);
        pbBackup = findViewById(R.id.pb_backup);
        
        etBroadcastInput = findViewById(R.id.et_broadcast_input);
        btnPublishBroadcast = findViewById(R.id.btn_publish_broadcast);
        btnDelayTemplate = findViewById(R.id.btn_template_delay);
        btnResetTemplate = findViewById(R.id.btn_template_reset);
        btnWeatherTemplate = findViewById(R.id.btn_template_weather);
        
        etClinicSearch = findViewById(R.id.et_clinic_search);
        rvClinics = findViewById(R.id.rv_clinics);
        
        rvLogs = findViewById(R.id.rv_logs);
        btnClearLogs = findViewById(R.id.btn_clear_logs);
        
        bottomNav = findViewById(R.id.bottom_navigation);
    }

    private void setupAdapters() {
        // Clinic recycler
        clinicAdapter = new ClinicQueueAdapter(this);
        rvClinics.setLayoutManager(new LinearLayoutManager(this));
        rvClinics.setAdapter(clinicAdapter);
        
        // Logs recycler
        logAdapter = new SystemLogAdapter();
        LinearLayoutManager logsLayout = new LinearLayoutManager(this);
        logsLayout.setStackFromEnd(true); // Always keep newest logs scrolling on screen
        rvLogs.setLayoutManager(logsLayout);
        rvLogs.setAdapter(logAdapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(SystemControlCenterViewModel.class);
        
        // Observe Emergency Mode
        viewModel.getEmergencyPause().observe(this, active -> {
            switchEmergency.setChecked(active);
            emergencyBanner.setVisibility(active ? View.VISIBLE : View.GONE);
        });
        
        // Observe Maintenance Mode
        viewModel.getMaintenanceMode().observe(this, active -> {
            switchMaintenance.setChecked(active);
        });
        
        // Observe Auto-call AI mode
        viewModel.getAutoCallEnabled().observe(this, active -> {
            switchAutoCall.setChecked(active);
        });
        
        // Observe Active Broadcast
        viewModel.getActiveBroadcast().observe(this, msg -> {
            if (msg == null || msg.isEmpty()) {
                broadcastSection.setVisibility(View.GONE);
            } else {
                tvBroadcastMessage.setText(msg);
                broadcastSection.setVisibility(View.VISIBLE);
            }
        });
        
        // Observe Clinic Queues
        viewModel.getClinicQueues().observe(this, list -> {
            if (list != null) {
                cachedClinics = list;
                filterClinics();
                
                // Recalculate KPI stats
                int totalQueue = 0;
                int openClinics = 0;
                for (ClinicQueue c : list) {
                    totalQueue += c.getQueueCount();
                    if (c.getStatus() == ClinicQueue.Status.ACTIVE) {
                        openClinics++;
                    }
                }
                tvActiveQueue.setText(String.valueOf(totalQueue));
                tvClinicsOpen.setText(openClinics + " / " + list.size());
            }
        });
        
        // Observe System Logs Console
        viewModel.getSystemLogs().observe(this, logs -> {
            if (logs != null) {
                logAdapter.updateData(logs);
                rvLogs.scrollToPosition(logs.size() - 1);
            }
        });
        
        // Observe Load Indicators
        viewModel.getServerCpuLoad().observe(this, load -> {
            tvCpuValue.setText(load + "%");
            pbCpuLoad.setProgress(load);
        });
        
        viewModel.getServerMemoryAllocated().observe(this, memory -> {
            tvMemoryValue.setText(memory + " GB / 8 GB");
            int progress = (int) ((memory / 8.0) * 100);
            pbMemoryLoad.setProgress(progress);
        });
        
        viewModel.getDbLatency().observe(this, latency -> {
            tvDbLatency.setText(latency + " ms");
        });
        
        // Observe Backup Status
        viewModel.getBackupLoading().observe(this, loading -> {
            pbBackup.setVisibility(loading ? View.VISIBLE : View.GONE);
            btnBackup.setEnabled(!loading);
            btnBackup.setText(loading ? "Processing..." : "Run System Backup");
        });
    }

    private void setupClickListeners() {
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_refresh).setOnClickListener(v -> {
            viewModel.refreshMetrics();
            Toast.makeText(this, "System metrics synchronized!", Toast.LENGTH_SHORT).show();
        });
        
        btnResumeAll.setOnClickListener(v -> viewModel.toggleEmergencyPause(false));
        btnDismissBroadcast.setOnClickListener(v -> viewModel.dismissBroadcast());
        
        // Emergency switch confirmation dialogue
        switchEmergency.setOnClickListener(v -> {
            boolean checked = switchEmergency.isChecked();
            // Undo checked state until dialog verification
            switchEmergency.setChecked(!checked);
            
            if (checked) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Confirm Safety Lockout?")
                        .setMessage("You are about to activate the Emergency Queue Pause. This will freeze all queue activities across all clinics and display a warning banner on all customer check-in systems.")
                        .setPositiveButton("Activate Lockout", (dialog, which) -> {
                            viewModel.toggleEmergencyPause(true);
                            playTone(ToneGenerator.TONE_CDMA_PIP, 400);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                viewModel.toggleEmergencyPause(false);
            }
        });
        
        // Maintenance switch confirmation dialogue
        switchMaintenance.setOnClickListener(v -> {
            boolean checked = switchMaintenance.isChecked();
            switchMaintenance.setChecked(!checked);
            
            if (checked) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Confirm Maintenance Mode?")
                        .setMessage("Activating Maintenance Mode will shift user portals to read-only mode and halt appointment creation. All active sessions will stay logged in, but transactional databases will be locked.")
                        .setPositiveButton("Enable Mode", (dialog, which) -> {
                            viewModel.toggleMaintenanceMode(true);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                viewModel.toggleMaintenanceMode(false);
            }
        });
        
        switchAutoCall.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.toggleAutoCall(isChecked);
        });
        
        btnBackup.setOnClickListener(v -> viewModel.runDatabaseBackup());
        
        btnPublishBroadcast.setOnClickListener(v -> {
            String msg = etBroadcastInput.getText().toString().trim();
            if (msg.isEmpty()) {
                Toast.makeText(this, "Please enter a alert message first", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.publishBroadcast(msg);
            etBroadcastInput.setText("");
        });
        
        btnDelayTemplate.setOnClickListener(v -> etBroadcastInput.setText("Cardiology clinic is running 15 mins late."));
        btnResetTemplate.setOnClickListener(v -> etBroadcastInput.setText("All queues will reset in 10 minutes."));
        btnWeatherTemplate.setOnClickListener(v -> etBroadcastInput.setText("Severe weather alert. Drive safe!"));
        
        btnClearLogs.setOnClickListener(v -> viewModel.clearLogs());
        
        // Navigation bar
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_admin_home); // Or a specific system control center item
        }
    }

    private void setupSearchFilter() {
        etClinicSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int after) {
                filterClinics();
            }
            
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterClinics() {
        String query = etClinicSearch.getText().toString().toLowerCase().trim();
        if (query.isEmpty()) {
            clinicAdapter.updateData(cachedClinics);
        } else {
            List<ClinicQueue> filtered = new ArrayList<>();
            for (ClinicQueue c : cachedClinics) {
                if (c.getName().toLowerCase().contains(query) || c.getDoctorName().toLowerCase().contains(query)) {
                    filtered.add(c);
                }
            }
            clinicAdapter.updateData(filtered);
        }
    }

    // Adapter action listeners
    @Override
    public void onCallNext(ClinicQueue clinic) {
        viewModel.callNext(clinic);
        playChimeSound();
        Toast.makeText(this, "Called Next for " + clinic.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onToggleStatus(ClinicQueue clinic) {
        viewModel.toggleClinicStatus(clinic);
    }

    private void playChimeSound() {
        if (toneGenerator != null) {
            new Thread(() -> {
                // High-fidelity elegant synthesizer chime chime
                toneGenerator.startTone(ToneGenerator.TONE_DTMF_3, 150);
                try {
                    Thread.sleep(180);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                toneGenerator.startTone(ToneGenerator.TONE_DTMF_D, 200);
            }).start();
        }
    }

    private void playTone(int toneType, int durationMs) {
        if (toneGenerator != null) {
            toneGenerator.startTone(toneType, durationMs);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toneGenerator != null) {
            toneGenerator.release();
        }
    }
}
