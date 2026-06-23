package com.example.mediqueue.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;

import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserManagementActivity extends AppCompatActivity implements UserAdapter.OnUserActionListener {
    private AdminViewModel viewModel;
    private RecyclerView rvUsers;
    private ImageView btnBack;
    private EditText etFullName, etEmail, etPhone, etPassword;
    private Spinner spinnerRole;
    private Button btnCreateUser;
    private ChipGroup cgUserFilters;
    private UserAdapter adapter;
    private List<User> allUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        btnBack = findViewById(R.id.btn_back);
        rvUsers = findViewById(R.id.rvUsers);
        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        spinnerRole = findViewById(R.id.spinner_role);
        btnCreateUser = findViewById(R.id.btn_create_user);
        cgUserFilters = findViewById(R.id.cgUserFilters);

        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        btnBack.setOnClickListener(v -> finish());

        setupRoleSpinner();
        setupFilters();

        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        viewModel.getAllStaff().observe(this, users -> {
            if (users != null) {
                allUsers = users;
                adapter = new UserAdapter(allUsers, this);
                rvUsers.setAdapter(adapter);
            }
        });

        btnCreateUser.setOnClickListener(v -> {
            String name = etFullName.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter full name", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Creating user: " + name, Toast.LENGTH_SHORT).show();
        });
    }

    private void setupRoleSpinner() {
        String[] roles = {"ADMIN", "DOCTOR", "RECEPTIONIST"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);
    }

    private void setupFilters() {
        cgUserFilters.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int id = checkedIds.get(0);
            filterUsers(id);
        });
    }

    private void filterUsers(int chipId) {
        List<User> filtered = allUsers.stream().filter(user -> {
            if (chipId == R.id.chipUserAll) return true;
            if (chipId == R.id.chipUserDoctors) return "DOCTOR".equalsIgnoreCase(user.getRole().name());
            if (chipId == R.id.chipUserReceptionists) return "RECEPTIONIST".equalsIgnoreCase(user.getRole().name());
            if (chipId == R.id.chipUserAdmins) return "ADMIN".equalsIgnoreCase(user.getRole().name());
            return true;
        }).collect(Collectors.toList());

        if (adapter != null) {
            adapter.updateData(filtered);
        }
    }

    @Override
    public void onView(User user) {
        Toast.makeText(this, "Viewing user: " + user.getFullName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEdit(User user) {
        Toast.makeText(this, "Editing user: " + user.getFullName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisable(User user) {
        Toast.makeText(this, "Disabling user: " + user.getFullName(), Toast.LENGTH_SHORT).show();
    }
}
