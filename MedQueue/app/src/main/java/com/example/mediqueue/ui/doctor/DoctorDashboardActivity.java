package com.example.mediqueue.ui.doctor;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.example.mediqueue.R;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DoctorDashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    private void onDestinationChanged(androidx.navigation.NavController controller, androidx.navigation.NavDestination destination, android.os.Bundle arguments) {
        if (destination.getId() == R.id.nav_doctor_home) {
            bottomNav.setVisibility(View.VISIBLE);
        } else {
            bottomNav.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        bottomNav = findViewById(R.id.bottom_nav);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNav, navController);

        navController.addOnDestinationChangedListener(this::onDestinationChanged);
    }
}
