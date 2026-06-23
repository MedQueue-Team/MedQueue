package com.example.mediqueue.ui.receptionist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entities.QueueEntity;

import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.QueueViewHolder> {
    private List<QueueEntity> patients;

    public QueueAdapter(List<QueueEntity> patients) {
        this.patients = patients;
    }

    @NonNull
    @Override
    public QueueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_queue_patient, parent, false);
        return new QueueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QueueViewHolder holder, int position) {
        QueueEntity patient = patients.get(position);
        holder.tvQueueNo.setText(patient.getQueueNumber());
        holder.tvPatientName.setText(patient.getName());
        holder.tvDeptReg.setText(patient.department);
        holder.tvWaitTime.setText("Wait: " + patient.estimatedWaitTimeMins + "m");
    }

    @Override
    public int getItemCount() {
        return patients != null ? patients.size() : 0;
    }

    public static class QueueViewHolder extends RecyclerView.ViewHolder {
        TextView tvQueueNo, tvPatientName, tvDeptReg, tvWaitTime;
        public QueueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQueueNo = itemView.findViewById(R.id.tv_queue_no);
            tvPatientName = itemView.findViewById(R.id.tv_patient_name);
            tvDeptReg = itemView.findViewById(R.id.tv_dept_reg);
            tvWaitTime = itemView.findViewById(R.id.tv_wait_time);
        }
    }
}
