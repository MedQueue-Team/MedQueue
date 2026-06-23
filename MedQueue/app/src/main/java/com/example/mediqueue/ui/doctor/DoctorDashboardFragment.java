package com.example.mediqueue.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;

public class DoctorDashboardFragment extends Fragment {

    private DoctorDashboardViewModel viewModel;
    private TextView tvGreeting, tvSpecialty, tvDepartment, tvDate, tvStatPatientsValue, tvStatPatientsIncrease, tvStatQueueValue, tvStatApptsValue, tvStatCompletedValue, tvPatientName, tvPatientDetail, tvWaitingTime;
    private Button btnCallPatient;
    private RecyclerView rvAppointments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_doctor_dashboard, container, false);

        initViews(root);
        viewModel = new ViewModelProvider(this).get(DoctorDashboardViewModel.class);

        observeViewModel();
        setupListeners(root);

        return root;
    }

    private void initViews(View root) {
        tvGreeting = root.findViewById(R.id.tvGreeting);
        tvSpecialty = root.findViewById(R.id.tvSpecialty);
        tvDepartment = root.findViewById(R.id.tvDepartment);
        tvDate = root.findViewById(R.id.tvDate);
        tvStatPatientsValue = root.findViewById(R.id.tvStatPatientsValue);
        tvStatPatientsIncrease = root.findViewById(R.id.tvStatPatientsIncrease);
        tvStatQueueValue = root.findViewById(R.id.tvStatQueueValue);
        tvStatApptsValue = root.findViewById(R.id.tvStatApptsValue);
        tvStatCompletedValue = root.findViewById(R.id.tvStatCompletedValue);
        tvPatientName = root.findViewById(R.id.tvPatientName);
        tvPatientDetail = root.findViewById(R.id.tvPatientDetail);
        tvWaitingTime = root.findViewById(R.id.tvWaitingTime);
        btnCallPatient = root.findViewById(R.id.btnCallPatient);
        rvAppointments = root.findViewById(R.id.rvAppointments);
        rvAppointments.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupListeners(View root) {
        View btnAppts = root.findViewById(R.id.btnQuickAppts);
        if (btnAppts != null) {
            btnAppts.setOnClickListener(v -> {
                androidx.navigation.Navigation.findNavController(v).navigate(R.id.action_home_to_appointments);
            });
        }

        View btnViewQueue = root.findViewById(R.id.btnQuickViewQueue);
        if (btnViewQueue != null) {
            btnViewQueue.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(getContext(), DoctorPatientsListActivity.class);
                startActivity(intent);
            });
        }

        View btnNotifications = root.findViewById(R.id.btnNotifications);
        if (btnNotifications != null) {
            btnNotifications.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(getContext(), DoctorNotificationsActivity.class);
                startActivity(intent);
            });
        }

        View btnQuickRecords = root.findViewById(R.id.btnQuickRecords);
        if (btnQuickRecords != null) {
            btnQuickRecords.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(getContext(), DoctorOperationsActivity.class);
                startActivity(intent);
            });
        }

        if (btnCallPatient != null) {
            btnCallPatient.setOnClickListener(v -> {
                androidx.navigation.Navigation.findNavController(v).navigate(R.id.nav_doctor_call_patient);
            });
        }
    }


    private void observeViewModel() {
        viewModel.getStats().observe(getViewLifecycleOwner(), stats -> {
            tvStatPatientsValue.setText(String.valueOf(stats.todayPatients));
            tvStatPatientsIncrease.setText(stats.patientsIncrease);
            tvStatQueueValue.setText(String.valueOf(stats.waitingQueue));
            tvStatApptsValue.setText(String.valueOf(stats.appointments));
            tvStatCompletedValue.setText(String.valueOf(stats.completed));
        });

        viewModel.getLivePatient().observe(getViewLifecycleOwner(), patient -> {
            tvPatientName.setText(patient.id + " " + patient.name);
            tvPatientDetail.setText(patient.department + " • " + patient.priority);
            tvWaitingTime.setText("Waiting Time\n" + patient.waitingTime);
        });

        viewModel.getAppointments().observe(getViewLifecycleOwner(), appointments -> {
            AppointmentAdapter adapter = new AppointmentAdapter(appointments);
            rvAppointments.setAdapter(adapter);
        });
    }
}
