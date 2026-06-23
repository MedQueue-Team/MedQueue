package com.example.mediqueue.ui.receptionist;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mediqueue.R;
import com.example.mediqueue.databinding.ActivityPatientRegistrationBinding;

public class PatientRegistrationActivity extends AppCompatActivity {

    private ActivityPatientRegistrationBinding binding;
    private TriageViewModel viewModel;
    private String selectedPriority = "MEDIUM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(TriageViewModel.class);

        setupToolbar();
        setupGenderSpinner();
        setupRelationshipSpinner();
        setupPriorityToggle();
        setupClickListeners();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupGenderSpinner() {
        String[] genders = new String[]{"Male", "Female", "Other", "Prefer not to say"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, genders);
        binding.actvGender.setAdapter(adapter);
    }

    private void setupRelationshipSpinner() {
        String[] relationships = new String[]{"Parent", "Spouse", "Sibling", "Child", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, relationships);
        binding.actvRelationship.setAdapter(adapter);
    }

    private void setupPriorityToggle() {
        binding.togglePriority.check(R.id.btnMedium);
        binding.togglePriority.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btnLow) selectedPriority = "LOW";
                else if (checkedId == R.id.btnMedium) selectedPriority = "MEDIUM";
                else if (checkedId == R.id.btnHigh) selectedPriority = "HIGH";
                else if (checkedId == R.id.btnEmergency) selectedPriority = "EMERGENCY";
            }
        });
    }

    private void setupClickListeners() {
        binding.btnRegister.setOnClickListener(v -> registerPatient());
        binding.btnCancel.setOnClickListener(v -> finish());
    }

    private void registerPatient() {
        String name = binding.etName.getText().toString().trim();
        String nrc = binding.etNrc.getText().toString().trim();
        String dob = binding.etDob.getText().toString().trim();
        String gender = binding.actvGender.getText().toString();
        String phone = binding.etPhone.getText().toString().trim();
        
        String nokName = binding.etNokName.getText().toString().trim();
        String relationship = binding.actvRelationship.getText().toString();
        String nokPhone = binding.etNokPhone.getText().toString().trim();
        
        String complaint = binding.etComplaint.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || complaint.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new patient
        String id = "LSK-KNH-" + (10000 + (int)(Math.random() * 90000));
        
        // Calculate age roughly from DOB or use a default if DOB is empty
        String age = dob.isEmpty() ? "Adult" : calculateAge(dob);

        TriagePatient patient = new TriagePatient(name, id, age, gender, selectedPriority, false, "Pending");
        patient.setPrimaryComplaint(complaint);
        patient.setNrcId(nrc);
        patient.setDob(dob);
        patient.setPhone(phone);
        patient.setNextOfKinName(nokName);
        patient.setNextOfKinRelationship(relationship);
        patient.setNextOfKinPhone(nokPhone);
        patient.setAssessed(false);

        viewModel.addPatient(patient);

        Toast.makeText(this, "Patient Registered Successfully: " + id, Toast.LENGTH_LONG).show();
        finish();
    }

    private String calculateAge(String dob) {
        // Placeholder for real date logic
        return "24 yrs"; 
    }
}
