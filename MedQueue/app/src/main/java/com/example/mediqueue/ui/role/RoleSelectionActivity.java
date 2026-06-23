package com.example.mediqueue.ui.role;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mediqueue.R;
import com.example.mediqueue.core.UserRole;
import com.example.mediqueue.ui.login.LoginActivity;
import com.example.mediqueue.ui.patient.PatientDashboardActivity;
import com.google.android.material.card.MaterialCardView;

public class RoleSelectionActivity extends AppCompatActivity {
    private com.example.mediqueue.core.SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        sessionManager = new com.example.mediqueue.core.SessionManager(this);
        setupRoleCards();
    }

    private void setupRoleCards() {
        // Clinical Staff Roles Only


        // Nurse Card
        View nurseCard = findViewById(R.id.btnNurse);
        setupCard(nurseCard, R.drawable.ic_support_agent, R.color.secondary_fixed, 
                "Nurse", "Perform patient triage, monitor vitals, and manage clinical queue flow.");
        nurseCard.setOnClickListener(v -> navigateToNext("Nurse"));

        // Doctor Card
        View doctorCard = findViewById(R.id.btnDoctor);
        setupCard(doctorCard, R.drawable.ic_stethoscope, R.color.primary_fixed, 
                "Doctor", "Access patient records, log consultations, and issue digital prescriptions.");
        doctorCard.setOnClickListener(v -> navigateToNext("Doctor"));

        // Admin Card
        View adminCard = findViewById(R.id.btnAdmin);
        setupCard(adminCard, R.drawable.ic_admin_panel_settings, R.color.secondary_fixed, 
                "Clinic Administrator", "Oversee facility operations, manage staff shifts, and view performance data.");
        adminCard.setOnClickListener(v -> navigateToNext("Clinic Administrator"));
    }

    private void setupCard(View card, int iconRes, int bgColorRes, String title, String desc) {
        ImageView ivIcon = card.findViewById(R.id.ivIcon);
        TextView tvTitle = card.findViewById(R.id.tvTitle);
        TextView tvDescription = card.findViewById(R.id.tvDescription);
        MaterialCardView iconContainer = card.findViewById(R.id.iconContainer);

        ivIcon.setImageResource(iconRes);
        tvTitle.setText(title);
        tvDescription.setText(desc);
        iconContainer.setCardBackgroundColor(getResources().getColor(bgColorRes, getTheme()));
    }

    private void navigateToNext(String role) {
        UserRole userRole = UserRole.fromString(role);
        sessionManager.saveLastStaffRole(userRole);
        
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("SELECTED_ROLE", role);
        startActivity(intent);
    }
}
