package com.example.mediqueue.ui.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.mediqueue.R;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookAppointmentFragment extends Fragment {

    private BookAppointmentViewModel viewModel;

    private Spinner spinnerDepartment;
    private EditText etDoctorSearch;
    private LinearLayout btnDateMon, btnDateTue, btnDateWed, btnDateMore;
    private Button chipTime1, chipTime2, chipTime3, chipTime4, chipTime5, chipTime6;
    private EditText etReason;
    private Button btnPriorityNormal, btnPriorityUrgent;

    private TextView tvSumDoctor, tvSumDept, tvSumDate, tvSumTime;
    private Button btnSubmitBooking;

    private ConstraintLayout layoutSuccessModal;
    private Button btnSuccessDone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_book_appointment, container, false);

        viewModel = new ViewModelProvider(this).get(BookAppointmentViewModel.class);

        // Bind Views
        spinnerDepartment = view.findViewById(R.id.spinnerDepartment);
        etDoctorSearch = view.findViewById(R.id.etDoctorSearch);
        
        btnDateMon = view.findViewById(R.id.btnDateMon);
        btnDateTue = view.findViewById(R.id.btnDateTue);
        btnDateWed = view.findViewById(R.id.btnDateWed);
        btnDateMore = view.findViewById(R.id.btnDateMore);

        chipTime1 = view.findViewById(R.id.chipTime1);
        chipTime2 = view.findViewById(R.id.chipTime2);
        chipTime3 = view.findViewById(R.id.chipTime3);
        chipTime4 = view.findViewById(R.id.chipTime4);
        chipTime5 = view.findViewById(R.id.chipTime5);
        chipTime6 = view.findViewById(R.id.chipTime6);

        etReason = view.findViewById(R.id.etReason);
        btnPriorityNormal = view.findViewById(R.id.btnPriorityNormal);
        btnPriorityUrgent = view.findViewById(R.id.btnPriorityUrgent);

        tvSumDoctor = view.findViewById(R.id.tvSumDoctor);
        tvSumDept = view.findViewById(R.id.tvSumDept);
        tvSumDate = view.findViewById(R.id.tvSumDate);
        tvSumTime = view.findViewById(R.id.tvSumTime);
        btnSubmitBooking = view.findViewById(R.id.btnSubmitBooking);

        layoutSuccessModal = view.findViewById(R.id.layoutSuccessModal);
        btnSuccessDone = view.findViewById(R.id.btnSuccessDone);

        setupDepartmentSpinner();
        setupDatePickers();
        setupTimeChips();
        setupPriorityButtons();
        setupObservers();

        // Submit Booking action
        btnSubmitBooking.setOnClickListener(v -> {
            String reason = etReason.getText().toString().trim();
            if (reason.isEmpty()) {
                reason = "Routine general health checkup consultation.";
            }
            viewModel.bookAppointment(reason);
        });

        // Done button in success modal
        btnSuccessDone.setOnClickListener(v -> {
            layoutSuccessModal.setVisibility(View.GONE);
            Navigation.findNavController(v).navigateUp();
        });

        // Back button
        view.findViewById(R.id.btnBack).setOnClickListener(v -> 
                Navigation.findNavController(v).navigateUp());

        return view;
    }

    private void setupDepartmentSpinner() {
        String[] departments = {"General OPD", "Dental", "Pediatrics", "Surgery", "Emergency"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, departments);
        spinnerDepartment.setAdapter(adapter);

        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setDepartment(departments[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupDatePickers() {
        btnDateMon.setOnClickListener(v -> {
            viewModel.setDate("Oct 18, 2023");
            updateDateUI(btnDateMon, btnDateTue, btnDateWed);
        });
        btnDateTue.setOnClickListener(v -> {
            viewModel.setDate("Oct 19, 2023");
            updateDateUI(btnDateTue, btnDateMon, btnDateWed);
        });
        btnDateWed.setOnClickListener(v -> {
            viewModel.setDate("Oct 20, 2023");
            updateDateUI(btnDateWed, btnDateMon, btnDateTue);
        });
        btnDateMore.setOnClickListener(v -> {
            viewModel.setDate("Oct 21, 2023");
            updateDateUI(btnDateMore, btnDateMon, btnDateTue, btnDateWed);
        });
    }

    private void updateDateUI(View selected, View... unselected) {
        selected.setBackgroundResource(R.drawable.date_active_bg);
        // Highlight child text views
        if (selected instanceof LinearLayout) {
            LinearLayout layout = (LinearLayout) selected;
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                if (child instanceof TextView) {
                    ((TextView) child).setTextColor(getResources().getColor(R.color.on_primary_fixed));
                }
            }
        }

        for (View u : unselected) {
            u.setBackgroundResource(R.drawable.date_inactive_bg);
            if (u instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) u;
                for (int i = 0; i < layout.getChildCount(); i++) {
                    View child = layout.getChildAt(i);
                    if (child instanceof TextView) {
                        ((TextView) child).setTextColor(getResources().getColor(R.color.primary));
                    }
                }
            }
        }
    }

    private void setupTimeChips() {
        Button[] chips = {chipTime1, chipTime2, chipTime3, chipTime4, chipTime5, chipTime6};
        for (Button chip : chips) {
            chip.setOnClickListener(v -> {
                viewModel.setTime(chip.getText().toString());
                updateTimeChipsUI(chip, chips);
            });
        }
    }

    private void updateTimeChipsUI(Button selected, Button[] all) {
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

    private void setupPriorityButtons() {
        btnPriorityNormal.setOnClickListener(v -> {
            viewModel.setPriority("NORMAL");
            btnPriorityNormal.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.primary_fixed)));
            btnPriorityNormal.setTextColor(getResources().getColor(R.color.on_primary_fixed));
            btnPriorityUrgent.setBackgroundTintList(null);
            btnPriorityUrgent.setTextColor(getResources().getColor(R.color.error));
        });

        btnPriorityUrgent.setOnClickListener(v -> {
            viewModel.setPriority("URGENT");
            btnPriorityUrgent.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.error_container)));
            btnPriorityUrgent.setTextColor(getResources().getColor(R.color.error));
            btnPriorityNormal.setBackgroundTintList(null);
            btnPriorityNormal.setTextColor(getResources().getColor(R.color.primary));
        });
    }

    private void setupObservers() {
        viewModel.getSelectedDoctor().observe(getViewLifecycleOwner(), doc -> tvSumDoctor.setText(doc));
        viewModel.getSelectedDepartment().observe(getViewLifecycleOwner(), dept -> tvSumDept.setText(dept));
        viewModel.getSelectedDate().observe(getViewLifecycleOwner(), date -> tvSumDate.setText(date));
        viewModel.getSelectedTime().observe(getViewLifecycleOwner(), time -> tvSumTime.setText(time));

        viewModel.getBookingSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                layoutSuccessModal.setVisibility(View.VISIBLE);
            }
        });
    }
}
