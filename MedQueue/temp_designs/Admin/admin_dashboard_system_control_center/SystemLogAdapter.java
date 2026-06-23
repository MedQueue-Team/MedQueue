package com.example.mediqueue.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import java.util.ArrayList;
import java.util.List;

public class SystemLogAdapter extends RecyclerView.Adapter<SystemLogAdapter.ViewHolder> {

    private final List<SystemLog> items = new ArrayList<>();

    public void updateData(List<SystemLog> newLogs) {
        this.items.clear();
        if (newLogs != null) {
            // We show items in reverse order or standard order. Since in Android Layout we can do stackFromEnd, standard order is best
            this.items.addAll(newLogs);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_system_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvLogText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLogText = itemView.findViewById(R.id.tv_log_text);
        }

        public void bind(SystemLog item) {
            String displayText = "[" + item.getTimestamp() + "] " + item.getType().name() + ": " + item.getMessage();
            tvLogText.setText(displayText);

            int colorRes;
            switch (item.getType()) {
                case INFO:
                    colorRes = android.R.color.white;
                    break;
                case WARNING:
                    colorRes = android.R.color.holo_orange_light;
                    break;
                case ERROR:
                    colorRes = android.R.color.holo_red_light;
                    break;
                case SUCCESS:
                    colorRes = android.R.color.holo_green_light;
                    break;
                default:
                    colorRes = android.R.color.darker_gray;
                    break;
            }
            tvLogText.setTextColor(ContextCompat.getColor(itemView.getContext(), colorRes));
        }
    }
}
