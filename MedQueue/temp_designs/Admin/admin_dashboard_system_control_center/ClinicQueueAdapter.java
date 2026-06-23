package com.example.mediqueue.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import java.util.ArrayList;
import java.util.List;

public class ClinicQueueAdapter extends RecyclerView.Adapter<ClinicQueueAdapter.ViewHolder> {

    public interface OnClinicActionListener {
        void onCallNext(ClinicQueue clinic);
        void onToggleStatus(ClinicQueue clinic);
    }

    private final List<ClinicQueue> items = new ArrayList<>();
    private final OnClinicActionListener listener;

    public ClinicQueueAdapter(OnClinicActionListener listener) {
        this.listener = listener;
    }

    public void updateData(List<ClinicQueue> newItems) {
        this.items.clear();
        if (newItems != null) {
            this.items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clinic_queue, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvClinicName;
        private final TextView tvDoctorName;
        private final TextView tvQueueCount;
        private final TextView tvServingToken;
        private final TextView tvNextToken;
        private final TextView tvStatusBadge;
        private final Button btnCallNext;
        private final Button btnToggleStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClinicName = itemView.findViewById(R.id.tv_clinic_name);
            tvDoctorName = itemView.findViewById(R.id.tv_doctor_name);
            tvQueueCount = itemView.findViewById(R.id.tv_queue_count);
            tvServingToken = itemView.findViewById(R.id.tv_serving_token);
            tvNextToken = itemView.findViewById(R.id.tv_next_token);
            tvStatusBadge = itemView.findViewById(R.id.tv_status_badge);
            btnCallNext = itemView.findViewById(R.id.btn_call_next);
            btnToggleStatus = itemView.findViewById(R.id.btn_toggle_status);
        }

        public void bind(ClinicQueue item, OnClinicActionListener listener) {
            tvClinicName.setText(item.getName());
            tvDoctorName.setText(item.getDoctorName());
            tvQueueCount.setText(String.valueOf(item.getQueueCount()));
            tvServingToken.setText(item.getCurrentTokenDisplay());
            tvNextToken.setText(item.getNextTokenDisplay());

            // Handle badge styles
            switch (item.getStatus()) {
                case ACTIVE:
                    tvStatusBadge.setText("ACTIVE");
                    tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_active);
                    tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.on_secondary_fixed_variant));
                    btnCallNext.setEnabled(item.getQueueCount() > 0);
                    btnCallNext.setAlpha(item.getQueueCount() > 0 ? 1.0f : 0.5f);
                    btnToggleStatus.setText("Pause");
                    itemView.setAlpha(1.0f);
                    break;
                case SUSPENDED:
                    tvStatusBadge.setText("SUSPENDED");
                    tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_suspended);
                    tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.tertiary));
                    btnCallNext.setEnabled(false);
                    btnCallNext.setAlpha(0.5f);
                    btnToggleStatus.setText("Resume");
                    itemView.setAlpha(0.9f);
                    break;
                case CLOSED:
                    tvStatusBadge.setText("CLOSED");
                    tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_closed);
                    tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.on_error_container));
                    btnCallNext.setEnabled(false);
                    btnCallNext.setAlpha(0.4f);
                    btnToggleStatus.setText("Open");
                    itemView.setAlpha(0.6f);
                    break;
            }

            btnCallNext.setOnClickListener(v -> {
                if (listener != null) listener.onCallNext(item);
            });

            btnToggleStatus.setOnClickListener(v -> {
                if (listener != null) listener.onToggleStatus(item);
            });
        }
    }
}
