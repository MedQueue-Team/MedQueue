package com.example.mediqueue.ui.receptionist;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.mediqueue.R;

public class ReceptionistOperationsActivity extends AppCompatActivity {
    private ReceptionistOperationsViewModel viewModel;
    private TextView tvFacilityStatus, tvStatusDesc;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionist_operations);

        btnBack = findViewById(R.id.btn_back);
        tvFacilityStatus = findViewById(R.id.tv_facility_status);
        tvStatusDesc = findViewById(R.id.tv_status_desc);

        btnBack.setOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this).get(ReceptionistOperationsViewModel.class);

        viewModel.getFacilityStatus().observe(this, status -> tvFacilityStatus.setText(status));
        viewModel.getStatusDesc().observe(this, desc -> tvStatusDesc.setText(desc));

        // Navigation listeners
        findViewById(R.id.btn_op_queue).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, QueueManagementActivity.class));
        });

        findViewById(R.id.btn_op_appointments).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, AppointmentApprovalActivity.class));
        });

        findViewById(R.id.btn_op_register).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, PatientRegistrationActivity.class));
        });

        findViewById(R.id.btn_op_logistics).setOnClickListener(v -> {
            // Placeholder for Logistics
        });

        findViewById(R.id.btn_op_profile).setOnClickListener(v -> {
            // Placeholder for Profile
        });

        findViewById(R.id.btn_op_support).setOnClickListener(v -> {
            // Placeholder for Support
        });
    }
}
