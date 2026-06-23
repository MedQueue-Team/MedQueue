package com.example.mediqueue.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class DoctorCallPatientFragment extends Fragment {

    private DoctorCallPatientViewModel viewModel;

    private ImageView btnBack;
    private TextView tvPatientName;
    private TextView tvPatientDetails;
    private TextView tvRoom;
    private TextView tvQueueNo;
    private TextView tvStatusText;
    private TextView tvSymptoms;
    private TextView tvApptType;
    private TextView tvApptDept;
    private TextView tvApptRoom;

    private MaterialButton btnStartConsultation;
    private MaterialButton btnSkipPatient;
    private MaterialButton btnMarkAbsent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_doctor_call_patient, container, false);

        initViews(root);
        viewModel = new ViewModelProvider(this).get(DoctorCallPatientViewModel.class);

        observeViewModel();
        setupListeners();

        return root;
    }

    private void initViews(View root) {
        btnBack = root.findViewById(R.id.btn_back);
        tvPatientName = root.findViewById(R.id.tv_patient_name);
        tvPatientDetails = root.findViewById(R.id.tv_patient_details);
        tvRoom = root.findViewById(R.id.tv_room);
        tvQueueNo = root.findViewById(R.id.tv_queue_no);
        tvStatusText = root.findViewById(R.id.tv_status_text);
        tvSymptoms = root.findViewById(R.id.tv_symptoms);
        tvApptType = root.findViewById(R.id.tv_appt_type);
        tvApptDept = root.findViewById(R.id.tv_appt_dept);
        tvApptRoom = root.findViewById(R.id.tv_appt_room);

        btnStartConsultation = root.findViewById(R.id.btn_start_consultation);
        btnSkipPatient = root.findViewById(R.id.btn_skip_patient);
        btnMarkAbsent = root.findViewById(R.id.btn_mark_absent);
    }

    private void observeViewModel() {
        viewModel.getPatientDetails().observe(getViewLifecycleOwner(), details -> {
            tvPatientName.setText(details.name);
            tvPatientDetails.setText(details.age + " Years • " + details.gender);
            tvRoom.setText(details.room);
            tvQueueNo.setText(details.id);
            tvSymptoms.setText(details.symptoms);
            tvApptType.setText(details.apptType);
            tvApptDept.setText(details.department);
            tvApptRoom.setText(details.room);
        });

        viewModel.getCallStatus().observe(getViewLifecycleOwner(), status -> {
            tvStatusText.setText(status);
        });
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        btnStartConsultation.setOnClickListener(v -> {
            viewModel.startConsultation();
            Toast.makeText(getContext(), "Starting consultation session", Toast.LENGTH_SHORT).show();
            // Navigate to structured consultation screen
            Navigation.findNavController(v).navigate(R.id.nav_doctor_consultation);
        });

        btnSkipPatient.setOnClickListener(v -> {
            viewModel.skipPatient();
            Toast.makeText(getContext(), "Patient skipped", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigateUp();
        });

        btnMarkAbsent.setOnClickListener(v -> {
            viewModel.markAbsent();
            Toast.makeText(getContext(), "Patient marked absent", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigateUp();
        });
    }
}
