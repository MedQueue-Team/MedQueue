package com.example.mediqueue.ui.receptionist;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mediqueue.R;
import com.example.mediqueue.databinding.ActivityNurseDashboardBinding;
import com.example.mediqueue.ui.base.BaseNavActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NurseDashboardActivity extends BaseNavActivity {

    private ActivityNurseDashboardBinding binding;
    private TriageViewModel viewModel;
    private TriageAdapter adapter;
    private final List<TriagePatient> allPatients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNurseDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(TriageViewModel.class);

        initViews();
        setupDashboardNavigation(DashboardRole.NURSE, R.id.nav_home);
        observeViewModel();
    }

    private void initViews() {
        binding.rvTriageQueue.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TriageAdapter(new ArrayList<>());
        binding.rvTriageQueue.setAdapter(adapter);
        
        binding.fabAddPatient.setOnClickListener(v -> {
            startActivity(new Intent(this, PatientRegistrationActivity.class));
        });

        binding.ivNotifications.setOnClickListener(v -> {
            Toast.makeText(this, "Notifications coming soon", Toast.LENGTH_SHORT).show();
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterQueue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.btnFullHistory.setOnClickListener(v -> {
            startActivity(new Intent(this, PatientMedicalHistoryActivity.class));
        });
    }

    private void observeViewModel() {
        viewModel.getTriageQueue().observe(this, patients -> {
            if (patients != null) {
                allPatients.clear();
                allPatients.addAll(patients);
                
                // Set the next patient in the focus card to be the first unassessed patient
                TriagePatient nextPatient = null;
                for (TriagePatient p : patients) {
                    if (!p.isAssessed()) {
                        nextPatient = p;
                        break;
                    }
                }
                
                if (nextPatient != null) {
                    binding.tvNextPatientName.setText(nextPatient.getName());
                } else if (!patients.isEmpty()) {
                    binding.tvNextPatientName.setText(patients.get(0).getName());
                }
                
                // Apply search filtering
                filterQueue(binding.etSearch.getText().toString());
            }
        });
    }

    private void filterQueue(String query) {
        List<TriagePatient> filtered = new ArrayList<>();
        for (TriagePatient p : allPatients) {
            if (p.getName().toLowerCase().contains(query.toLowerCase()) || 
                p.getId().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(p);
            }
        }
        adapter.updateData(filtered);
    }
}
