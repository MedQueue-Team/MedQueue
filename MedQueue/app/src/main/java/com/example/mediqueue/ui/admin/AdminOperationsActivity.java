package com.example.mediqueue.ui.admin;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.mediqueue.R;

public class AdminOperationsActivity extends AppCompatActivity {
    private AdminOperationsViewModel viewModel;
    private TextView tvSystemHealth;
    private ProgressBar pbSystemLoad;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_operations);

        btnBack = findViewById(R.id.btn_back);
        tvSystemHealth = findViewById(R.id.tv_system_health);
        pbSystemLoad = findViewById(R.id.pb_system_load);

        btnBack.setOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this).get(AdminOperationsViewModel.class);

        viewModel.getSystemHealth().observe(this, health -> tvSystemHealth.setText(health));
        viewModel.getServerLoad().observe(this, load -> {
            pbSystemLoad.setProgress(load);
            // Update label in a real app
        });

        // Navigation listeners
        findViewById(R.id.btn_op_users).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, UserManagementActivity.class));
        });

        findViewById(R.id.btn_op_schedule).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, ScheduleManagementActivity.class));
        });

        findViewById(R.id.btn_op_appointments).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, AppointmentOverviewActivity.class));
        });
    }
}
