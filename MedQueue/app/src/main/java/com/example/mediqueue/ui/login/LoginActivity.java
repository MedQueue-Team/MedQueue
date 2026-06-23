package com.example.mediqueue.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mediqueue.core.RoleResolver;
import com.example.mediqueue.core.SessionManager;
import com.example.mediqueue.core.UserRole;
import com.example.mediqueue.databinding.ActivityLoginBinding;
import com.example.mediqueue.ui.auth.AuthViewModel;
import com.example.mediqueue.ui.role.RoleSelectionActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        
        String selectedRoleExtra = getIntent().getStringExtra("SELECTED_ROLE");
        
        initViews();
        observeViewModel(selectedRoleExtra);
    }

    private void initViews() {
        binding.btnLogin.setOnClickListener(v -> {
            String email = getText(binding.etPhoneEmail);
            String password = getText(binding.etPassword);

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all credentials", Toast.LENGTH_SHORT).show();
            } else {
                performLogin(email, password);
            }
        });

        binding.btnStaffPortal.setOnClickListener(v -> {
            Intent intent = new Intent(this, RoleSelectionActivity.class);
            startActivity(intent);
        });

        binding.tvForgotPassword.setOnClickListener(v ->
            Toast.makeText(this, "Forgot password functionality coming soon", Toast.LENGTH_SHORT).show()
        );

        binding.btnGoogleLogin.setOnClickListener(v ->
            Toast.makeText(this, "Google Sign-In integration coming soon", Toast.LENGTH_SHORT).show()
        );

        // Update staff portal text based on last role
        UserRole lastRole = sessionManager.getLastStaffRole();
        if (lastRole != UserRole.UNKNOWN) {
            String name = lastRole.name().toUpperCase();
            binding.btnStaffPortal.setText("CONTINUE AS " + name);
        }
    }

    private void observeViewModel(String selectedRoleExtra) {
        authViewModel.getLoginResponse().observe(this, new Observer<com.example.mediqueue.api.ApiModels.LoginResponse>() {
            @Override
            public void onChanged(com.example.mediqueue.api.ApiModels.LoginResponse response) {
                if (response != null && response.isSuccess()) {
                    String email = getText(binding.etPhoneEmail);
                    UserRole role = UserRole.fromString(response.role);
                    if (role == UserRole.UNKNOWN) {
                        role = resolveRole(selectedRoleExtra, email);
                    }
                    sessionManager.saveSession(response.token, role);

                    // Show message if logged in locally
                    if (response.message != null && response.message.contains("locally")) {
                        Toast.makeText(LoginActivity.this, response.message, Toast.LENGTH_SHORT).show();
                    }

                    navigateToDashboard(role);
                }
            }
        });

        authViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }
        });

        authViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                binding.btnLogin.setEnabled(!isLoading);
            }
        });
    }

    private void performLogin(String email, String password) {
        authViewModel.login(email, password);
    }

    private String getText(android.widget.EditText editText) {
        return editText.getText() != null ? editText.getText().toString() : "";
    }

    private void navigateToDashboard(UserRole role) {
        if (role == UserRole.UNKNOWN) {
            Toast.makeText(this, "Unknown role. Please try again.", Toast.LENGTH_SHORT).show();
            binding.btnLogin.setEnabled(true);
            return;
        }
        startActivity(RoleResolver.getDashboardIntent(this, role));
        finish();
    }

    private UserRole resolveRole(String selectedRole, String email) {
        if (selectedRole != null) {
            UserRole role = UserRole.fromString(selectedRole);
            if (role != UserRole.UNKNOWN) return role;
        }
        
        String input = email.toLowerCase();
        if (input.contains("doctor")) return UserRole.DOCTOR;
        if (input.contains("nurse") || input.contains("reception")) return UserRole.NURSE;
        if (input.contains("admin")) return UserRole.ADMIN;

        return UserRole.PATIENT;
    }
}
