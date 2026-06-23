package com.example.mediqueue.ui.receptionist;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediqueue.R;
import com.example.mediqueue.ui.base.BaseNavActivity;

public class NurseSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_settings);

        BaseNavActivity.enableToolbarBack(this, R.id.toolbar);
        setupClickListeners();
    }

    private void setupClickListeners() {
        findViewById(R.id.btnEditProfile).setOnClickListener(v ->
            Toast.makeText(this, "Opening Profile Editor...", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.cvNotifications).setOnClickListener(v ->
            Toast.makeText(this, "Notification Settings", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.cvSecurity).setOnClickListener(v ->
            Toast.makeText(this, "Privacy & Security Settings", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.cvUnits).setOnClickListener(v ->
            Toast.makeText(this, "Units of Measurement Preferences", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.cvLanguage).setOnClickListener(v ->
            Toast.makeText(this, "Language Settings", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.cvLogout).setOnClickListener(v -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            com.example.mediqueue.core.SessionManager sessionManager = new com.example.mediqueue.core.SessionManager(this);
            sessionManager.logout();
            android.content.Intent intent = new android.content.Intent(this, com.example.mediqueue.ui.login.LoginActivity.class);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
