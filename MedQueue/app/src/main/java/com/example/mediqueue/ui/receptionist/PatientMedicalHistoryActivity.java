package com.example.mediqueue.ui.receptionist;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Patient Medical History Activity (Nurse View)
 * Nurse/staff view of a patient's complete medical history with vitals summary,
 * search/filter controls, and timeline of medical encounters.
 * Design reference: temp_designs/.../patient_medical_history_nurse_view/
 */
public class PatientMedicalHistoryActivity extends AppCompatActivity {

    private TextView txtPatientName, txtPatientInfo;
    private TextView txtBP, txtHR, txtSpO2, txtTemp;
    private RecyclerView recyclerEncounters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medical_history_nurse);

        initViews();
        loadMockData();
    }

    private void initViews() {
        txtPatientName = findViewById(R.id.txtPatientName);
        txtPatientInfo = findViewById(R.id.txtPatientInfo);
        txtBP = findViewById(R.id.txtBP);
        txtHR = findViewById(R.id.txtHR);
        txtSpO2 = findViewById(R.id.txtSpO2);
        txtTemp = findViewById(R.id.txtTemp);
        recyclerEncounters = findViewById(R.id.recyclerEncounters);

        recyclerEncounters.setLayoutManager(new LinearLayoutManager(this));

        // Back navigation is handled by parent activity if needed
    }

    private void loadMockData() {
        txtPatientName.setText("Amara Okafor");
        txtPatientInfo.setText("ID: LSK-KNH-004582 • 34 Years • Female");
        txtBP.setText("120/80");
        txtHR.setText("72 bpm");
        txtSpO2.setText("98%");
        txtTemp.setText("36.8°C");

        List<MedicalEncounter> encounters = new ArrayList<>();
        encounters.add(new MedicalEncounter(
                "OCT 24, 2023 — 09:15 AM",
                "Emergency Triage",
                "St. Mary's Clinic",
                "Dr. Elena Rodriguez",
                "Patient presented with acute abdominal pain and mild nausea. Vital signs stable but heart rate slightly elevated. Referred for immediate abdominal ultrasound.",
                "Ongoing"));
        encounters.add(new MedicalEncounter(
                "SEP 12, 2023 — 14:30 PM",
                "Chronic Condition Management",
                "City Health Annex",
                "Nurse Sarah Jenkins",
                "Follow-up for Type 2 Diabetes management. HbA1c levels showing improvement (6.8%). Patient reported consistent medication adherence.",
                "Completed"));
        encounters.add(new MedicalEncounter(
                "JUL 05, 2023 — 10:00 AM",
                "Immunization",
                "Central Vaccination Hub",
                "Vaccination Officer K. Phiri",
                "Influenza Quadrivalent Vaccine administered. Batch No: IZ-88902-X",
                "Completed"));

        MedicalEncounterAdapter adapter = new MedicalEncounterAdapter(encounters);
        recyclerEncounters.setAdapter(adapter);
    }
}
