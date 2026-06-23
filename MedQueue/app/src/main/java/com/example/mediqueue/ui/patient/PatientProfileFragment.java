package com.example.mediqueue.ui.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entities.PatientEntity;
import com.google.android.material.imageview.ShapeableImageView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PatientProfileFragment extends Fragment {

    private PatientProfileViewModel viewModel;

    private EditText etProfileName, etProfileEmail, etProfilePhone, etProfileDob;
    private EditText etEmergencyName, etEmergencyPhone;
    
    private Button btnBloodA, btnBloodB, btnBloodAB, btnBloodO;
    private Button btnBloodAMin, btnBloodBMin, btnBloodABMin, btnBloodOMin;
    
    private Button btnSaveProfile;
    private ShapeableImageView ivProfileImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_profile, container, false);

        viewModel = new ViewModelProvider(this).get(PatientProfileViewModel.class);

        // Bind Inputs
        etProfileName = view.findViewById(R.id.etProfileName);
        etProfileEmail = view.findViewById(R.id.etProfileEmail);
        etProfilePhone = view.findViewById(R.id.etProfilePhone);
        etProfileDob = view.findViewById(R.id.etProfileDob);

        etEmergencyName = view.findViewById(R.id.etEmergencyName);
        etEmergencyPhone = view.findViewById(R.id.etEmergencyPhone);

        // Bind Blood Buttons
        btnBloodA = view.findViewById(R.id.btnBloodA);
        btnBloodB = view.findViewById(R.id.btnBloodB);
        btnBloodAB = view.findViewById(R.id.btnBloodAB);
        btnBloodO = view.findViewById(R.id.btnBloodO);
        
        btnBloodAMin = view.findViewById(R.id.btnBloodAMin);
        btnBloodBMin = view.findViewById(R.id.btnBloodBMin);
        btnBloodABMin = view.findViewById(R.id.btnBloodABMin);
        btnBloodOMin = view.findViewById(R.id.btnBloodOMin);

        btnSaveProfile = view.findViewById(R.id.btnSaveProfile);
        ivProfileImage = view.findViewById(R.id.ivProfileImage);

        setupBloodTypeSelectors();
        setupObservers();

        // Save profile trigger
        btnSaveProfile.setOnClickListener(v -> {
            String name = etProfileName.getText().toString().trim();
            String email = etProfileEmail.getText().toString().trim();
            String phone = etProfilePhone.getText().toString().trim();
            String dob = etProfileDob.getText().toString().trim();
            viewModel.saveProfile(name, email, phone, dob);
        });

        // Back button click
        view.findViewById(R.id.btnBack).setOnClickListener(v -> 
                Navigation.findNavController(v).navigateUp());

        return view;
    }

    private void setupBloodTypeSelectors() {
        Button[] bloodButtons = {btnBloodA, btnBloodB, btnBloodAB, btnBloodO, btnBloodAMin, btnBloodBMin, btnBloodABMin, btnBloodOMin};
        String[] types = {"A+", "B+", "AB+", "O+", "A-", "B-", "AB-", "O-"};

        for (int i = 0; i < bloodButtons.length; i++) {
            final int index = i;
            bloodButtons[i].setOnClickListener(v -> {
                viewModel.setBloodType(types[index]);
                updateBloodTypeUI(bloodButtons[index], bloodButtons);
            });
        }
    }

    private void updateBloodTypeUI(Button selected, Button[] all) {
        for (Button btn : all) {
            if (btn == selected) {
                btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.primary)));
                btn.setTextColor(getResources().getColor(R.color.white));
            } else {
                btn.setBackgroundTintList(null);
                btn.setTextColor(getResources().getColor(R.color.primary));
            }
        }
    }

    private void setupObservers() {
        // Populate patient profile from DB
        viewModel.getPatient().observe(getViewLifecycleOwner(), patient -> {
            if (patient != null) {
                if (patient.fullName != null) etProfileName.setText(patient.fullName);
                if (patient.email != null) etProfileEmail.setText(patient.email);
                if (patient.phoneNumber != null) etProfilePhone.setText(patient.phoneNumber);
                if (patient.dateOfBirth != null) etProfileDob.setText(patient.dateOfBirth);
            }
            ivProfileImage.setImageResource(android.R.drawable.ic_menu_gallery);
        });

        viewModel.getSelectedBloodType().observe(getViewLifecycleOwner(), bloodType -> {
            Button[] bloodButtons = {btnBloodA, btnBloodB, btnBloodAB, btnBloodO, btnBloodAMin, btnBloodBMin, btnBloodABMin, btnBloodOMin};
            String[] types = {"A+", "B+", "AB+", "O+", "A-", "B-", "AB-", "O-"};
            for (int i = 0; i < types.length; i++) {
                if (types[i].equalsIgnoreCase(bloodType)) {
                    updateBloodTypeUI(bloodButtons[i], bloodButtons);
                    break;
                }
            }
        });

        viewModel.getSaveSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getContext(), "Profile changes saved successfully!", Toast.LENGTH_SHORT).show();
                viewModel.setSaveSuccess(false);
            }
        });
    }
}
