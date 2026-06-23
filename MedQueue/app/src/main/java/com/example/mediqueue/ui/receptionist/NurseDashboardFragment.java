package com.example.mediqueue.ui.receptionist;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mediqueue.R;
import com.example.mediqueue.databinding.FragmentNurseDashboardBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NurseDashboardFragment extends Fragment {

    private FragmentNurseDashboardBinding binding;
    private TriageViewModel viewModel;
    private TriageAdapter adapter;
    private final List<TriagePatient> allPatients = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNurseDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(TriageViewModel.class);

        initViews();
        observeViewModel();
    }

    private void initViews() {
        binding.rvTriageQueue.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TriageAdapter(new ArrayList<>());
        binding.rvTriageQueue.setAdapter(adapter);
        
        binding.fabAddPatient.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), PatientRegistrationActivity.class));
        });

        binding.ivNotifications.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Notifications coming soon", Toast.LENGTH_SHORT).show();
        });

        binding.ivSettings.setOnClickListener(v -> {
            try {
                androidx.navigation.Navigation.findNavController(v)
                    .navigate(R.id.nav_settings);
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Error navigating to settings", Toast.LENGTH_SHORT).show();
            }
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
            try {
                String nextPatientId = "LSK-KNH-004582";
                for (TriagePatient p : allPatients) {
                    if (!p.isAssessed()) {
                        nextPatientId = p.getId();
                        break;
                    }
                }
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putString("PATIENT_ID", nextPatientId);
                androidx.navigation.Navigation.findNavController(v)
                    .navigate(R.id.patientMedicalHistoryFragment, bundle);
            } catch (Exception e) {
                Intent intent = new Intent(requireContext(), PatientMedicalHistoryActivity.class);
                intent.putExtra("PATIENT_ID", "LSK-KNH-004582");
                startActivity(intent);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getTriageQueue().observe(getViewLifecycleOwner(), patients -> {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
