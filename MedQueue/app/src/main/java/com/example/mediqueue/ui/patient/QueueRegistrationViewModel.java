package com.example.mediqueue.ui.patient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mediqueue.data.local.entities.QueueEntity;
import com.example.mediqueue.data.repository.QueueRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.UUID;
import javax.inject.Inject;

@HiltViewModel
public class QueueRegistrationViewModel extends ViewModel {

    private final QueueRepository queueRepository;

    private final MutableLiveData<String> selectedDept = new MutableLiveData<>("Cardiology");
    private final MutableLiveData<Boolean> joinSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<String> generatedTicketId = new MutableLiveData<>("102");

    @Inject
    public QueueRegistrationViewModel(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    public LiveData<String> getSelectedDept() { return selectedDept; }
    public LiveData<Boolean> getJoinSuccess() { return joinSuccess; }
    public LiveData<String> getGeneratedTicketId() { return generatedTicketId; }

    public void setSelectedDept(String dept) { selectedDept.setValue(dept); }
    public void setJoinSuccess(boolean success) { joinSuccess.setValue(success); }

    public void joinQueue() {
        new Thread(() -> {
            try {
                String ticket = String.valueOf(100 + (int)(Math.random() * 900));
                generatedTicketId.postValue(ticket);

                QueueEntity ticketEntity = new QueueEntity();
                ticketEntity.queueId = ticket;
                ticketEntity.patientId = "MQ-8821";
                ticketEntity.department = selectedDept.getValue();
                ticketEntity.position = 4; // Assigned 4th position
                ticketEntity.estimatedWaitTimeMins = 12;
                ticketEntity.status = "WAITING";
                ticketEntity.lastUpdated = System.currentTimeMillis();
                ticketEntity.isSynced = false;

                queueRepository.insertQueue(ticketEntity);
                joinSuccess.postValue(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
