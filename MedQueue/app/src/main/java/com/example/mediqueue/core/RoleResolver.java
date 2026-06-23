package com.example.mediqueue.core;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.example.mediqueue.ui.admin.AdminDashboardActivity;
import com.example.mediqueue.ui.doctor.DoctorDashboardActivity;
import com.example.mediqueue.ui.patient.PatientDashboardActivity;
import com.example.mediqueue.ui.receptionist.NurseDashboardActivity;

public class RoleResolver {

    /**
     * Resolves the appropriate Dashboard activity for a given role.
     * 
     * @param role The UserRole to resolve.
     * @return The Activity class for the dashboard.
     */
    public static Class<?> getDashboardActivity(@NonNull UserRole role) {
        switch (role) {
            case PATIENT:
                return PatientDashboardActivity.class;
            case NURSE:
                return com.example.mediqueue.MainActivity.class;
            case DOCTOR:
                return DoctorDashboardActivity.class;
            case ADMIN:
                return AdminDashboardActivity.class;
            default:
                // Default fallback
                return PatientDashboardActivity.class;
        }
    }

    /**
     * Creates an intent for the appropriate dashboard.
     * 
     * @param context Application or Activity context.
     * @param role The user role.
     * @return Intent ready to be started.
     */
    public static Intent getDashboardIntent(@NonNull Context context, @NonNull UserRole role) {
        Intent intent = new Intent(context, getDashboardActivity(role));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }
}
