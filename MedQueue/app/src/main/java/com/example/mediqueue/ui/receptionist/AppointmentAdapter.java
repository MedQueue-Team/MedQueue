package com.example.mediqueue.ui.receptionist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entities.AppointmentEntity;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<AppointmentEntity> appointments;
    private OnAppointmentActionListener listener;

    public interface OnAppointmentActionListener {
        void onApprove(AppointmentEntity appointment);
        void onReject(AppointmentEntity appointment);
    }

    public AppointmentAdapter(List<AppointmentEntity> appointments, OnAppointmentActionListener listener) {
        this.appointments = appointments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_approval, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentEntity appointment = appointments.get(position);
        holder.tvPatientName.setText(appointment.getPatientName());
        holder.tvAppointmentTime.setText(appointment.getTime() + " • " + appointment.getReason());
        holder.tvRequestTime.setText("Just now"); // Mock value
        holder.tvAppointmentDesc.setText(appointment.getNotes());

        holder.btnApprove.setOnClickListener(v -> listener.onApprove(appointment));
        holder.btnReject.setOnClickListener(v -> listener.onReject(appointment));
    }

    @Override
    public int getItemCount() {
        return appointments != null ? appointments.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName, tvAppointmentTime, tvRequestTime, tvAppointmentDesc;
        Button btnApprove, btnReject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tv_patient_name);
            tvAppointmentTime = itemView.findViewById(R.id.tv_appointment_time);
            tvRequestTime = itemView.findViewById(R.id.tv_request_time);
            tvAppointmentDesc = itemView.findViewById(R.id.tv_appointment_desc);
            btnApprove = itemView.findViewById(R.id.btn_approve);
            btnReject = itemView.findViewById(R.id.btn_reject);
        }
    }
}
