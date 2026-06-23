package com.example.mediqueue.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<Schedule> schedules;
    private OnScheduleActionListener listener;

    public interface OnScheduleActionListener {
        void onEdit(Schedule schedule);
        void onDelete(Schedule schedule);
    }

    public ScheduleAdapter(List<Schedule> schedules, OnScheduleActionListener listener) {
        this.schedules = schedules;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);
        holder.tvDoctorName.setText(schedule.getDoctorName());
        holder.tvDoctorInitials.setText(getInitials(schedule.getDoctorName()));
        holder.tvDeptTag.setText(schedule.getDepartment());
        holder.tvDays.setText(schedule.getDays());
        holder.tvTime.setText(schedule.getTime());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(schedule));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(schedule));
    }

    private String getInitials(String name) {
        if (name == null || name.isEmpty()) return "??";
        String[] parts = name.split(" ");
        StringBuilder initials = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) initials.append(part.toUpperCase().charAt(0));
        }
        return initials.toString();
    }

    @Override
    public int getItemCount() {
        return schedules != null ? schedules.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDoctorName, tvDoctorInitials, tvDeptTag, tvDays, tvTime;
        Button btnEdit;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tv_doctor_name);
            tvDoctorInitials = itemView.findViewById(R.id.tv_doctor_initials);
            tvDeptTag = itemView.findViewById(R.id.tv_dept_tag);
            tvDays = itemView.findViewById(R.id.tv_days);
            tvTime = itemView.findViewById(R.id.tv_time);
            btnEdit = itemView.findViewById(R.id.btn_edit_schedule);
            btnDelete = itemView.findViewById(R.id.btn_delete_schedule);
        }
    }
}

