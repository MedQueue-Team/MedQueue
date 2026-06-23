package com.example.mediqueue.ui.receptionist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.R;
import com.example.mediqueue.databinding.ItemNurseNotificationBinding;

import java.util.List;

public class NurseNotificationAdapter extends RecyclerView.Adapter<NurseNotificationAdapter.ViewHolder> {

    private final List<NurseNotification> notifications;

    public NurseNotificationAdapter(List<NurseNotification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNurseNotificationBinding binding = ItemNurseNotificationBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NurseNotification notification = notifications.get(position);
        ItemNurseNotificationBinding b = holder.binding;
        Context ctx = b.getRoot().getContext();

        b.tvNotifTitle.setText(notification.getTitle());
        b.tvNotifMessage.setText(notification.getMessage());
        b.tvNotifTime.setText(notification.getTime());

        // Urgent indicator stripe
        b.urgentStripe.setVisibility(notification.isUrgent() ? View.VISIBLE : View.GONE);

        // Type-specific icon + colour
        applyTypeStyling(b, notification, ctx);

        // Action buttons
        if (notification.hasActions()) {
            b.llActions.setVisibility(View.VISIBLE);
            b.btnPrimary.setText(notification.getPrimaryAction());

            if (notification.getSecondaryAction() != null) {
                b.btnSecondary.setVisibility(View.VISIBLE);
                b.btnSecondary.setText(notification.getSecondaryAction());
            } else {
                b.btnSecondary.setVisibility(View.GONE);
            }

            b.btnPrimary.setOnClickListener(v ->
                Toast.makeText(ctx, notification.getPrimaryAction() + " — " + notification.getTitle(),
                        Toast.LENGTH_SHORT).show());
            b.btnSecondary.setOnClickListener(v ->
                Toast.makeText(ctx, notification.getSecondaryAction() + " — " + notification.getTitle(),
                        Toast.LENGTH_SHORT).show());
        } else {
            b.llActions.setVisibility(View.GONE);
        }

        // Accessibility
        b.getRoot().setContentDescription(
                "Notification: " + notification.getTitle() + ". " + notification.getMessage());
    }

    private void applyTypeStyling(ItemNurseNotificationBinding b,
                                  NurseNotification n, Context ctx) {
        int iconRes, iconColor, bgColor;

        switch (n.getType()) {
            case TRIAGE_ALERT:
                iconRes   = R.drawable.ic_emergency;
                iconColor = R.color.error;
                bgColor   = R.color.error_container;
                break;
            case PATIENT_TRANSFER:
                iconRes   = R.drawable.ic_send;
                iconColor = R.color.tertiary;
                bgColor   = R.color.tertiary_fixed;
                break;
            case EMERGENCY:
                iconRes   = R.drawable.ic_warning_priority;
                iconColor = R.color.error;
                bgColor   = R.color.error_container;
                break;
            case QUEUE_UPDATE:
                iconRes   = R.drawable.ic_pending_priority;
                iconColor = R.color.primary;
                bgColor   = R.color.primary_fixed;
                break;
            case SYSTEM:
            default:
                iconRes   = R.drawable.ic_info;
                iconColor = R.color.on_surface_variant;
                bgColor   = R.color.surface_variant;
                break;
        }

        b.ivNotifIcon.setImageResource(iconRes);
        b.ivNotifIcon.setColorFilter(ContextCompat.getColor(ctx, iconColor));
        b.cvIconBg.setCardBackgroundColor(ContextCompat.getColor(ctx, bgColor));

        // Urgent: make time text red
        if (n.isUrgent()) {
            b.tvNotifTime.setTextColor(ContextCompat.getColor(ctx, R.color.error));
        } else {
            b.tvNotifTime.setTextColor(ContextCompat.getColor(ctx, R.color.on_surface_variant));
        }
    }

    @Override
    public int getItemCount() { return notifications.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemNurseNotificationBinding binding;
        ViewHolder(@NonNull ItemNurseNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
