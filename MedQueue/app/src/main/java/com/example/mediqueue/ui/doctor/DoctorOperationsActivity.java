package com.example.mediqueue.ui.doctor;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.mediqueue.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class DoctorOperationsActivity extends AppCompatActivity {
    private DoctorOperationsViewModel viewModel;
    private SwitchMaterial switchAvailability;
    private TextView tvStatusDesc;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_operations);

        btnBack = findViewById(R.id.btn_back);
        switchAvailability = findViewById(R.id.switch_availability);
        tvStatusDesc = findViewById(R.id.tv_status_desc);

        btnBack.setOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this).get(DoctorOperationsViewModel.class);

        switchAvailability.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setAvailability(isChecked);
        });

        viewModel.isAvailable().observe(this, available -> {
            switchAvailability.setChecked(available);
        });

        viewModel.getStatusText().observe(this, text -> {
            tvStatusDesc.setText(text);
        });

        // Navigation listeners for the operations grid
        findViewById(R.id.btn_op_queue).setOnClickListener(v -> {
            // Navigate to Patients List
            startActivity(new android.content.Intent(this, DoctorPatientsListActivity.class));
        });

        findViewById(R.id.btn_op_prescriptions).setOnClickListener(v -> {
            // Placeholder for Prescription Templates
        });

        findViewById(R.id.btn_op_search).setOnClickListener(v -> {
            // Placeholder for Patient Registry
        });

        findViewById(R.id.btn_op_logs).setOnClickListener(v -> {
            // Placeholder for Shift Analytics
        });

        findViewById(R.id.btn_op_profile).setOnClickListener(v -> {
            // Navigate to Profile (if exists)
        });

        findViewById(R.id.btn_op_support).setOnClickListener(v -> {
            // Placeholder for Support
        });
    }
}
