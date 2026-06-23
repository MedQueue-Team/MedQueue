package com.example.mediqueue.ui.receptionist;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entities.AppointmentEntity;

public class AppointmentApprovalActivity extends AppCompatActivity implements AppointmentAdapter.OnAppointmentActionListener {
    private AppointmentApprovalViewModel viewModel;
    private TextView tvPendingStat, tvApprovedStat, tvRejectedStat;
    private RecyclerView rvAppointments;
    private ImageView btnBack;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_approvals);

        btnBack = findViewById(R.id.btn_back);
        tvPendingStat = findViewById(R.id.tv_pending_stat);
        tvApprovedStat = findViewById(R.id.tv_approved_stat);
        tvRejectedStat = findViewById(R.id.tv_rejected_stat);
        rvAppointments = findViewById(R.id.rv_appointments);
        etSearch = findViewById(R.id.et_search);

        viewModel = new ViewModelProvider(this).get(AppointmentApprovalViewModel.class);

        btnBack.setOnClickListener(v -> finish());

        rvAppointments.setLayoutManager(new LinearLayoutManager(this));
        
        viewModel.getPendingAppointments().observe(this, appointments -> {
            tvPendingStat.setText(String.valueOf(appointments.size()));
            tvApprovedStat.setText("45");
            tvRejectedStat.setText("03");
            rvAppointments.setAdapter(new AppointmentAdapter(appointments, this));
        });
    }

    @Override
    public void onApprove(AppointmentEntity appointment) {
        Toast.makeText(this, "Approved appointment for: " + appointment.getPatientName(), Toast.LENGTH_SHORT).show();
        viewModel.approveAppointment(String.valueOf(appointment.getId()));
    }

    @Override
    public void onReject(AppointmentEntity appointment) {
        Toast.makeText(this, "Rejected appointment for: " + appointment.getPatientName(), Toast.LENGTH_SHORT).show();
        viewModel.rejectAppointment(String.valueOf(appointment.getId()));
    }
}
