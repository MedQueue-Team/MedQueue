package com.example.mediqueue.ui.receptionist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.R;
import com.example.mediqueue.databinding.ItemTriagePatientBinding;

import java.util.List;

public class TriageAdapter extends RecyclerView.Adapter<TriageAdapter.ViewHolder> {

    private final List<TriagePatient> patients;

    public TriageAdapter(List<TriagePatient> patients) {
        this.patients = patients;
    }

    public void updateData(List<TriagePatient> newPatients) {
        this.patients.clear();
        this.patients.addAll(newPatients);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTriagePatientBinding binding = ItemTriagePatientBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TriagePatient patient = patients.get(position);
        ItemTriagePatientBinding binding = holder.binding;
        Context context = binding.getRoot().getContext();

        binding.tvPatientName.setText(patient.getName());
        binding.tvPatientMeta.setText(patient.getId() + " • " + patient.getAge() + " • " + patient.getGender());
        binding.tvPriority.setText(patient.getPriority());
        binding.tvWaitTimeValue.setText(patient.getWaitTimeText());

        // Priority Styling (Premium UI Polish)
        applyPriorityStyling(binding, patient, context);

        // Visibility and Action Logic
        boolean isInProgress = "In Progress".equals(patient.getWaitTimeText());

        if (patient.isAssessed()) {
            binding.btnAction.setVisibility(View.GONE);
            binding.tvStatus.setVisibility(View.VISIBLE);
            binding.tvStatus.setText("✅ Assessed");
            binding.tvWaitTimeValue.setVisibility(View.GONE);
            binding.btnExport.setVisibility(View.VISIBLE);
        } else if (isInProgress) {
            binding.btnAction.setVisibility(View.GONE);
            binding.tvStatus.setVisibility(View.VISIBLE);
            binding.tvStatus.setText("🔄 In Progress");
            binding.tvWaitTimeValue.setVisibility(View.GONE);
            binding.btnExport.setVisibility(View.GONE);
        } else {
            binding.btnAction.setVisibility(View.VISIBLE);
            binding.tvStatus.setVisibility(View.GONE);
            binding.tvWaitTimeValue.setVisibility(View.VISIBLE);
            binding.btnExport.setVisibility(View.GONE);
        }

        binding.btnAction.setOnClickListener(v -> {
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putString("PATIENT_ID", patient.getId());
                androidx.navigation.Navigation.findNavController(v)
                    .navigate(R.id.triageAssessmentFragment, bundle);
            } catch (Exception e) {
                Intent intent = new Intent(context, TriageAssessmentActivity.class);
                intent.putExtra("PATIENT_ID", patient.getId());
                intent.putExtra("PATIENT_NAME", patient.getName());
                context.startActivity(intent);
            }
        });

        binding.btnExport.setOnClickListener(v -> {
            com.example.mediqueue.ui.base.PdfExportHelper.exportTriageAssessmentToPdf(context, patient);
        });

        // Accessibility Support
        binding.getRoot().setContentDescription("Patient: " + patient.getName() + ", Priority: " + patient.getPriority());
        binding.btnAction.setContentDescription("Start triage assessment for " + patient.getName());
        binding.btnExport.setContentDescription("Export triage report for " + patient.getName());
    }

    private void applyPriorityStyling(ItemTriagePatientBinding binding, TriagePatient patient, Context context) {
        int priorityColor;
        int priorityBg;
        int iconBg;
        int iconRes;
        
        String priority = patient.getPriority();
        if (priority == null) {
            priority = "LOW"; // Default to LOW if priority is null
        }

        switch (priority.toUpperCase()) {
            case "EMERGENCY":
                priorityColor = R.color.on_error;
                priorityBg = R.color.error;
                iconBg = R.color.error_container;
                iconRes = R.drawable.ic_emergency;
                binding.tvWaitTimeValue.setTextColor(ContextCompat.getColor(context, R.color.error));
                break;
            case "HIGH":
                priorityColor = R.color.on_tertiary_container;
                priorityBg = R.color.tertiary_container;
                iconBg = R.color.tertiary_fixed;
                iconRes = R.drawable.ic_priority_high;
                binding.tvWaitTimeValue.setTextColor(ContextCompat.getColor(context, R.color.tertiary));
                break;
            case "MEDIUM":
                priorityColor = R.color.on_primary;
                priorityBg = R.color.primary;
                iconBg = R.color.primary_fixed;
                iconRes = R.drawable.ic_medical_information;
                binding.tvWaitTimeValue.setTextColor(ContextCompat.getColor(context, R.color.primary));
                break;
            default: // LOW
                priorityColor = R.color.on_secondary;
                priorityBg = R.color.secondary;
                iconBg = R.color.secondary_fixed;
                iconRes = R.drawable.ic_low_priority;
                binding.tvWaitTimeValue.setTextColor(ContextCompat.getColor(context, R.color.secondary));
                break;
        }
        
        binding.cvPriority.setCardBackgroundColor(ContextCompat.getColor(context, priorityBg));
        binding.tvPriority.setTextColor(ContextCompat.getColor(context, priorityColor));
        binding.cvTriageIcon.setCardBackgroundColor(ContextCompat.getColor(context, iconBg));
        binding.ivTriageIcon.setImageResource(iconRes);
        binding.ivTriageIcon.setColorFilter(ContextCompat.getColor(context, priorityBg));
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemTriagePatientBinding binding;

        public ViewHolder(@NonNull ItemTriagePatientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}