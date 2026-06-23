package com.example.mediqueue.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.mediqueue.R;
import com.google.android.material.button.MaterialButton;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DoctorConsultationFragment extends Fragment {

    private DoctorConsultationViewModel viewModel;

    private ImageView btnBack;
    private ImageView btnProfile;

    private TextView tvPatientName;
    private TextView tvPatientDetails;
    private TextView tvPatientId;
    private TextView tvStatusTag;
    private TextView tvQueueNo;

    // Accordions
    private View headerHistory;
    private View contentHistory;
    private ImageView arrowHistory;

    private View headerConsultation;
    private View contentConsultation;
    private ImageView arrowConsultation;

    // Inputs
    private EditText etSymptoms;
    private EditText etDiagnosis;
    private EditText etPrescription;
    private EditText etNotes;

    // Buttons
    private MaterialButton btnSaveRecord;
    private MaterialButton btnCompleteConsultation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_doctor_consultation, container, false);

        initViews(root);
        viewModel = new ViewModelProvider(this).get(DoctorConsultationViewModel.class);

        observeViewModel();
        setupListeners();
        setupAccordionStates();

        return root;
    }

    private void initViews(View root) {
        btnBack = root.findViewById(R.id.btnBack);
        btnProfile = root.findViewById(R.id.btnProfile);

        tvPatientName = root.findViewById(R.id.tvPatientName);
        tvPatientDetails = root.findViewById(R.id.tvPatientDetails);
        tvPatientId = root.findViewById(R.id.tvPatientId);
        tvStatusTag = root.findViewById(R.id.tvPatientStatus);
        tvQueueNo = root.findViewById(R.id.tvPatientRoom);

        headerHistory = root.findViewById(R.id.rlMedicalHistoryHeader);
        contentHistory = root.findViewById(R.id.llMedicalHistoryContent);
        arrowHistory = root.findViewById(R.id.ivMedicalHistoryExpand);

        headerConsultation = root.findViewById(R.id.rlConsultationHeader);
        contentConsultation = root.findViewById(R.id.llConsultationContent);
        arrowConsultation = root.findViewById(R.id.ivConsultationExpand);

        etSymptoms = root.findViewById(R.id.etSymptoms);
        etDiagnosis = root.findViewById(R.id.etDiagnosis);
        etPrescription = root.findViewById(R.id.etPrescription);
        etNotes = root.findViewById(R.id.etNotes);

        btnSaveRecord = root.findViewById(R.id.btnSaveRecord);
        btnCompleteConsultation = root.findViewById(R.id.btnCompleteConsultation);
    }

    private void setupAccordionStates() {
        // Set initial rotation for expanded state
        arrowHistory.setRotation(180);
        arrowConsultation.setRotation(180);
    }

    private void observeViewModel() {
        viewModel.getPatientInfo().observe(getViewLifecycleOwner(), info -> {
            tvPatientName.setText(info.name);
            tvPatientDetails.setText(info.age + "Y, " + info.gender + " • " + info.bloodType);
            tvPatientId.setText("ID: " + info.patientId);
            tvStatusTag.setText(info.status);
            tvQueueNo.setText(info.id);
        });

        viewModel.getSaveSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getContext(), "Consultation record saved offline successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        btnProfile.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Profile clicked", Toast.LENGTH_SHORT).show();
        });

        headerHistory.setOnClickListener(v -> toggleSection(contentHistory, arrowHistory));
        headerConsultation.setOnClickListener(v -> toggleSection(contentConsultation, arrowConsultation));

        btnSaveRecord.setOnClickListener(v -> {
            viewModel.saveRecord(
                    etSymptoms.getText().toString(),
                    etDiagnosis.getText().toString(),
                    etPrescription.getText().toString(),
                    etNotes.getText().toString()
            );
        });

        btnCompleteConsultation.setOnClickListener(v -> {
            viewModel.saveRecord(
                    etSymptoms.getText().toString(),
                    etDiagnosis.getText().toString(),
                    etPrescription.getText().toString(),
                    etNotes.getText().toString()
            );
            Toast.makeText(getContext(), "Consultation complete! Syncing details...", Toast.LENGTH_SHORT).show();
            // Navigate back to Doctor Dashboard / Home destination
            Navigation.findNavController(v).navigate(R.id.nav_doctor_home);
        });
    }

    private void toggleSection(View content, ImageView arrow) {
        if (content.getVisibility() == View.VISIBLE) {
            content.setVisibility(View.GONE);
            arrow.animate().rotation(0).setDuration(200).start();
        } else {
            content.setVisibility(View.VISIBLE);
            arrow.animate().rotation(180).setDuration(200).start();
        }
    }
}
