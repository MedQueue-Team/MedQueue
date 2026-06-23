package com.example.mediqueue.ui.doctor;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;

public class DoctorPatientsListActivity extends AppCompatActivity {
    private DoctorPatientsListViewModel viewModel;
    private RecyclerView rvPatients;
    private DoctorPatientsListAdapter adapter;
    private TextView txtActiveCount, txtAvgWait, txtSuccessRate;
    private ImageView btnBack, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patients_list);

        btnBack = findViewById(R.id.btn_back);
        btnProfile = findViewById(R.id.btn_profile);
        rvPatients = findViewById(R.id.rv_patients);
        txtActiveCount = findViewById(R.id.txtActiveCount);
        txtAvgWait = findViewById(R.id.txtAvgWait);
        txtSuccessRate = findViewById(R.id.txtSuccessRate);

        btnBack.setOnClickListener(v -> finish());
        btnProfile.setOnClickListener(v -> {
            // Profile navigation logic
        });

        rvPatients.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoctorPatientsListAdapter();
        rvPatients.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(DoctorPatientsListViewModel.class);
        viewModel.getWaitingPatients().observe(this, patients -> adapter.setPatients(patients));
        viewModel.getActiveCount().observe(this, count -> txtActiveCount.setText(String.valueOf(count)));
        viewModel.getAvgWait().observe(this, wait -> txtAvgWait.setText(wait));
        viewModel.getSuccessRate().observe(this, rate -> txtSuccessRate.setText(rate));
    }
}
