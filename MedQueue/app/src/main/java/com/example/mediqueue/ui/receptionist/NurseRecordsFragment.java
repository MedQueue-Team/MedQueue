package com.example.mediqueue.ui.receptionist;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mediqueue.R;
import com.example.mediqueue.databinding.FragmentNurseRecordsBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NurseRecordsFragment extends Fragment {

    private FragmentNurseRecordsBinding binding;
    private NurseRecordsViewModel viewModel;
    private NurseRecordsAdapter adapter;
    private NurseRecord.RecordType activeTab = NurseRecord.RecordType.ALL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNurseRecordsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(NurseRecordsViewModel.class);

        setupToolbar();
        setupSearch();
        setupTabFilters();
        setupRecyclerView();
        observeViewModel();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationIcon(null);

        binding.ivNotifications.setOnClickListener(v ->
            startActivity(new Intent(requireContext(), NurseNotificationsActivity.class)));
        binding.ivNotifications.setContentDescription("Open notifications");
    }

    private void setupRecyclerView() {
        binding.rvRecords.setLayoutManager(new LinearLayoutManager(requireContext()));
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
        int defaultText = requireContext().getColor(R.color.on_surface_variant);
        int activeText  = requireContext().getColor(R.color.primary);

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
        viewModel.getFilteredRecords().observe(getViewLifecycleOwner(), records -> {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
