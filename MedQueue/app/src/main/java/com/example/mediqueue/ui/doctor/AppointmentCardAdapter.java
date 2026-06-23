package com.example.mediqueue.ui.doctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import java.util.List;

public class AppointmentCardAdapter extends RecyclerView.Adapter<AppointmentCardAdapter.AppointmentCardViewHolder> {

    private final List<DoctorAppointmentsViewModel.AppointmentDetail> appointments;

    public AppointmentCardAdapter(List<DoctorAppointmentsViewModel.AppointmentDetail> appointments, View.OnClickListener startConsultationListener) {
        this.appointments = appointments;
        this.startConsultationListener = startConsultationListener;
    }

    private View.OnClickListener startConsultationListener;

    @Override
    public void onBindViewHolder(@NonNull AppointmentCardViewHolder holder, int position) {
        DoctorAppointmentsViewModel.AppointmentDetail appt = appointments.get(position);
        
        holder.tvName.setText(appt.name);
        holder.tvDept.setText(appt.department);
        holder.tvTime.setText(appt.time);
        holder.tvInitials.setText(appt.initials);
        holder.tvReason.setText("Reason: " + appt.reason);
        holder.tvPrevDiag.setText(appt.previousDiagnosis != null ? "Previous diagnosis: " + appt.previousDiagnosis : "");
        
        holder.tvStatus.setText(appt.status.name());
        
        // Handle state-specific visibility and styling
        holder.viewBorder.setVisibility(View.VISIBLE);
        holder.llActions.setVisibility(View.GONE);
        holder.btnViewRecord.setVisibility(View.GONE);
        holder.cvPendingBanner.setVisibility(View.GONE);
        holder.cvReason.setVisibility(View.VISIBLE);

        switch (appt.status) {
            case APPROVED:
                holder.viewBorder.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.triage_low)); // Green
                holder.tvStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary_fixed));
                holder.llActions.setVisibility(View.VISIBLE);
                holder.btnStart.setOnClickListener(v -> startConsultationListener.onClick(v));
                break;
            case PENDING:
                holder.viewBorder.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.triage_medium)); // Yellow/Orange
                holder.tvStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.secondary_container));
                holder.cvPendingBanner.setVisibility(View.VISIBLE);
                break;
            case COMPLETED:
                holder.viewBorder.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.outline_variant)); // Grey
                holder.tvStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.surface_container));
                holder.btnViewRecord.setVisibility(View.VISIBLE);
                break;
        }
    }


    @NonNull
    @Override
    public AppointmentCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_appointment_card, parent, false);
        return new AppointmentCardViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return appointments.size();
    }

    static class AppointmentCardViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDept, tvTime, tvInitials, tvReason, tvPrevDiag, tvStatus;
        View viewBorder;
        View llActions, btnViewRecord, cvPendingBanner, cvReason;
        Button btnStart, btnHistory;

        AppointmentCardViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPatientName);
            tvDept = itemView.findViewById(R.id.tvDepartment);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvInitials = itemView.findViewById(R.id.tvInitials);
            tvReason = itemView.findViewById(R.id.tvReason);
            tvPrevDiag = itemView.findViewById(R.id.tvPreviousDiagnosis);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            viewBorder = itemView.findViewById(R.id.viewBorder);
            llActions = itemView.findViewById(R.id.llActions);
            btnViewRecord = itemView.findViewById(R.id.btnViewMedicalRecord);
            cvPendingBanner = itemView.findViewById(R.id.cvPendingBanner);
            cvReason = itemView.findViewById(R.id.cvReason);
            btnStart = itemView.findViewById(R.id.btnStartConsultation);
            btnHistory = itemView.findViewById(R.id.btnViewHistory);
        }
    }
}
