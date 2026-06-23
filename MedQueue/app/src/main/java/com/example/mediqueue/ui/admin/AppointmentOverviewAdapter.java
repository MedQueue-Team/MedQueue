package com.example.mediqueue.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import java.util.List;

public class AppointmentOverviewAdapter extends RecyclerView.Adapter<AppointmentOverviewAdapter.ViewHolder> {
    private List<Appointment> appointments;

    public AppointmentOverviewAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.tvPatientName.setText(appointment.getPatientName());
        holder.tvDoctorName.setText(appointment.getDoctorName());
        holder.tvDeptName.setText(appointment.getDepartment());
        holder.tvAppointmentTime.setText(appointment.getTime());

        Appointment.Status status = appointment.getStatus();
        holder.tvStatusTag.setText(status.name());
        int resId;
        int colorId;
        switch (status) {
            case PENDING:
                resId = R.drawable.bg_tag_pending;
                colorId = R.color.tertiary;
                break;
            case APPROVED:
                resId = R.drawable.bg_tag_approved;
                colorId = R.color.primary;
                break;
            case COMPLETED:
                resId = R.drawable.bg_tag_completed;
                colorId = R.color.secondary;
                break;
            case REJECTED:
                resId = R.drawable.bg_tag_rejected;
                colorId = R.color.error;
                break;
            default:
                resId = R.drawable.bg_tag_pending;
                colorId = R.color.on_surface_variant;
        }
        holder.tvStatusTag.setBackgroundResource(resId);
        holder.tvStatusTag.setTextColor(holder.itemView.getContext().getResources().getColor(colorId));
    }

    @Override
    public int getItemCount() {
        return appointments != null ? appointments.size() : 0;
    }

    public void updateData(List<Appointment> newAppointments) {
        this.appointments = newAppointments;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName, tvDoctorName, tvDeptName, tvAppointmentTime, tvStatusTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tv_patient_name);
            tvDoctorName = itemView.findViewById(R.id.tv_doctor_name);
            tvDeptName = itemView.findViewById(R.id.tv_dept_name);
            tvAppointmentTime = itemView.findViewById(R.id.tv_appointment_time);
            tvStatusTag = itemView.findViewById(R.id.tv_status_tag);
        }
    }
}
