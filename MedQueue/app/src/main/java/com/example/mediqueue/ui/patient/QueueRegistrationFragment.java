package com.example.mediqueue.ui.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.mediqueue.R;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QueueRegistrationFragment extends Fragment {

    private QueueRegistrationViewModel viewModel;

    private CardView cardCardiology, cardOPD, cardPediatrics, cardDental;
    private ImageView ivCheckCardio, ivCheckOPD, ivCheckPeds, ivCheckDental;
    private Button btnJoinQueueSubmit;

    private ConstraintLayout layoutTicketModal;
    private TextView tvTicketDept, tvTicketNumber, tvTicketPos;
    private Button btnTicketDone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_queue_registration, container, false);

        viewModel = new ViewModelProvider(this).get(QueueRegistrationViewModel.class);

        // Bind Department Cards
        cardCardiology = view.findViewById(R.id.cardCardiology);
        cardOPD = view.findViewById(R.id.cardOPD);
        cardPediatrics = view.findViewById(R.id.cardPediatrics);
        cardDental = view.findViewById(R.id.cardDental);

        // Bind Checks
        ivCheckCardio = view.findViewById(R.id.ivCheckCardio);
        ivCheckOPD = view.findViewById(R.id.ivCheckOPD);
        ivCheckPeds = view.findViewById(R.id.ivCheckPeds);
        ivCheckDental = view.findViewById(R.id.ivCheckDental);

        btnJoinQueueSubmit = view.findViewById(R.id.btnJoinQueueSubmit);

        // Bind Ticket Overlay Views
        layoutTicketModal = view.findViewById(R.id.layoutTicketModal);
        tvTicketDept = view.findViewById(R.id.tvTicketDept);
        tvTicketNumber = view.findViewById(R.id.tvTicketNumber);
        tvTicketPos = view.findViewById(R.id.tvTicketPos);
        btnTicketDone = view.findViewById(R.id.btnTicketDone);

        setupDepartmentSelection();
        setupObservers();

        // Join Queue trigger
        btnJoinQueueSubmit.setOnClickListener(v -> viewModel.joinQueue());

        // Done ticket confirmation
        btnTicketDone.setOnClickListener(v -> {
            layoutTicketModal.setVisibility(View.GONE);
            Navigation.findNavController(v).navigateUp();
        });

        // Back button click
        view.findViewById(R.id.btnBack).setOnClickListener(v -> 
                Navigation.findNavController(v).navigateUp());

        return view;
    }

    private void setupDepartmentSelection() {
        cardCardiology.setOnClickListener(v -> {
            viewModel.setSelectedDept("Cardiology");
            updateSelectionUI(ivCheckCardio, ivCheckOPD, ivCheckPeds, ivCheckDental);
        });

        cardOPD.setOnClickListener(v -> {
            viewModel.setSelectedDept("General OPD");
            updateSelectionUI(ivCheckOPD, ivCheckCardio, ivCheckPeds, ivCheckDental);
        });

        cardPediatrics.setOnClickListener(v -> {
            viewModel.setSelectedDept("Pediatrics");
            updateSelectionUI(ivCheckPeds, ivCheckCardio, ivCheckOPD, ivCheckDental);
        });

        cardDental.setOnClickListener(v -> {
            viewModel.setSelectedDept("Dental");
            updateSelectionUI(ivCheckDental, ivCheckCardio, ivCheckOPD, ivCheckPeds);
        });
    }

    private void updateSelectionUI(ImageView selectedCheck, ImageView... unselectedChecks) {
        selectedCheck.setVisibility(View.VISIBLE);
        for (ImageView iv : unselectedChecks) {
            iv.setVisibility(View.GONE);
        }
    }

    private void setupObservers() {
        viewModel.getSelectedDept().observe(getViewLifecycleOwner(), dept -> 
            btnJoinQueueSubmit.setText("JOIN " + dept.toUpperCase() + " QUEUE")
        );

        viewModel.getJoinSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                String dept = viewModel.getSelectedDept().getValue();
                String ticket = viewModel.getGeneratedTicketId().getValue();

                tvTicketDept.setText(dept + " Clinic");
                tvTicketNumber.setText("#" + ticket);
                tvTicketPos.setText("Position in queue: #4");

                layoutTicketModal.setVisibility(View.VISIBLE);
            }
        });
    }
}
