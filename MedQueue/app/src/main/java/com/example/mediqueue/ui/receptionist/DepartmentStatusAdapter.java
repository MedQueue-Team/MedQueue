package com.example.mediqueue.ui.receptionist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import java.util.List;

public class DepartmentStatusAdapter extends RecyclerView.Adapter<DepartmentStatusAdapter.ViewHolder> {
    private List<DepartmentStatus> departments;

    public DepartmentStatusAdapter(List<DepartmentStatus> departments) {
        this.departments = departments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DepartmentStatus dept = departments.get(position);
        holder.tvDeptName.setText(dept.getName());
        holder.tvDeptStats.setText(dept.getPatientCount() + " Patients • " + dept.getAvgWaitTime());
        holder.tvActiveDoctors.setText(dept.getActiveDoctors() + " Doctors Active");
        holder.pbProgress.setProgress(dept.getProgress());
    }

    @Override
    public int getItemCount() {
        return departments != null ? departments.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDeptName, tvDeptStats, tvActiveDoctors;
        ProgressBar pbProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeptName = itemView.findViewById(R.id.tv_dept_name);
            tvDeptStats = itemView.findViewById(R.id.tv_dept_stats);
            tvActiveDoctors = itemView.findViewById(R.id.tv_active_doctors);
            pbProgress = itemView.findViewById(R.id.pb_dept_progress);
        }
    }
}
