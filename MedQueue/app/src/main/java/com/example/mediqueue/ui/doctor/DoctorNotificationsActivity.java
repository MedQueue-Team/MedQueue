package com.example.mediqueue.ui.doctor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import java.util.List;

public class DoctorNotificationsActivity extends AppCompatActivity {
    private DoctorNotificationsViewModel viewModel;
    private RecyclerView rvNotifications;
    private NotificationAdapter adapter;
    private TextView tvCriticalCount, tvWarningsCount, tvUpdatesCount;
    private Button btnMarkAllRead;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_notifications);

        btnBack = findViewById(R.id.btn_back);
        btnMarkAllRead = findViewById(R.id.btn_mark_all_read);
        rvNotifications = findViewById(R.id.rv_notifications);
        tvCriticalCount = findViewById(R.id.tv_critical_count);
        tvWarningsCount = findViewById(R.id.tv_warnings_count);
        tvUpdatesCount = findViewById(R.id.tv_updates_count);

        btnBack.setOnClickListener(v -> finish());
        btnMarkAllRead.setOnClickListener(v -> viewModel.markAllAsRead());

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        
        // Reusing the existing item_notification.xml. I'll create a simple adapter if needed, 
        // or check if there's one. Looking at the project, there is NotificationAdapter in patient.
        // I'll create a specific one for Doctor if it differs.
        adapter = new NotificationAdapter(this);
        rvNotifications.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(DoctorNotificationsViewModel.class);
        viewModel.getAllNotifications().observe(this, notifications -> {
            adapter.setNotifications(notifications);
            updateStats(notifications);
        });
    }

    private void updateStats(List<?> notifications) {
        if (notifications == null) return;
        // Mock stats since we don't have complex filtering logic yet
        tvCriticalCount.setText(String.valueOf(notifications.size() / 3));
        tvWarningsCount.setText(String.valueOf(notifications.size() / 2));
        tvUpdatesCount.setText(String.valueOf(notifications.size()));
    }
}
