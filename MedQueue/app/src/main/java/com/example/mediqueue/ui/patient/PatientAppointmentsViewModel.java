package com.example.mediqueue.ui.patient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.example.mediqueue.data.local.entities.AppointmentEntity;
import com.example.mediqueue.data.repository.AppointmentRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class PatientAppointmentsViewModel extends ViewModel {

    private final AppointmentRepository appointmentRepository;
    private final String defaultPatientId = "MQ-8821";

    private final MutableLiveData<String> filterState = new MutableLiveData<>("ALL");
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");

    @Inject
    public PatientAppointmentsViewModel(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public LiveData<List<AppointmentEntity>> getFilteredAppointments() {
        LiveData<List<AppointmentEntity>> allAppts = appointmentRepository.getAppointmentsForPatient(defaultPatientId);
        
        // We will transform the LiveData based on search query and filter selection
        return Transformations.switchMap(filterState, state -> 
            Transformations.map(allAppts, list -> {
                List<AppointmentEntity> result = new ArrayList<>();
                if (list == null) return result;
                
                String query = searchQuery.getValue() != null ? searchQuery.getValue().toLowerCase().trim() : "";
                
                for (AppointmentEntity appt : list) {
                    // Match State
                    boolean stateMatch = "ALL".equalsIgnoreCase(state) || state.equalsIgnoreCase(appt.status);
                    
                    // Match Query
                    boolean queryMatch = query.isEmpty() 
                            || (appt.doctorName != null && appt.doctorName.toLowerCase().contains(query))
                            || (appt.department != null && appt.department.toLowerCase().contains(query));
                    
                    if (stateMatch && queryMatch) {
                        result.add(appt);
                    }
                }
                return result;
            })
        );
    }

    public void setFilterState(String state) {
        filterState.setValue(state);
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
        // Trigger a change to filterState to force transformation update
        filterState.setValue(filterState.getValue());
    }
}
