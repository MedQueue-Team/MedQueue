package com.example.mediqueue.ui.receptionist;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.mediqueue.R;
import com.example.mediqueue.databinding.ActivityTriageAssessmentBinding;
import com.google.android.material.card.MaterialCardView;

public class TriageAssessmentActivity extends AppCompatActivity {

    private ActivityTriageAssessmentBinding binding;
    private TriageViewModel viewModel;
    private TriagePatient currentPatient;
    private String selectedPriority = "MEDIUM";
    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTriageAssessmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(TriageViewModel.class);
        patientId = getIntent().getStringExtra("PATIENT_ID");

        setupToolbar();
        setupClickListeners();
        updatePrioritySelection();
        observePatient();
    }

    private void observePatient() {
        if (patientId != null) {
            viewModel.getPatientById(patientId).observe(this, patient -> {
                if (patient != null) {
                    currentPatient = patient;
                    selectedPriority = patient.getPriority();
                    updatePrioritySelection();
                    
                    // Fill Header Details
                    binding.tvPatientName.setText(patient.getName());
                    binding.tvPatientDetails.setText("ID: " + patient.getId() + " • " + patient.getAge() + " • " + patient.getGender());
                    binding.tvPatientInitials.setText(getInitials(patient.getName()));
                    
                    // Fill vitals if they exist
                    if (patient.getBloodPressure() != null) binding.etBP.setText(patient.getBloodPressure());
                    if (patient.getTemperature() != null) binding.etTemp.setText(patient.getTemperature());
                    if (patient.getPulseRate() != null) binding.etPulse.setText(patient.getPulseRate());
                    if (patient.getPrimaryComplaint() != null) binding.etComplaint.setText(patient.getPrimaryComplaint());
                }
            });
        }
    }

    private String getInitials(String name) {
        if (name == null || name.isEmpty()) return "??";
        String[] parts = name.split(" ");
        if (parts.length >= 2) {
            return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
        }
        return name.substring(0, Math.min(name.length(), 2)).toUpperCase();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupClickListeners() {
        binding.cardEmergency.setOnClickListener(v -> selectPriority("EMERGENCY"));
        binding.cardHigh.setOnClickListener(v -> selectPriority("HIGH"));
        binding.cardMedium.setOnClickListener(v -> selectPriority("MEDIUM"));
        binding.cardLow.setOnClickListener(v -> selectPriority("LOW"));

        binding.btnCompleteAssessment.setOnClickListener(v -> saveAssessment());
        
        binding.chipAddTag.setOnClickListener(v -> {
            // Simple logic to add a mock tag for demo
            addSymptomTag("New Symptom");
        });

        // Add text watchers for AI suggestions
        android.text.TextWatcher aiWatcher = new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(android.text.Editable s) {
                runAISuggestionLogic();
            }
        };
        binding.etBP.addTextChangedListener(aiWatcher);
        binding.etTemp.addTextChangedListener(aiWatcher);
        binding.etPulse.addTextChangedListener(aiWatcher);

        // Accessibility Support
        binding.cardEmergency.setContentDescription("Select Emergency Priority");
        binding.cardHigh.setContentDescription("Select High Priority");
        binding.cardMedium.setContentDescription("Select Medium Priority");
        binding.cardLow.setContentDescription("Select Low Priority");
        binding.btnCompleteAssessment.setContentDescription("Submit triage assessment");
    }

    private void addSymptomTag(String tag) {
        com.google.android.material.chip.Chip chip = new com.google.android.material.chip.Chip(this);
        chip.setText(tag);
        chip.setChipBackgroundColorResource(R.color.secondary_container);
        chip.setCloseIconEnabled(true);
        chip.setOnCloseIconClickListener(v -> binding.chipGroupTags.removeView(chip));
        binding.chipGroupTags.addView(chip, binding.chipGroupTags.getChildCount() - 1);
    }

    private void selectPriority(String priority) {
        selectedPriority = priority;
        updatePrioritySelection();
    }

    private void updatePrioritySelection() {
        // Reset all cards
        resetCardStyle(binding.cardEmergency, R.color.error_container, R.color.error);
        resetCardStyle(binding.cardHigh, R.color.surface_container_lowest, R.color.outline_variant);
        resetCardStyle(binding.cardMedium, R.color.surface_container_lowest, R.color.outline_variant);
        resetCardStyle(binding.cardLow, R.color.surface_container_lowest, R.color.outline_variant);

        // Highlight selected
        switch (selectedPriority) {
            case "EMERGENCY":
                highlightCard(binding.cardEmergency, R.color.error_container, R.color.error);
                break;
            case "HIGH":
                highlightCard(binding.cardHigh, R.color.tertiary_fixed, R.color.tertiary);
                break;
            case "MEDIUM":
                highlightCard(binding.cardMedium, R.color.secondary_container, R.color.secondary);
                break;
            case "LOW":
                highlightCard(binding.cardLow, R.color.surface_container_highest, R.color.outline);
                break;
        }
    }

    private void resetCardStyle(MaterialCardView card, int bgColorRes, int strokeColorRes) {
        card.setCardBackgroundColor(ContextCompat.getColor(this, bgColorRes));
        card.setStrokeColor(ContextCompat.getColor(this, strokeColorRes));
        card.setStrokeWidth(2);
    }

    private void highlightCard(MaterialCardView card, int bgColorRes, int strokeColorRes) {
        card.setCardBackgroundColor(ContextCompat.getColor(this, bgColorRes));
        card.setStrokeColor(ContextCompat.getColor(this, strokeColorRes));
        card.setStrokeWidth(6);
    }

    private void runAISuggestionLogic() {
        // Mock AI Logic: Simple rule-based engine
        String bp = binding.etBP.getText().toString();
        String temp = binding.etTemp.getText().toString();
        String pulse = binding.etPulse.getText().toString();

        try {
            // Check for Emergency conditions
            if (bp.contains("/") && Integer.parseInt(bp.split("/")[0]) > 180) {
                suggestPriority("EMERGENCY", "Critical BP detected (>180 systolic)");
                return;
            }
            
            if (!temp.isEmpty() && Double.parseDouble(temp) > 39.5) {
                suggestPriority("EMERGENCY", "Severe Hyperthermia detected (>39.5°C)");
                return;
            }

            // Check for High Priority
            if (!pulse.isEmpty() && (Integer.parseInt(pulse) > 120 || Integer.parseInt(pulse) < 50)) {
                suggestPriority("HIGH", "Abnormal pulse rate detected.");
                return;
            }
        } catch (Exception ignored) {}
    }

    private void suggestPriority(String priority, String reason) {
        // UI feedback for AI suggestion could go here
        // For now, we'll just auto-select for the demo
        selectPriority(priority);
        Toast.makeText(this, "AI Suggestion: " + priority + "\n" + reason, Toast.LENGTH_SHORT).show();
    }

    private void saveAssessment() {
        if (currentPatient != null) {
            currentPatient.setPriority(selectedPriority);
            currentPatient.setAssessed(true);
            currentPatient.setBloodPressure(binding.etBP.getText().toString());
            currentPatient.setTemperature(binding.etTemp.getText().toString());
            currentPatient.setPulseRate(binding.etPulse.getText().toString());
            currentPatient.setPrimaryComplaint(binding.etComplaint.getText().toString());
            
            viewModel.updatePatient(currentPatient);
            Toast.makeText(this, "Assessment Saved Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error: Patient not found", Toast.LENGTH_SHORT).show();
        }
    }
}
