package com.example.mediqueue.ui.admin;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mediqueue.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;

public class AdminReportsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ChipGroup chipGroupDateRange;
    private TextView tvTotalPatients, tvAvgWaitTime, tvSatisfaction, tvStaffUtil;
    private MaterialButton btnExport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reports);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        chipGroupDateRange = findViewById(R.id.chipGroupDateRange);
        tvTotalPatients = findViewById(R.id.tvTotalPatients);
        tvAvgWaitTime = findViewById(R.id.tvAvgWaitTime);
        tvSatisfaction = findViewById(R.id.tvSatisfaction);
        tvStaffUtil = findViewById(R.id.tvStaffUtil);
        btnExport = findViewById(R.id.btnExport);

        chipGroupDateRange.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                int checkedId = checkedIds.get(0);
                if (checkedId == R.id.chipThisWeek) {
                    loadData("This Week");
                } else if (checkedId == R.id.chipThisMonth) {
                    loadData("This Month");
                } else if (checkedId == R.id.chipCustom) {
                    loadData("Custom");
                }
            }
        });

        btnExport.setOnClickListener(v -> {
            Toast.makeText(this, "Exporting report...", Toast.LENGTH_SHORT).show();
            // Implement export logic
        });
    }

    private void loadData(String range) {
        // Simulate data loading based on date range
        if ("This Week".equals(range)) {
            tvTotalPatients.setText("1,247");
            tvAvgWaitTime.setText("18 min");
            tvSatisfaction.setText("92%");
            tvStaffUtil.setText("87%");
        } else if ("This Month".equals(range)) {
            tvTotalPatients.setText("5,432");
            tvAvgWaitTime.setText("22 min");
            tvSatisfaction.setText("88%");
            tvStaffUtil.setText("85%");
        }
    }
}
