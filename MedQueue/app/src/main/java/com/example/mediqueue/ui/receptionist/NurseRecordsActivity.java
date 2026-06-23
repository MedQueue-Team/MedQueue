package com.example.mediqueue.ui.receptionist;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mediqueue.R;
import com.example.mediqueue.databinding.ActivityNurseRecordsBinding;

public class NurseRecordsActivity extends AppCompatActivity {

    private ActivityNurseRecordsBinding binding;
    private NurseRecordsViewModel viewModel;
    private NurseRecordsAdapter adapter;
    private NurseRecord.RecordType activeTab = NurseRecord.RecordType.ALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNurseRecordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(NurseRecordsViewModel.class);

        setupToolbar();
        setupSearch();
        setupTabFilters();
        setupRecyclerView();
        observeViewModel();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.toolbar.setNavigationContentDescription("Return to dashboard");

        binding.ivNotifications.setOnClickListener(v ->
            startActivity(new Intent(this, NurseNotificationsActivity.class)));
        binding.ivNotifications.setContentDescription("Open notifications");
    }

    private void setupRecyclerView() {
        binding.rvRecords.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRecords.setNestedScrollingEnabled(false);
    }

    private void setupSearch() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.applySearchAndFilter(s.toString());
            }
        });

        binding.ivFilter.setOnClickListener(v -> {
            // Cycle through filters as a shortcut; real app would use bottom sheet
            switch (activeTab) {
                case ALL:          applyTab(NurseRecord.RecordType.TRIAGE);        break;
                case TRIAGE:       applyTab(NurseRecord.RecordType.CONSULTATION);  break;
                case CONSULTATION: applyTab(NurseRecord.RecordType.INPATIENT);     break;
                default:           applyTab(NurseRecord.RecordType.ALL);           break;
            }
        });
        binding.ivFilter.setContentDescription("Filter records");
    }

    private void setupTabFilters() {
        binding.tabAll.setOnClickListener(v -> applyTab(NurseRecord.RecordType.ALL));
        binding.tabTriage.setOnClickListener(v -> applyTab(NurseRecord.RecordType.TRIAGE));
        binding.tabConsultations.setOnClickListener(v -> applyTab(NurseRecord.RecordType.CONSULTATION));
        binding.tabInpatients.setOnClickListener(v -> applyTab(NurseRecord.RecordType.INPATIENT));

        // Accessibility
        binding.tabAll.setContentDescription("Show all records");
        binding.tabTriage.setContentDescription("Show triage records");
        binding.tabConsultations.setContentDescription("Show consultation records");
        binding.tabInpatients.setContentDescription("Show inpatient records");

        updateTabStyles(NurseRecord.RecordType.ALL);
    }

    private void applyTab(NurseRecord.RecordType type) {
        activeTab = type;
        viewModel.applyFilter(type);
        updateTabStyles(type);
    }

    private void updateTabStyles(NurseRecord.RecordType active) {
        // Reset all tabs
        int defaultText = getColor(R.color.on_surface_variant);
        int activeText  = getColor(R.color.primary);

        binding.tabAll.setTextColor(active == NurseRecord.RecordType.ALL ? activeText : defaultText);
        binding.tabAll.setSelected(active == NurseRecord.RecordType.ALL);

        binding.tabTriage.setTextColor(active == NurseRecord.RecordType.TRIAGE ? activeText : defaultText);
        binding.tabTriage.setSelected(active == NurseRecord.RecordType.TRIAGE);

        binding.tabConsultations.setTextColor(active == NurseRecord.RecordType.CONSULTATION ? activeText : defaultText);
        binding.tabConsultations.setSelected(active == NurseRecord.RecordType.CONSULTATION);

        binding.tabInpatients.setTextColor(active == NurseRecord.RecordType.INPATIENT ? activeText : defaultText);
        binding.tabInpatients.setSelected(active == NurseRecord.RecordType.INPATIENT);
    }

    private void observeViewModel() {
        viewModel.getFilteredRecords().observe(this, records -> {
            if (records == null || records.isEmpty()) {
                binding.rvRecords.setVisibility(View.GONE);
                binding.layoutEmptyState.setVisibility(View.VISIBLE);
            } else {
                binding.layoutEmptyState.setVisibility(View.GONE);
                binding.rvRecords.setVisibility(View.VISIBLE);
                adapter = new NurseRecordsAdapter(records);
                binding.rvRecords.setAdapter(adapter);
                binding.tvRecordCount.setText(records.size() + " records found");
            }
        });
    }
}
