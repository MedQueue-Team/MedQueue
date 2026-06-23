package com.example.mediqueue.ui.receptionist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mediqueue.core.SessionManager;
import com.example.mediqueue.databinding.FragmentNurseSettingsBinding;
import com.example.mediqueue.ui.login.LoginActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NurseSettingsFragment extends Fragment {

    private FragmentNurseSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNurseSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.toolbar.setNavigationOnClickListener(v -> {
            try {
                androidx.navigation.Navigation.findNavController(v).popBackStack();
            } catch (Exception e) {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });

        binding.btnEditProfile.setOnClickListener(v ->
            Toast.makeText(requireContext(), "Opening Profile Editor...", Toast.LENGTH_SHORT).show()
        );

        binding.cvNotifications.setOnClickListener(v ->
            Toast.makeText(requireContext(), "Notification Settings", Toast.LENGTH_SHORT).show()
        );

        binding.cvSecurity.setOnClickListener(v ->
            Toast.makeText(requireContext(), "Privacy & Security Settings", Toast.LENGTH_SHORT).show()
        );

        binding.cvUnits.setOnClickListener(v ->
            Toast.makeText(requireContext(), "Units of Measurement Preferences", Toast.LENGTH_SHORT).show()
        );

        binding.cvLanguage.setOnClickListener(v ->
            Toast.makeText(requireContext(), "Language Settings", Toast.LENGTH_SHORT).show()
        );

        binding.cvLogout.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Logging out...", Toast.LENGTH_SHORT).show();
            SessionManager sessionManager = new SessionManager(requireContext());
            sessionManager.logout();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
