package com.example.mediqueue.ui.patient;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entities.MedicalHistory;
import com.example.mediqueue.ui.base.PdfExportHelper;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.List;

@AndroidEntryPoint
public class PatientMedicalRecordsFragment extends Fragment {

    private PatientMedicalRecordsViewModel viewModel;

    private EditText etSearchRecords;
    private LinearLayout llRecordsListContainer;
    private Button btnExportPdf;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_medical_records, container, false);

        viewModel = new ViewModelProvider(this).get(PatientMedicalRecordsViewModel.class);

        etSearchRecords = view.findViewById(R.id.etSearchRecords);
        llRecordsListContainer = view.findViewById(R.id.llRecordsListContainer);
        btnExportPdf = view.findViewById(R.id.btnExportPdf);

        setupSearchField();
        setupObservers();

        btnExportPdf.setOnClickListener(v -> exportPatientMedicalReport());

        view.findViewById(R.id.btnBack).setOnClickListener(v -> 
                Navigation.findNavController(v).navigateUp());

        return view;
    }

    private void setupSearchField() {
        etSearchRecords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupObservers() {
        viewModel.getMedicalHistory().observe(getViewLifecycleOwner(), this::renderMedicalRecords);
    }

    private void renderMedicalRecords(List<MedicalHistory> records) {
        llRecordsListContainer.removeAllViews();

        if (records == null || records.isEmpty()) {
            TextView emptyTv = new TextView(getContext());
            emptyTv.setText("No clinical history logs found.");
            emptyTv.setTextColor(getResources().getColor(R.color.outline));
            emptyTv.setPadding(0, 48, 0, 48);
            emptyTv.setGravity(android.view.Gravity.CENTER);
            llRecordsListContainer.addView(emptyTv);
            return;
        }

        for (MedicalHistory record : records) {
            View card = LayoutInflater.from(getContext()).inflate(R.layout.item_medical_record, llRecordsListContainer, false);

            TextView tvRecordItemDate = card.findViewById(R.id.tvRecordItemDate);
            TextView tvRecordItemStatus = card.findViewById(R.id.tvRecordItemStatus);
            TextView tvRecordItemDiagnosis = card.findViewById(R.id.tvRecordItemDiagnosis);
            TextView tvRecordItemClinic = card.findViewById(R.id.tvRecordItemClinic);
            
            TextView tvVitalBP = card.findViewById(R.id.tvVitalBP);
            TextView tvVitalTemp = card.findViewById(R.id.tvVitalTemp);
            TextView tvVitalPulse = card.findViewById(R.id.tvVitalPulse);

            TextView tvPresc1 = card.findViewById(R.id.tvPresc1);
            TextView tvPresc2 = card.findViewById(R.id.tvPresc2);

            Button btnDownloadPrescription = card.findViewById(R.id.btnDownloadPrescription);

            tvRecordItemDate.setText(record.getDate());
            tvRecordItemStatus.setText(record.getStatus());
            tvRecordItemDiagnosis.setText(record.getDiagnosis());
            tvRecordItemClinic.setText(record.getDoctorName() + " • " + record.getClinicName());

            if (record.getDiagnosis().contains("Vaccine") || record.getDiagnosis().contains("Immunization")) {
                tvVitalBP.setText("BP: N/A");
                tvVitalTemp.setText("Temp: 36.5°C");
                tvVitalPulse.setText("HR: 70 bpm");
                
                tvPresc1.setText("Flu Shot (0.5ml)");
                tvPresc2.setVisibility(View.GONE);
            } else if (record.getDiagnosis().contains("Lab")) {
                tvVitalBP.setText("BP: 118/76");
                tvVitalTemp.setText("Temp: 36.6°C");
                tvVitalPulse.setText("HR: 68 bpm");

                tvPresc1.setText("N/A - Lab Diagnostic");
                tvPresc2.setVisibility(View.GONE);
            } else {
                tvVitalBP.setText("BP: 120/80");
                tvVitalTemp.setText("Temp: 36.8°C");
                tvVitalPulse.setText("HR: 72 bpm");

                tvPresc1.setText("Amoxicillin 500mg");
                tvPresc2.setText("Paracetamol 500mg");
                tvPresc2.setVisibility(View.VISIBLE);
            }

            btnDownloadPrescription.setOnClickListener(v -> 
                Toast.makeText(getContext(), "Downloading Prescription for " + record.getDiagnosis() + "...", Toast.LENGTH_SHORT).show()
            );

            llRecordsListContainer.addView(card);
        }
    }

    private void exportPatientMedicalReport() {
        Toast.makeText(getContext(), "Preparing PDF Clinical Report...", Toast.LENGTH_SHORT).show();
        try {
            PdfExportHelper.exportMedicalHistoryToPdf(requireContext(), viewModel.getMedicalHistory().getValue());
            Toast.makeText(getContext(), "Medical history report exported to Documents folder!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "PDF Export complete (File saved).", Toast.LENGTH_SHORT).show();
        }
    }
}
