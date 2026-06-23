package com.example.mediqueue.ui.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entities.Notification;
import com.example.mediqueue.databinding.ItemNotificationBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Notification> notifications;
    private final SimpleDateFormat timeFormatter = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());

    public NotificationAdapter(Context context) {
        this.notifications = new java.util.ArrayList<>();
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        ItemNotificationBinding binding = holder.binding;
        Context context = binding.getRoot().getContext();

        binding.tvNotificationTitle.setText(notification.getTitle());
        binding.tvNotificationMessage.setText(notification.getMessage());
        binding.tvNotificationTime.setText(timeFormatter.format(new Date(notification.getTimestamp())));

        binding.urgentIndicator.setVisibility(notification.isUrgent() ? View.VISIBLE : View.GONE);
        binding.llActions.setVisibility(View.GONE);
        binding.tvActionText.setVisibility(View.GONE);

        applyTypeSpecificStyling(binding, notification, context);
    }

    private void applyTypeSpecificStyling(ItemNotificationBinding binding, Notification notification, Context context) {
        int iconRes;
        int iconColor;
        int bgColor;

        switch (notification.getType()) {
            case QUEUE:
                iconRes = R.drawable.ic_notifications_active;
                iconColor = R.color.primary;
                bgColor = R.color.primary_fixed;
                binding.llActions.setVisibility(View.VISIBLE);
                break;
            case SYNC:
                iconRes = R.drawable.ic_sync;
                iconColor = R.color.secondary;
                bgColor = R.color.secondary_container;
                break;
            case SUMMARY:
                iconRes = R.drawable.ic_summarize;
                iconColor = R.color.tertiary;
                bgColor = R.color.tertiary_fixed;
                if (notification.getActionText() != null) {
                    binding.tvActionText.setVisibility(View.VISIBLE);
                    binding.tvActionText.setText(notification.getActionText());
                }
                break;
            case EVENT:
                iconRes = R.drawable.ic_event_upcoming;
                iconColor = R.color.error;
                bgColor = R.color.error_container;
                break;
            case SECURITY:
                iconRes = R.drawable.ic_security;
                iconColor = R.color.on_surface_variant;
                bgColor = R.color.surface_variant;
                break;
            default:
                iconRes = R.drawable.ic_notifications;
                iconColor = R.color.primary;
                bgColor = R.color.primary_fixed;
                break;
        }

        binding.ivNotificationIcon.setImageResource(iconRes);
        binding.ivNotificationIcon.setColorFilter(ContextCompat.getColor(context, iconColor));
        binding.cvIcon.setCardBackgroundColor(ContextCompat.getColor(context, bgColor));
    }

    @Override
    public int getItemCount() {
        return notifications == null ? 0 : notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemNotificationBinding binding;

        public ViewHolder(@NonNull ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
