package com.example.mediqueue.ui.receptionist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mediqueue.R;
import com.example.mediqueue.databinding.FragmentPatientMedicalHistoryNurseBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PatientMedicalHistoryFragment extends Fragment {

    private FragmentPatientMedicalHistoryNurseBinding binding;
    private TriageViewModel viewModel;
    private MedicalEncounterAdapter adapter;
    private final List<MedicalEncounter> allEncounters = new ArrayList<>();
    private final List<MedicalEncounter> filteredEncounters = new ArrayList<>();
    
    private String selectedCategory = "ALL";
    private String searchQuery = "";
    private String patientId = "LSK-KNH-004582"; // Default fallback ID

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPatientMedicalHistoryNurseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(TriageViewModel.class);

        if (getArguments() != null && getArguments().getString("PATIENT_ID") != null) {
            patientId = getArguments().getString("PATIENT_ID");
        }

        setupToolbar();
        setupRecyclerView();
        setupFilters();
        setupSearch();
        observePatient();
        loadEncountersMockData();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).popBackStack());
    }

    private void setupRecyclerView() {
        binding.recyclerEncounters.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MedicalEncounterAdapter(filteredEncounters);
        binding.recyclerEncounters.setAdapter(adapter);
    }

    private void observePatient() {
        viewModel.getPatientById(patientId).observe(getViewLifecycleOwner(), patient -> {
            if (patient != null) {
                binding.txtPatientName.setText(patient.getName());
                binding.txtPatientInfo.setText("ID: " + patient.getId() + " • " + patient.getAge() + " • " + patient.getGender());
                
                // Update Vitals if available
                if (patient.getBloodPressure() != null && !patient.getBloodPressure().isEmpty()) {
                    binding.txtBP.setText(patient.getBloodPressure());
                }
                if (patient.getPulseRate() != null && !patient.getPulseRate().isEmpty()) {
                    binding.txtHR.setText(patient.getPulseRate() + " bpm");
                }
                if (patient.getTemperature() != null && !patient.getTemperature().isEmpty()) {
                    binding.txtTemp.setText(patient.getTemperature() + "°C");
                }
            }
        });
    }

    private void setupFilters() {
        binding.chipAll.setOnClickListener(v -> filterByCategory("ALL", binding.chipAll));
        binding.chipConsultations.setOnClickListener(v -> filterByCategory("Consultations", binding.chipConsultations));
        binding.chipLabResults.setOnClickListener(v -> filterByCategory("Lab Results", binding.chipLabResults));
        binding.chipVaccinations.setOnClickListener(v -> filterByCategory("Vaccinations", binding.chipVaccinations));
        binding.chipMedications.setOnClickListener(v -> filterByCategory("Medications", binding.chipMedications));
    }

    private void filterByCategory(String category, TextView activeChip) {
        selectedCategory = category;
        
        // Reset all chips style
        resetChipStyle(binding.chipAll);
        resetChipStyle(binding.chipConsultations);
        resetChipStyle(binding.chipLabResults);
        resetChipStyle(binding.chipVaccinations);
        resetChipStyle(binding.chipMedications);

        // Highlight selected chip
        activeChip.setBackgroundResource(R.drawable.chip_selected);
        activeChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));

        applyFilters();
    }

    private void resetChipStyle(TextView chip) {
        chip.setBackgroundResource(R.drawable.chip_unselected);
        chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_surface_variant));
    }

    private void setupSearch() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery = s.toString().toLowerCase();
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void applyFilters() {
        filteredEncounters.clear();
        for (MedicalEncounter enc : allEncounters) {
            boolean matchesCategory = selectedCategory.equals("ALL") || enc.getTitle().equalsIgnoreCase(selectedCategory) || enc.getStatus().equalsIgnoreCase(selectedCategory);
            
            // Allow general search match on text
            boolean matchesSearch = searchQuery.isEmpty() ||
                    enc.getTitle().toLowerCase().contains(searchQuery) ||
                    enc.getSummary().toLowerCase().contains(searchQuery) ||
                    enc.getProvider().toLowerCase().contains(searchQuery) ||
                    enc.getFacility().toLowerCase().contains(searchQuery);

            if (matchesCategory && matchesSearch) {
                filteredEncounters.add(enc);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void loadEncountersMockData() {
        allEncounters.clear();
        
        allEncounters.add(new MedicalEncounter(
                "OCT 24, 2023 — 09:15 AM",
                "Emergency Triage",
                "St. Mary's Clinic",
                "Dr. Elena Rodriguez",
                "Patient presented with acute abdominal pain and mild nausea. Vital signs stable but heart rate slightly elevated. Referred for immediate abdominal ultrasound.",
                "Ongoing"));
                
        allEncounters.add(new MedicalEncounter(
                "SEP 12, 2023 — 14:30 PM",
                "Chronic Condition Management",
                "City Health Annex",
                "Nurse Sarah Jenkins",
                "Follow-up for Type 2 Diabetes management. HbA1c levels showing improvement (6.8%). Patient reported consistent medication adherence.",
                "Completed"));
                
        allEncounters.add(new MedicalEncounter(
                "JUL 05, 2023 — 10:00 AM",
                "Immunization",
                "Central Vaccination Hub",
                "Vaccination Officer K. Phiri",
                "Influenza Quadrivalent Vaccine administered. Batch No: IZ-88902-X | Expiry: 12/2024",
                "Completed"));

        allEncounters.add(new MedicalEncounter(
                "MAY 20, 2023 — 11:00 AM",
                "Lab Results",
                "St. Mary's Pathology Lab",
                "Pathologist Dr. A. Banda",
                "Comprehensive metabolic panel and lipid panel completed. Cholesterol slightly elevated (220 mg/dL). All other markers within normal limits.",
                "Completed"));

        allEncounters.add(new MedicalEncounter(
                "FEB 14, 2023 — 08:30 AM",
                "Medications",
                "St. Mary's Pharmacy",
                "Pharmacist J. Tembo",
                "Dispensed Metformin 500mg (30-day supply) and Lipitor 10mg (30-day supply). Counseled on taking Metformin with meals.",
                "Completed"));

        applyFilters();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
