package com.example.mediqueue.ui.receptionist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entities.QueueEntity;
import java.util.List;

public class QueuePatientAdapter extends RecyclerView.Adapter<QueuePatientAdapter.ViewHolder> {
    private List<QueueEntity> patients;
    private OnPatientActionListener listener;

    public interface OnPatientActionListener {
        void onEdit(QueueEntity patient);
        void onMove(QueueEntity patient);
        void onCall(QueueEntity patient);
        void onDelete(QueueEntity patient);
    }

    public QueuePatientAdapter(List<QueueEntity> patients, OnPatientActionListener listener) {
        this.patients = patients;
        this.listener = listener;
    }

    public QueuePatientAdapter(List<QueueEntity> patients) {
        this.patients = patients;
        this.listener = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_queue_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QueueEntity patient = patients.get(position);
        holder.tvQueueNo.setText(patient.getQueueNumber());
        holder.tvPatientName.setText(patient.getName());
        holder.tvDeptReg.setText(patient.getDepartment() + " • Reg: " + patient.getRegistrationTime());
        holder.tvWaitTime.setText("Wait: " + patient.getWaitTime() + "m");
        holder.tvStatusTag.setText(patient.status);

        if ("Urgent".equalsIgnoreCase(patient.getPriority())) {
            holder.tvPriorityTag.setVisibility(View.VISIBLE);
            holder.tvPriorityTag.setText("Urgent");
        } else {
            holder.tvPriorityTag.setVisibility(View.GONE);
        }

        if (listener != null) {
            holder.btnEdit.setOnClickListener(v -> listener.onEdit(patient));
            holder.btnMove.setOnClickListener(v -> listener.onMove(patient));
            holder.btnCall.setOnClickListener(v -> listener.onCall(patient));
            holder.btnDelete.setOnClickListener(v -> listener.onDelete(patient));
        }
    }

    @Override
    public int getItemCount() {
        return patients != null ? patients.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQueueNo, tvPatientName, tvDeptReg, tvWaitTime, tvStatusTag, tvPriorityTag;
        ImageView btnEdit, btnMove, btnDelete;
        Button btnCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQueueNo = itemView.findViewById(R.id.tv_queue_no);
            tvPatientName = itemView.findViewById(R.id.tv_patient_name);
            tvDeptReg = itemView.findViewById(R.id.tv_dept_reg);
            tvWaitTime = itemView.findViewById(R.id.tv_wait_time);
            tvStatusTag = itemView.findViewById(R.id.tv_status_tag);
            tvPriorityTag = itemView.findViewById(R.id.tv_priority_tag);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnMove = itemView.findViewById(R.id.btn_move);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnCall = itemView.findViewById(R.id.btn_call);
        }
    }
}
