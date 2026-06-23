package com.example.mediqueue.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class DoctorAppointmentsFragment extends Fragment {

    private DoctorAppointmentsViewModel viewModel;
    private RecyclerView rvAppointments;
    private EditText etSearch;
    private ChipGroup cgFilters;
    private TextView tvDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_doctor_appointments, container, false);

        initViews(root);
        viewModel = new ViewModelProvider(this).get(DoctorAppointmentsViewModel.class);

        observeViewModel();
        setupListeners(root);

        return root;
    }

    private void initViews(View root) {
        rvAppointments = root.findViewById(R.id.rvAppointments);
        etSearch = root.findViewById(R.id.etSearch);
        cgFilters = root.findViewById(R.id.cgFilters);
        tvDate = root.findViewById(R.id.tvDate);
        
        rvAppointments.setLayoutManager(new LinearLayoutManager(getContext()));
        tvDate.setText("Monday, 17 May 2026");
    }

    private void setupListeners(View root) {
        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int after) {
                viewModel.setSearchQuery(s.toString());
            }
            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        cgFilters.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chipAll) viewModel.setFilter(DoctorAppointmentsViewModel.AppointmentStatus.ALL);
            else if (checkedId == R.id.chipPending) viewModel.setFilter(DoctorAppointmentsViewModel.AppointmentStatus.PENDING);
            else if (checkedId == R.id.chipApproved) viewModel.setFilter(DoctorAppointmentsViewModel.AppointmentStatus.APPROVED);
            else if (checkedId == R.id.chipCompleted) viewModel.setFilter(DoctorAppointmentsViewModel.AppointmentStatus.COMPLETED);
        });

        View btnBack = root.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                requireActivity().onBackPressed();
            });
        }
    }

    private void observeViewModel() {
        viewModel.getAppointments().observe(getViewLifecycleOwner(), appointments -> {
            AppointmentCardAdapter adapter = new AppointmentCardAdapter(appointments, v -> {
                androidx.navigation.Navigation.findNavController(v).navigate(R.id.action_appointments_to_consultation);
            });
            rvAppointments.setAdapter(adapter);
        });
    }
}
