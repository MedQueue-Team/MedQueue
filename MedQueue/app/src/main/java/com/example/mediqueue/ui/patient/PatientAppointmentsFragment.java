package com.example.mediqueue.ui.patient;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entities.AppointmentEntity;
import com.google.android.material.imageview.ShapeableImageView;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.List;

@AndroidEntryPoint
public class PatientAppointmentsFragment extends Fragment {

    private PatientAppointmentsViewModel viewModel;

    private EditText etSearchAppts;
    private Button btnFilterAll, btnFilterPending, btnFilterApproved, btnFilterRejected, btnFilterCompleted;
    private LinearLayout llApptsListContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_appointments, container, false);

        viewModel = new ViewModelProvider(this).get(PatientAppointmentsViewModel.class);

        // Bind Views
        etSearchAppts = view.findViewById(R.id.etSearchAppts);
        
        btnFilterAll = view.findViewById(R.id.btnFilterAll);
        btnFilterPending = view.findViewById(R.id.btnFilterPending);
        btnFilterApproved = view.findViewById(R.id.btnFilterApproved);
        btnFilterRejected = view.findViewById(R.id.btnFilterRejected);
        btnFilterCompleted = view.findViewById(R.id.btnFilterCompleted);

        llApptsListContainer = view.findViewById(R.id.llApptsListContainer);

        setupSearchField();
        setupFilterChips();
        setupObservers();

        // FAB booking click
        view.findViewById(R.id.fabBookAppt).setOnClickListener(v -> 
                Navigation.findNavController(v).navigate(R.id.action_appts_to_book));

        // Back button click
        view.findViewById(R.id.btnBack).setOnClickListener(v -> 
                Navigation.findNavController(v).navigateUp());

        return view;
    }

    private void setupSearchField() {
        etSearchAppts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupFilterChips() {
        Button[] filters = {btnFilterAll, btnFilterPending, btnFilterApproved, btnFilterRejected, btnFilterCompleted};
        String[] states = {"ALL", "PENDING", "APPROVED", "REJECTED", "COMPLETED"};

        for (int i = 0; i < filters.length; i++) {
            final int index = i;
            filters[i].setOnClickListener(v -> {
                viewModel.setFilterState(states[index]);
                updateFilterChipsUI(filters[index], filters);
            });
        }
    }

    private void updateFilterChipsUI(Button selected, Button[] all) {
        for (Button chip : all) {
            if (chip == selected) {
                chip.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.primary)));
                chip.setTextColor(getResources().getColor(R.color.white));
            } else {
                chip.setBackgroundTintList(null);
                chip.setTextColor(getResources().getColor(R.color.primary));
            }
        }
    }

    private void setupObservers() {
        viewModel.getFilteredAppointments().observe(getViewLifecycleOwner(), this::renderAppointments);
    }

    private void renderAppointments(List<AppointmentEntity> appointments) {
        llApptsListContainer.removeAllViews();

        if (appointments == null || appointments.isEmpty()) {
            TextView emptyTv = new TextView(getContext());
            emptyTv.setText("No appointments found matching selection.");
            emptyTv.setTextColor(getResources().getColor(R.color.outline));
            emptyTv.setPadding(0, 48, 0, 48);
            emptyTv.setGravity(android.view.Gravity.CENTER);
            llApptsListContainer.addView(emptyTv);
            return;
        }

        for (AppointmentEntity appt : appointments) {
            View card = LayoutInflater.from(getContext()).inflate(R.layout.item_appointment_card, llApptsListContainer, false);

            ShapeableImageView ivDocPhoto = card.findViewById(R.id.ivDocPhoto);
            TextView tvDocName = card.findViewById(R.id.tvDocName);
            TextView tvDocSpecialty = card.findViewById(R.id.tvDocSpecialty);
            TextView tvCardStatus = card.findViewById(R.id.tvCardStatus);
            TextView tvCardDate = card.findViewById(R.id.tvCardDate);
            TextView tvCardTime = card.findViewById(R.id.tvCardTime);
            TextView tvCardReason = card.findViewById(R.id.tvCardReason);
            TextView tvWarningBanner = card.findViewById(R.id.tvWarningBanner);
            Button btnCardAction = card.findViewById(R.id.btnCardAction);

            tvDocName.setText(appt.doctorName);
            tvDocSpecialty.setText(appt.department + " Specialist");
            tvCardDate.setText(appt.appointmentDate);
            tvCardTime.setText(appt.appointmentTime);
            tvCardReason.setText("Reason: " + (appt.appointmentId != null ? "Regular clinical consultation for checkup." : "Routine visit."));
            tvCardStatus.setText(appt.status);

            ivDocPhoto.setImageResource(android.R.drawable.ic_menu_gallery);

            // Configure state specific layout adjustments
            if ("APPROVED".equalsIgnoreCase(appt.status)) {
                tvCardStatus.setBackgroundResource(R.drawable.bg_tag_approved);
                tvCardStatus.setTextColor(getResources().getColor(R.color.on_secondary_container));
                btnCardAction.setText("READY FOR VISIT");
                btnCardAction.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.primary)));
                btnCardAction.setTextColor(getResources().getColor(R.color.white));
                btnCardAction.setEnabled(true);
                btnCardAction.setOnClickListener(v -> 
                        Navigation.findNavController(v).navigate(R.id.nav_home));
            } else if ("PENDING".equalsIgnoreCase(appt.status)) {
                tvCardStatus.setBackgroundResource(R.drawable.bg_tag_pending);
                tvCardStatus.setTextColor(getResources().getColor(R.color.on_tertiary_container));
                btnCardAction.setText("WAITING FOR CLINIC APPROVAL");
                btnCardAction.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.surface_container_high)));
                btnCardAction.setTextColor(getResources().getColor(R.color.outline));
                btnCardAction.setEnabled(false);
            } else if ("COMPLETED".equalsIgnoreCase(appt.status)) {
                tvCardStatus.setBackgroundResource(R.drawable.bg_tag_completed);
                tvCardStatus.setTextColor(getResources().getColor(R.color.on_secondary_fixed));
                btnCardAction.setText("VIEW MEDICAL SUMMARY");
                btnCardAction.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.secondary_container)));
                btnCardAction.setTextColor(getResources().getColor(R.color.on_secondary_container));
                btnCardAction.setEnabled(true);
                btnCardAction.setOnClickListener(v -> 
                        Navigation.findNavController(v).navigate(R.id.nav_records));
            } else {
                // REJECTED or CANCELLED
                tvCardStatus.setBackgroundResource(R.drawable.bg_tag_rejected);
                tvCardStatus.setTextColor(getResources().getColor(R.color.on_error_container));
                tvWarningBanner.setVisibility(View.VISIBLE);
                btnCardAction.setVisibility(View.GONE);
            }

            llApptsListContainer.addView(card);
        }
    }
}
