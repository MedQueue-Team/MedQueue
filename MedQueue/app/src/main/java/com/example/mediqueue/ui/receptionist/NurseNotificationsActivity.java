package com.example.mediqueue.ui.receptionist;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mediqueue.databinding.ActivityNurseNotificationsBinding;

public class NurseNotificationsActivity extends AppCompatActivity {

    private ActivityNurseNotificationsBinding binding;
    private NurseNotificationsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNurseNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(NurseNotificationsViewModel.class);

        setupToolbar();
        setupRecyclerViews();
        observeViewModel();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.toolbar.setNavigationContentDescription("Return to dashboard");

        binding.tvMarkAllRead.setOnClickListener(v -> {
            viewModel.markAllRead();
            binding.tvUnreadBadge.setVisibility(android.view.View.GONE);
            Toast.makeText(this, "All notifications marked as read", Toast.LENGTH_SHORT).show();
        });
        binding.tvMarkAllRead.setContentDescription("Mark all notifications as read");
    }

    private void setupRecyclerViews() {
        binding.rvRecentNotifications.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRecentNotifications.setNestedScrollingEnabled(false);

        binding.rvEarlierNotifications.setLayoutManager(new LinearLayoutManager(this));
        binding.rvEarlierNotifications.setNestedScrollingEnabled(false);
    }

    private void observeViewModel() {
        viewModel.getRecentNotifications().observe(this, recent -> {
            if (recent != null && !recent.isEmpty()) {
                binding.rvRecentNotifications.setAdapter(new NurseNotificationAdapter(recent));
                binding.tvSectionRecent.setVisibility(android.view.View.VISIBLE);
            }
        });

        viewModel.getEarlierNotifications().observe(this, earlier -> {
            if (earlier != null && !earlier.isEmpty()) {
                binding.rvEarlierNotifications.setAdapter(new NurseNotificationAdapter(earlier));
                binding.tvSectionEarlier.setVisibility(android.view.View.VISIBLE);
            }
        });

        viewModel.getUnreadCount().observe(this, count -> {
            if (count != null && count > 0) {
                binding.tvUnreadBadge.setVisibility(android.view.View.VISIBLE);
                binding.tvUnreadBadge.setText(count + " unread");
            } else {
                binding.tvUnreadBadge.setVisibility(android.view.View.GONE);
            }
        });
    }
}
