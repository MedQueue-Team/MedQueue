package com.example.mediqueue.ui.base;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mediqueue.R;
import com.example.mediqueue.ui.admin.AppointmentOverviewActivity;
import com.example.mediqueue.ui.admin.ClinicAdminSettingsActivity;
import com.example.mediqueue.ui.admin.StaffManagementActivity;
import com.example.mediqueue.ui.receptionist.NurseRecordsActivity;
import com.example.mediqueue.ui.receptionist.NurseSettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Base activity for role dashboards only. Child screens use standard back navigation.
 */
public abstract class BaseNavActivity extends AppCompatActivity {

    public enum DashboardRole {
        PATIENT(R.menu.patient_bottom_nav_menu),
        DOCTOR(R.menu.doctor_bottom_nav_menu),
        ADMIN(R.menu.admin_bottom_nav_menu),
        NURSE(R.menu.menu_nurse_dashboard);

        @MenuRes
        final int menuRes;

        DashboardRole(@MenuRes int menuRes) {
            this.menuRes = menuRes;
        }
    }

    protected BottomNavigationView bottomNavigation;

    protected void setupDashboardNavigation(@NonNull DashboardRole role, int selectedItemId) {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        if (bottomNavigation == null) {
            return;
        }

        bottomNavigation.getMenu().clear();
        bottomNavigation.inflateMenu(role.menuRes);
        bottomNavigation.setSelectedItemId(selectedItemId);
        bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == selectedItemId) {
                return true;
            }
            return navigateFromDashboard(role, item.getItemId());
        });
    }

    protected void openChildScreen(Class<?> destination) {
        startActivity(new Intent(this, destination));
    }

    public static void enableToolbarBack(AppCompatActivity activity, int toolbarId) {
        Toolbar toolbar = activity.findViewById(toolbarId);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener(v -> activity.finish());
        }
    }

    private boolean navigateFromDashboard(DashboardRole role, int itemId) {
        switch (role) {
            case PATIENT:
                return navigatePatient(itemId);
            case DOCTOR:
                return navigateDoctor(itemId);
            case ADMIN:
                return navigateAdmin(itemId);
            case NURSE:
                return navigateNurse(itemId);
            default:
                return false;
        }
    }

    private boolean navigatePatient(int itemId) {
        if (itemId == R.id.nav_home) {
            return true;
        }
        if (itemId == R.id.nav_appts) {
            showComingSoon();
            return true;
        }
        if (itemId == R.id.nav_records) {
            showComingSoon();
            return true;
        }
        if (itemId == R.id.nav_profile) {
            showComingSoon();
            return true;
        }
        return false;
    }

    private boolean navigateDoctor(int itemId) {
        if (itemId == R.id.nav_queue || itemId == R.id.nav_home) {
            return true;
        }
        if (itemId == R.id.nav_settings) {
            showComingSoon();
            return true;
        }
        showComingSoon();
        return false;
    }

    private boolean navigateAdmin(int itemId) {
        if (itemId == R.id.nav_admin_home) {
            return true;
        }
        if (itemId == R.id.nav_staff) {
            openChildScreen(StaffManagementActivity.class);
            return true;
        }
        if (itemId == R.id.nav_analytics) {
            openChildScreen(AppointmentOverviewActivity.class);
            return true;
        }
        if (itemId == R.id.nav_admin_settings) {
            openChildScreen(ClinicAdminSettingsActivity.class);
            return true;
        }
        showComingSoon();
        return false;
    }

    private boolean navigateNurse(int itemId) {
        if (itemId == R.id.nav_home) {
            return true;
        }
        if (itemId == R.id.nav_records) {
            openChildScreen(NurseRecordsActivity.class);
            return true;
        }
        if (itemId == R.id.nav_settings) {
            openChildScreen(NurseSettingsActivity.class);
            return true;
        }
        showComingSoon();
        return false;
    }

    private void showComingSoon() {
        Toast.makeText(this, "Feature coming soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            super.onBackPressed();
        } else {
            finish();
        }
    }
}
