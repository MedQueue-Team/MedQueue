package com.example.mediqueue.ui.receptionist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.R;
import com.example.mediqueue.databinding.ItemNurseRecordBinding;

import java.util.List;

public class NurseRecordsAdapter extends RecyclerView.Adapter<NurseRecordsAdapter.ViewHolder> {

    private final List<NurseRecord> records;

    public NurseRecordsAdapter(List<NurseRecord> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNurseRecordBinding binding = ItemNurseRecordBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NurseRecord record = records.get(position);
        ItemNurseRecordBinding b = holder.binding;
        Context ctx = b.getRoot().getContext();

        b.tvPatientId.setText(record.getPatientId());
        b.tvPatientName.setText(record.getPatientName());
        b.tvAgeGender.setText(record.getAge() + " yrs • " + record.getGender());
        b.tvDoctor.setText(record.getAssignedDoctor());
        b.tvLastVisit.setText(record.getLastVisitDate());

        // Record type badge
        String typeBadge;
        int typeBadgeBg;
        switch (record.getRecordType()) {
            case INPATIENT:
                typeBadge   = "Inpatient";
                typeBadgeBg = R.color.primary_fixed;
                break;
            case TRIAGE:
                typeBadge   = "Triage";
                typeBadgeBg = R.color.error_container;
                break;
            case CONSULTATION:
                typeBadge   = "Consultation";
                typeBadgeBg = R.color.secondary_container;
                break;
            default:
                typeBadge   = "General";
                typeBadgeBg = R.color.surface_container;
                break;
        }
        b.tvTypeBadge.setText(typeBadge);
        b.tvTypeBadge.setBackgroundTintList(
                ContextCompat.getColorStateList(ctx, typeBadgeBg));

        // Priority badge + colour
        applyPriorityStyling(b, record, ctx);

        // Ward row (only for inpatients)
        if (record.getWard() != null) {
            b.llWard.setVisibility(View.VISIBLE);
            b.tvWard.setText(record.getWard());
        } else {
            b.llWard.setVisibility(View.GONE);
        }

        // Action buttons
        b.btnViewRecord.setOnClickListener(v -> {
            Toast.makeText(ctx, "Viewing patient: " + record.getPatientName(), Toast.LENGTH_SHORT).show();
        });
        b.btnEditRecord.setOnClickListener(v ->
            Toast.makeText(ctx, "Editing record for " + record.getPatientName(),
                    Toast.LENGTH_SHORT).show());

        // Accessibility
        b.getRoot().setContentDescription("Patient record: " + record.getPatientName()
                + ", " + typeBadge + ", priority " + record.getTriagePriority());
    }

    private void applyPriorityStyling(ItemNurseRecordBinding b, NurseRecord r, Context ctx) {
        String priority = r.getTriagePriority() != null ? r.getTriagePriority() : "UNKNOWN";
        int priorityColor;
        int priorityBg;
        switch (priority) {
            case "CRITICAL":
                priorityColor = R.color.error;
                priorityBg    = R.color.error_container;
                break;
            case "HIGH":
                priorityColor = R.color.tertiary;
                priorityBg    = R.color.tertiary_fixed;
                break;
            case "MEDIUM":
                priorityColor = R.color.primary;
                priorityBg    = R.color.primary_fixed;
                break;
            default: // LOW
                priorityColor = R.color.on_surface_variant;
                priorityBg    = R.color.surface_container;
                break;
        }
        b.tvPriorityBadge.setText(priority);
        b.tvPriorityBadge.setTextColor(ContextCompat.getColor(ctx, priorityColor));
        b.tvPriorityBadge.setBackgroundTintList(
                ContextCompat.getColorStateList(ctx, priorityBg));
    }

    @Override
    public int getItemCount() { return records.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemNurseRecordBinding binding;
        ViewHolder(@NonNull ItemNurseRecordBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
