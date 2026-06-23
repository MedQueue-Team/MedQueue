package com.example.mediqueue.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.mediqueue.R;

public class AdminDashboardActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        bottomNav = findViewById(R.id.bottom_navigation);
        Button btnNavAppts = findViewById(R.id.btn_nav_appointments);
        Button btnNavSchedule = findViewById(R.id.btn_nav_schedule);
        Button btnNavUsers = findViewById(R.id.btn_nav_users);

        btnNavAppts.setOnClickListener(v -> startActivity(new Intent(this, AppointmentOverviewActivity.class)));
        btnNavSchedule.setOnClickListener(v -> startActivity(new Intent(this, ScheduleManagementActivity.class)));
        btnNavUsers.setOnClickListener(v -> startActivity(new Intent(this, UserManagementActivity.class)));

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_admin_home) {
                return true;
            } else if (id == R.id.nav_analytics) {
                startActivity(new Intent(this, AppointmentOverviewActivity.class));
                return true;
            } else if (id == R.id.nav_staff) {
                startActivity(new Intent(this, UserManagementActivity.class));
                return true;
            } else if (id == R.id.nav_admin_settings) {
                startActivity(new Intent(this, ClinicAdminSettingsActivity.class));
                return true;
            }
            return false;
        });
    }
}
