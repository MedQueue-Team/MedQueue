package com.example.mediqueue.ui.receptionist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entities.QueueEntity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReceptionistDashboardActivity extends AppCompatActivity {
    private ReceptionistDashboardViewModel viewModel;
    private TextView tvQueueCount, tvPendingCount;
    private RecyclerView rvQueuePreview, rvDeptStatus;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionist_dashboard);

        tvQueueCount = findViewById(R.id.tv_queue_count);
        tvPendingCount = findViewById(R.id.tv_pending_count);
        rvQueuePreview = findViewById(R.id.rv_queue_preview);
        rvDeptStatus = findViewById(R.id.rv_department_status);
        bottomNav = findViewById(R.id.bottom_navigation);

        viewModel = new ViewModelProvider(this).get(ReceptionistDashboardViewModel.class);

        viewModel.getTotalWaitingCount().observe(this, count -> 
            tvQueueCount.setText(count + " Patients"));
        
        viewModel.getPendingAppointmentsCount().observe(this, count -> 
            tvPendingCount.setText(String.valueOf(count)));

        rvQueuePreview.setLayoutManager(new LinearLayoutManager(this));
        viewModel.getQueuePreview().observe(this, patients -> {
            rvQueuePreview.setAdapter(new QueueAdapter(patients));
        });

        rvDeptStatus.setLayoutManager(new LinearLayoutManager(this));
        viewModel.getDepartmentStatuses().observe(this, depts -> {
            rvDeptStatus.setAdapter(new DepartmentStatusAdapter(depts));
        });
        
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                return true;
            } else if (id == R.id.nav_queue) {
                startActivity(new Intent(this, QueueManagementActivity.class));
                return true;
            } else if (id == R.id.nav_appointments) {
                startActivity(new Intent(this, AppointmentApprovalActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                return true;
            }
            return false;
        });
    }
}
