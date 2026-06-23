package com.example.mediqueue.ui.admin;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    private List<StaffMember> staffList;
    private Context context;

    public StaffAdapter(List<StaffMember> staffList) {
        this.staffList = staffList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_staff_workload, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StaffMember staff = staffList.get(position);
        holder.tvStaffName.setText(staff.getName());
        holder.tvDepartment.setText(staff.getDepartment());
        holder.tvLoadPercentage.setText(staff.getLoad() + "%");
        holder.pbWorkload.setProgress(staff.getLoad());
        holder.tvStatusBadge.setText(staff.getStatus());
        holder.tvStatusMessage.setText(staff.getStatusMessage());
        holder.ivAvatar.setImageResource(staff.getAvatarRes());

        // Dynamic coloring and status logic
        int color;
        int badgeTint;
        int statusColor = context.getColor(R.color.secondary);

        if (staff.getLoad() > 80) {
            color = context.getColor(R.color.error);
            badgeTint = context.getColor(R.color.error_container);
        } else if (staff.getLoad() > 50) {
            color = context.getColor(R.color.primary);
            badgeTint = context.getColor(R.color.primary_container);
        } else {
            color = context.getColor(R.color.secondary);
            badgeTint = context.getColor(R.color.secondary_container);
        }

        if (staff.getStatus().equalsIgnoreCase("On-Break")) {
            statusColor = context.getColor(R.color.tertiary);
            badgeTint = context.getColor(R.color.tertiary_fixed);
            holder.tvStatusBadge.setTextColor(context.getColor(R.color.on_tertiary_fixed_variant));
        } else {
            holder.tvStatusBadge.setTextColor(context.getColor(R.color.on_secondary_container));
        }
        
        holder.tvLoadPercentage.setTextColor(color);
        holder.pbWorkload.setProgressTintList(ColorStateList.valueOf(color));
        holder.statusIndicator.setBackgroundTintList(ColorStateList.valueOf(statusColor));
        holder.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(badgeTint));
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        View statusIndicator;
        TextView tvStaffName, tvDepartment, tvLoadPercentage, tvStatusBadge, tvStatusMessage;
        ProgressBar pbWorkload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            statusIndicator = itemView.findViewById(R.id.status_indicator);
            tvStaffName = itemView.findViewById(R.id.tvStaffName);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            tvLoadPercentage = itemView.findViewById(R.id.tvLoadPercentage);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
            tvStatusMessage = itemView.findViewById(R.id.tvStatusMessage);
            pbWorkload = itemView.findViewById(R.id.pbWorkload);
        }
    }
}
