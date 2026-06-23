package com.example.mediqueue.ui.admin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;

import com.example.mediqueue.R;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentOverviewActivity extends AppCompatActivity {
    private AdminViewModel viewModel;
    private RecyclerView rvAppointments;
    private ImageView btnBack, btnRefresh;
    private EditText etSearch;
    private ChipGroup cgFilters;
    private AppointmentOverviewAdapter adapter;
    private List<Appointment> allAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_overview);

        btnBack = findViewById(R.id.btn_back);
        btnRefresh = findViewById(R.id.btn_refresh);
        rvAppointments = findViewById(R.id.rv_appointments);
        etSearch = findViewById(R.id.et_search);
        cgFilters = findViewById(R.id.cgFilters);

        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        btnBack.setOnClickListener(v -> finish());
        btnRefresh.setOnClickListener(v -> {
            viewModel.getAppointments().removeObservers(this);
            viewModel.getAppointments().observe(this, appointments -> {
                if (appointments != null) {
                    allAppointments = appointments;
                    filterAppointments();
                }
            });
        });

        setupSearch();
        setupFilters();

        rvAppointments.setLayoutManager(new LinearLayoutManager(this));
        
        viewModel.getAppointments().observe(this, appointments -> {
            if (appointments != null) {
                allAppointments = appointments;
                filterAppointments();
            }
        });
    }

    private void setupFilters() {
        if (cgFilters != null) {
            cgFilters.setOnCheckedStateChangeListener((group, checkedIds) -> {
                if (!checkedIds.isEmpty()) {
                    filterAppointments();
                }
            });
        }
    }

    private void setupSearch() {
        if (etSearch != null) {
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int after) {
                    filterAppointments();
                }
                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void filterAppointments() {
        if (allAppointments == null) return;

        String query = (etSearch != null) ? etSearch.getText().toString().toLowerCase().trim() : "";
        int selectedChipId = (cgFilters != null) ? cgFilters.getCheckedChipId() : R.id.filter_all;
        
        List<Appointment> filtered = allAppointments.stream()
            .filter(a -> (query.isEmpty() || a.patientName.toLowerCase().contains(query) || a.doctorName.toLowerCase().contains(query)))
            .filter(a -> {
            if (selectedChipId == R.id.filter_all) return true;
            if (selectedChipId == R.id.filter_pending) return a.status == Appointment.Status.PENDING;
            if (selectedChipId == R.id.filter_approved) return a.status == Appointment.Status.APPROVED;
            if (selectedChipId == R.id.filter_rejected) return a.status == Appointment.Status.REJECTED;
            if (selectedChipId == R.id.filter_completed) return a.status == Appointment.Status.COMPLETED;
                return true;
            })
            .collect(Collectors.toList());

        if (adapter == null) {
            adapter = new AppointmentOverviewAdapter(filtered);
            rvAppointments.setAdapter(adapter);
        } else {
            adapter.updateData(filtered);
        }
    }
}
