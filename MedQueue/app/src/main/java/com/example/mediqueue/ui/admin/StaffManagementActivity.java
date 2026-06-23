package com.example.mediqueue.ui.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.R;
import com.example.mediqueue.ui.base.BaseNavActivity;

import java.util.ArrayList;
import java.util.List;

public class StaffManagementActivity extends AppCompatActivity {

    private AdminViewModel viewModel;
    private RecyclerView rvStaff;
    private StaffAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_management);

        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        BaseNavActivity.enableToolbarBack(this, R.id.toolbar);
        rvStaff = findViewById(R.id.rvStaff);
        setupRecyclerView();

        findViewById(R.id.fabAddStaff).setOnClickListener(v ->
            Toast.makeText(this, "Add new staff member", Toast.LENGTH_SHORT).show()
        );
    }

    private void setupRecyclerView() {
        viewModel.getStaffMembers().observe(this, staff -> {
            if (staff != null) {
                adapter = new StaffAdapter(staff);
                rvStaff.setLayoutManager(new LinearLayoutManager(this));
                rvStaff.setAdapter(adapter);
            }
        });
    }
}
