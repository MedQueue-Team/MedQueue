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
import com.example.mediqueue.data.local.entity.User;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> users;
    private OnUserActionListener listener;

    public interface OnUserActionListener {
        void onView(User user);
        void onEdit(User user);
        void onDisable(User user);
    }

    public UserAdapter(List<User> users, OnUserActionListener listener) {
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.tvUserName.setText(user.getFullName());
        holder.tvUserEmail.setText(user.getEmail());
        holder.tvUserPhone.setText(user.getPhone());
        holder.tvUserRole.setText(user.getRole().name());
        
        // Mock department for users who aren't admins
        if (com.example.mediqueue.core.UserRole.ADMIN != user.getRole()) {
            holder.tvUserDept.setText("General OPD");
        } else {
            holder.tvUserDept.setVisibility(View.GONE);
        }

        holder.btnView.setOnClickListener(v -> listener.onView(user));
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(user));
        holder.btnDisable.setOnClickListener(v -> listener.onDisable(user));
    }

    public void updateData(List<User> newUsers) {
        this.users = newUsers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvUserEmail, tvUserPhone, tvUserRole, tvUserDept, tvUserStatus;
        ImageView ivUserProfile;
        Button btnView, btnEdit, btnDisable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserEmail = itemView.findViewById(R.id.tv_user_email);
            tvUserPhone = itemView.findViewById(R.id.tv_user_phone);
            tvUserRole = itemView.findViewById(R.id.tv_user_role);
            tvUserDept = itemView.findViewById(R.id.tv_user_dept);
            tvUserStatus = itemView.findViewById(R.id.tv_user_status);
            ivUserProfile = itemView.findViewById(R.id.iv_user_profile);
            btnView = itemView.findViewById(R.id.btn_user_view);
            btnEdit = itemView.findViewById(R.id.btn_user_edit);
            btnDisable = itemView.findViewById(R.id.btn_user_disable);
        }
    }
}
