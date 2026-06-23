package com.example.mediqueue.ui.receptionist;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mediqueue.R;
import com.example.mediqueue.data.local.entities.QueueEntity;
import java.util.ArrayList;
import java.util.List;

public class NurseQueueManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerQueue;
    private QueuePatientAdapter adapter;
    private List<QueueEntity> patientList;
    private TextView txtWaitingTotal, txtAvgWaitTime, txtCriticalCount, txtConsultationCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_queue_management);

        initViews();
        setupRecyclerView();
        loadDummyData();
    }

    private void initViews() {
        recyclerQueue = findViewById(R.id.recyclerQueue);
        txtWaitingTotal = findViewById(R.id.txtWaitingTotal);
        txtAvgWaitTime = findViewById(R.id.txtAvgWaitTime);
        txtCriticalCount = findViewById(R.id.txtCriticalCount);
        txtConsultationCount = findViewById(R.id.txtConsultationCount);
    }

    private void setupRecyclerView() {
        patientList = new ArrayList<>();
        adapter = new QueuePatientAdapter(patientList);
        recyclerQueue.setLayoutManager(new LinearLayoutManager(this));
        recyclerQueue.setAdapter(adapter);
    }

    private void loadDummyData() {
        txtWaitingTotal.setText("24");
        txtAvgWaitTime.setText("18m");
        txtCriticalCount.setText("3");
        txtConsultationCount.setText("8");

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
}
