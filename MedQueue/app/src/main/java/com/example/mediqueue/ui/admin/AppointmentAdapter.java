package com.example.mediqueue.ui.admin;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<Appointment> appointments;

    public AppointmentAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void updateData(List<Appointment> newAppointments) {
        this.appointments = newAppointments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appt = appointments.get(position);
        holder.tvPatient.setText(appt.patientName);
        holder.tvDoctor.setText(appt.doctorName);
        holder.tvDept.setText(appt.department);
        holder.tvTime.setText(appt.time);
        holder.tvStatus.setText(appt.status.name());
        
        int statusColor;
        switch (appt.status) {
            case PENDING: statusColor = Color.parseColor("#FDBA74"); break; // Peach
            case APPROVED: statusColor = Color.parseColor("#A0C4FF"); break; // Blue
            case COMPLETED: statusColor = Color.parseColor("#B7E4C7"); break; // Green
            case REJECTED: statusColor = Color.parseColor("#FFADAD"); break; // Red
            default: statusColor = Color.LTGRAY;
        }
        holder.tvStatus.setBackgroundColor(statusColor);
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatient, tvDoctor, tvDept, tvTime, tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPatient = itemView.findViewById(R.id.tvPatientName);
            tvDoctor = itemView.findViewById(R.id.tvDoctorName);
            tvDept = itemView.findViewById(R.id.tvDeptName);
            tvTime = itemView.findViewById(R.id.tvApptTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
