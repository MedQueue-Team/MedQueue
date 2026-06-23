package com.example.mediqueue.ui.doctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private final List<DoctorDashboardViewModel.Appointment> appointments;

    public AppointmentAdapter(List<DoctorDashboardViewModel.Appointment> appointments) {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        DoctorDashboardViewModel.Appointment appt = appointments.get(position);
        holder.tvName.setText(appt.name);
        holder.tvReason.setText("Reason: " + appt.reason);
        holder.tvTime.setText(appt.time);
        holder.tvStatus.setText(appt.status);
        holder.tvInitials.setText(appt.initials);
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvReason, tvTime, tvStatus, tvInitials;

        AppointmentViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvApptName);
            tvReason = itemView.findViewById(R.id.tvApptReason);
            tvTime = itemView.findViewById(R.id.tvApptTime);
            tvStatus = itemView.findViewById(R.id.tvApptStatus);
            tvInitials = itemView.findViewById(R.id.tvApptInitials);
        }
    }
}
