package com.example.mediqueue.ui.receptionist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mediqueue.databinding.FragmentNurseQueueBinding;
import com.example.mediqueue.data.local.entities.QueueEntity;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NurseQueueFragment extends Fragment {

    private FragmentNurseQueueBinding binding;
    private QueuePatientAdapter adapter;
    private List<QueueEntity> patientList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNurseQueueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        loadDummyData();
    }

    private void setupRecyclerView() {
        patientList = new ArrayList<>();
        adapter = new QueuePatientAdapter(patientList);
        binding.recyclerQueue.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerQueue.setAdapter(adapter);
    }

    private void loadDummyData() {
        binding.txtWaitingTotal.setText("24");
        binding.txtAvgWaitTime.setText("18m");
        binding.txtCriticalCount.setText("3");
        binding.txtConsultationCount.setText("8");

        patientList.clear();

        QueueEntity p1 = new QueueEntity();
        p1.queueId = "Q-001"; p1.patientId = "P-001"; p1.department = "Emergency"; p1.position = 1; p1.estimatedWaitTimeMins = 2; p1.status = "WAITING";
        QueueEntity p2 = new QueueEntity();
        p2.queueId = "Q-002"; p2.patientId = "P-002"; p2.department = "Neurology"; p2.position = 2; p2.estimatedWaitTimeMins = 15; p2.status = "WAITING";
        QueueEntity p3 = new QueueEntity();
        p3.queueId = "Q-003"; p3.patientId = "P-003"; p3.department = "General"; p3.position = 3; p3.estimatedWaitTimeMins = 25; p3.status = "WAITING";
        QueueEntity p4 = new QueueEntity();
        p4.queueId = "Q-004"; p4.patientId = "P-004"; p4.department = "General"; p4.position = 4; p4.estimatedWaitTimeMins = 45; p4.status = "WAITING";

        patientList.add(p1);
        patientList.add(p2);
        patientList.add(p3);
        patientList.add(p4);
        
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
