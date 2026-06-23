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
import com.example.mediqueue.data.local.entities.QueueEntity;

public class QueueManagementActivity extends AppCompatActivity implements QueuePatientAdapter.OnPatientActionListener {
    private QueueManagementViewModel viewModel;
    private TextView tvTotalWaiting, tvActiveQueue, tvHighPriority, tvCompleted;
    private RecyclerView rvQueue;
    private ImageView btnBack;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_management);

        btnBack = findViewById(R.id.btn_back);
        tvTotalWaiting = findViewById(R.id.tv_total_waiting);
        tvActiveQueue = findViewById(R.id.tv_active_queue);
        tvHighPriority = findViewById(R.id.tv_high_priority);
        tvCompleted = findViewById(R.id.tv_completed);
        rvQueue = findViewById(R.id.rv_queue);
        etSearch = findViewById(R.id.et_search);

        viewModel = new ViewModelProvider(this).get(QueueManagementViewModel.class);

        btnBack.setOnClickListener(v -> finish());

        rvQueue.setLayoutManager(new LinearLayoutManager(this));
        
        viewModel.getAllWaitingPatients().observe(this, patients -> {
            tvTotalWaiting.setText(String.valueOf(patients.size()));
            tvActiveQueue.setText(String.valueOf(patients.size() / 2));
            tvHighPriority.setText(String.valueOf(patients.size() / 4));
            tvCompleted.setText("42");
            rvQueue.setAdapter(new QueuePatientAdapter(patients, this));
        });

        findViewById(R.id.btn_call_next).setOnClickListener(v -> {
            Toast.makeText(this, "Calling next patient...", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_new_queue).setOnClickListener(v -> {
            Toast.makeText(this, "Adding new patient to queue...", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onEdit(QueueEntity patient) {
        Toast.makeText(this, "Editing patient: " + patient.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMove(QueueEntity patient) {
        Toast.makeText(this, "Moving patient: " + patient.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCall(QueueEntity patient) {
        Toast.makeText(this, "Calling patient: " + patient.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete(QueueEntity patient) {
        Toast.makeText(this, "Deleting patient: " + patient.getName(), Toast.LENGTH_SHORT).show();
    }
}
