package com.example.mediqueue.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mediqueue.R;
import com.example.mediqueue.ui.login.LoginActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ClinicAdminSettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout btnClinicProfile, btnOperatingHours, btnDepartmentMgmt;
    private LinearLayout btnManageRoles, btnShiftSchedules, btnApprovalWorkflows;
    private LinearLayout btnChangePassword, btnTwoFactor;
    private SwitchMaterial switchOperationalAlerts, switchSystemUpdates;
    private MaterialButton btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_admin_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Facility
        btnClinicProfile = findViewById(R.id.btnClinicProfile);
        btnOperatingHours = findViewById(R.id.btnOperatingHours);
        btnDepartmentMgmt = findViewById(R.id.btnDepartmentMgmt);

        // Staff
        btnManageRoles = findViewById(R.id.btnManageRoles);
        btnShiftSchedules = findViewById(R.id.btnShiftSchedules);
        btnApprovalWorkflows = findViewById(R.id.btnApprovalWorkflows);

        // Security
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnTwoFactor = findViewById(R.id.btnTwoFactor);

        // Toggles
        switchOperationalAlerts = findViewById(R.id.switchOperationalAlerts);
        switchSystemUpdates = findViewById(R.id.switchSystemUpdates);

        btnLogout = findViewById(R.id.btnLogout);

        setupListeners();
    }

    private void setupListeners() {
        View.OnClickListener notImplementedListener = v -> 
            Toast.makeText(this, "Feature coming soon", Toast.LENGTH_SHORT).show();

        btnClinicProfile.setOnClickListener(notImplementedListener);
        btnOperatingHours.setOnClickListener(notImplementedListener);
        btnDepartmentMgmt.setOnClickListener(notImplementedListener);
        
        btnManageRoles.setOnClickListener(notImplementedListener);
        btnShiftSchedules.setOnClickListener(notImplementedListener);
        btnApprovalWorkflows.setOnClickListener(notImplementedListener);
        
        btnChangePassword.setOnClickListener(notImplementedListener);
        btnTwoFactor.setOnClickListener(notImplementedListener);

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
