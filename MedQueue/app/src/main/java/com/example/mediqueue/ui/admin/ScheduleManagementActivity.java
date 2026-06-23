package com.example.mediqueue.ui.admin;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.R;
import java.util.List;

public class ScheduleManagementActivity extends AppCompatActivity {
    private AdminViewModel viewModel;
    private RecyclerView rvSchedules;
    private Spinner spinnerDoctor, spinnerDept;
    private ImageView btnBack;
    private ScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_management);

        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        btnBack = findViewById(R.id.btn_back);
        rvSchedules = findViewById(R.id.rv_schedules);
        spinnerDoctor = findViewById(R.id.spinner_doctor);
        spinnerDept = findViewById(R.id.spinner_dept);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        setupSchedules();
    }

    private void setupSchedules() {
        viewModel.getSchedules().observe(this, schedules -> {
            if (schedules != null) {
                adapter = new ScheduleAdapter(schedules, new ScheduleAdapter.OnScheduleActionListener() {
                    @Override
                    public void onEdit(Schedule schedule) {
                        Toast.makeText(ScheduleManagementActivity.this, "Editing " + schedule.doctorName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDelete(Schedule schedule) {
                        Toast.makeText(ScheduleManagementActivity.this, "Deleting " + schedule.doctorName, Toast.LENGTH_SHORT).show();
                    }
                });
                rvSchedules.setLayoutManager(new LinearLayoutManager(this));
                rvSchedules.setAdapter(adapter);
            }
        });
    }
}
