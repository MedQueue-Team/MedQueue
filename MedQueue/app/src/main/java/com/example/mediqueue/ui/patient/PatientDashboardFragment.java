package com.example.mediqueue.ui.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entities.AppointmentEntity;
import com.example.mediqueue.data.local.entities.MedicalHistory;
import com.example.mediqueue.data.local.entities.PatientEntity;
import com.example.mediqueue.data.local.entities.QueueEntity;
import com.google.android.material.imageview.ShapeableImageView;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.List;

@AndroidEntryPoint
public class PatientDashboardFragment extends Fragment {

    private PatientDashboardViewModel viewModel;

    private TextView tvGreeting;
    private ShapeableImageView ivProfileAvatar;
    private CardView cardLiveQueue;
    private TextView tvQueueDept;
    private TextView tvQueueNumber;
    private ProgressBar pbQueueProgress;
    private TextView tvQueuePosition;
    private TextView tvQueueWait;

    private LinearLayout llAppointmentsContainer;
    private CardView cardRecentRecord;
    private TextView tvRecordDate;
    private TextView tvRecordDoctor;
    private TextView tvRecordDiagnosis;
    private TextView tvRecordTreatment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_dashboard, container, false);

        viewModel = new ViewModelProvider(this).get(PatientDashboardViewModel.class);

        // Bind Greeting views
        tvGreeting = view.findViewById(R.id.tvGreeting);
        ivProfileAvatar = view.findViewById(R.id.ivProfileAvatar);

        // Bind Live Queue views
        cardLiveQueue = view.findViewById(R.id.cardLiveQueue);
        tvQueueDept = view.findViewById(R.id.tvQueueDept);
        tvQueueNumber = view.findViewById(R.id.tvQueueNumber);
        pbQueueProgress = view.findViewById(R.id.pbQueueProgress);
        tvQueuePosition = view.findViewById(R.id.tvQueuePosition);
        tvQueueWait = view.findViewById(R.id.tvQueueWait);

        // Bind Lists & Sub-cards
        llAppointmentsContainer = view.findViewById(R.id.llAppointmentsContainer);
        cardRecentRecord = view.findViewById(R.id.cardRecentRecord);
        tvRecordDate = view.findViewById(R.id.tvRecordDate);
        tvRecordDoctor = view.findViewById(R.id.tvRecordDoctor);
        tvRecordDiagnosis = view.findViewById(R.id.tvRecordDiagnosis);
        tvRecordTreatment = view.findViewById(R.id.tvRecordTreatment);

        setupObservers();
        setupClickListeners(view);

        return view;
    }

    private void setupObservers() {
        // Observe Patient Profile
        viewModel.getPatient().observe(getViewLifecycleOwner(), patient -> {
            if (patient != null) {
                tvGreeting.setText("Good Morning, " + (patient.fullName != null ? patient.fullName.split(" ")[0] : "Amara"));
            } else {
                tvGreeting.setText("Good Morning, Amara");
            }
            // Use Amara default avatar
            ivProfileAvatar.setImageResource(android.R.drawable.ic_menu_gallery);
        });

        // Observe Active Queue Status
        viewModel.getActiveQueue().observe(getViewLifecycleOwner(), queue -> {
            if (queue != null && "WAITING".equals(queue.status)) {
                cardLiveQueue.setVisibility(View.VISIBLE);
                tvQueueDept.setText(queue.department + " Dept");
                tvQueueNumber.setText("Ticket #" + queue.queueId);
                tvQueuePosition.setText(queue.position + " patients ahead of you");
                tvQueueWait.setText("Est: " + queue.estimatedWaitTimeMins + " mins");
                
                // Set wait progress (inverse position progress)
                int progress = Math.max(10, 100 - (queue.position * 15));
                pbQueueProgress.setProgress(progress);
            } else {
                cardLiveQueue.setVisibility(View.GONE);
            }
        });

        // Observe Upcoming Appointments
        viewModel.getUpcomingAppointments().observe(getViewLifecycleOwner(), appointments -> {
            llAppointmentsContainer.removeAllViews();
            if (appointments != null && !appointments.isEmpty()) {
                int count = Math.min(2, appointments.size());
                for (int i = 0; i < count; i++) {
                    AppointmentEntity appt = appointments.get(i);
                    addAppointmentView(appt);
                }
            } else {
                // Show empty text helper
                TextView emptyTv = new TextView(getContext());
                emptyTv.setText("No upcoming visits scheduled.");
                emptyTv.setTextColor(getResources().getColor(R.color.outline));
                emptyTv.setPadding(0, 16, 0, 16);
                llAppointmentsContainer.addView(emptyTv);
            }
        });

        // Observe Medical Records
        viewModel.getMedicalHistory().observe(getViewLifecycleOwner(), historyList -> {
            if (historyList != null && !historyList.isEmpty()) {
                cardRecentRecord.setVisibility(View.VISIBLE);
                // Get the most recent completed record
                MedicalHistory record = historyList.get(0);
                tvRecordDate.setText(record.getDate());
                tvRecordDoctor.setText(record.getDoctorName());
                tvRecordDiagnosis.setText(record.getDiagnosis());
                tvRecordTreatment.setText("Treatment: Prescribed medicines provided in summary.");
            } else {
                cardRecentRecord.setVisibility(View.GONE);
            }
        });
    }

    private void addAppointmentView(AppointmentEntity appt) {
        View apptView = LayoutInflater.from(getContext()).inflate(R.layout.item_dashboard_appointment, llAppointmentsContainer, false);
        
        TextView tvDoctorName = apptView.findViewById(R.id.tvDoctorName);
        TextView tvDepartment = apptView.findViewById(R.id.tvDepartment);
        TextView tvDateTime = apptView.findViewById(R.id.tvDateTime);
        TextView tvStatusTag = apptView.findViewById(R.id.tvStatusTag);
        ShapeableImageView ivDoctorAvatar = apptView.findViewById(R.id.ivDoctorAvatar);

        tvDoctorName.setText(appt.doctorName);
        tvDepartment.setText(appt.department);
        tvDateTime.setText(appt.appointmentDate + " • " + appt.appointmentTime);
        
        tvStatusTag.setText(appt.status);
        if ("APPROVED".equalsIgnoreCase(appt.status)) {
            tvStatusTag.setBackgroundResource(R.drawable.bg_tag_approved);
            tvStatusTag.setTextColor(getResources().getColor(R.color.on_secondary_container));
        } else if ("PENDING".equalsIgnoreCase(appt.status)) {
            tvStatusTag.setBackgroundResource(R.drawable.bg_tag_pending);
            tvStatusTag.setTextColor(getResources().getColor(R.color.on_tertiary_container));
        } else {
            tvStatusTag.setBackgroundResource(R.drawable.bg_tag_rejected);
            tvStatusTag.setTextColor(getResources().getColor(R.color.on_error_container));
        }

        ivDoctorAvatar.setImageResource(android.R.drawable.ic_menu_gallery);

        llAppointmentsContainer.addView(apptView);
    }

    private void setupClickListeners(View rootView) {
        // Navigation actions
        rootView.findViewById(R.id.btnQuickBook).setOnClickListener(v -> 
                Navigation.findNavController(v).navigate(R.id.action_home_to_book));
        
        rootView.findViewById(R.id.fabBookAppointment).setOnClickListener(v -> 
                Navigation.findNavController(v).navigate(R.id.action_home_to_book));

        rootView.findViewById(R.id.btnQuickRecords).setOnClickListener(v -> 
                Navigation.findNavController(v).navigate(R.id.action_home_to_records));
        
        rootView.findViewById(R.id.btnViewFullHistory).setOnClickListener(v -> 
                Navigation.findNavController(v).navigate(R.id.action_home_to_records));

        rootView.findViewById(R.id.btnQuickQueue).setOnClickListener(v -> 
                Navigation.findNavController(v).navigate(R.id.action_home_to_queue_reg));

        rootView.findViewById(R.id.btnViewAllAppts).setOnClickListener(v -> 
                Navigation.findNavController(v).navigate(R.id.nav_appts));

        ivProfileAvatar.setOnClickListener(v -> 
                Navigation.findNavController(v).navigate(R.id.action_home_to_profile));
    }
}
