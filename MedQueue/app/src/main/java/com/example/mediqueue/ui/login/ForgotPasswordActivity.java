package com.example.mediqueue.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediqueue.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private MaterialButton btnSendCode;
    private TextView tvBackToLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);
        btnSendCode = findViewById(R.id.btnSendCode);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);
        progressBar = findViewById(R.id.progressBar);

        btnSendCode.setOnClickListener(v -> {
            String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
            if (email.isEmpty()) {
                etEmail.setError("Email is required");
                return;
            }

            // Simulate API call
            progressBar.setVisibility(View.VISIBLE);
            btnSendCode.setEnabled(false);

            v.postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                btnSendCode.setEnabled(true);
                Toast.makeText(this, "Verification code sent to " + email, Toast.LENGTH_SHORT).show();
                // We would typically navigate to a VerifyCodeActivity here
            }, 1500);
        });

        tvBackToLogin.setOnClickListener(v -> {
            finish();
        });
    }
}
