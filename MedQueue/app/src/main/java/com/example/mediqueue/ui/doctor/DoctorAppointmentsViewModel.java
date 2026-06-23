package com.example.mediqueue.ui.doctor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorAppointmentsViewModel extends ViewModel {

    public enum AppointmentStatus {
        ALL, PENDING, APPROVED, COMPLETED
    }

    public static class AppointmentDetail {
        public String id;
        public String name;
        public String department;
        public AppointmentStatus status;
        public String time;
        public String reason;
        public String previousDiagnosis;
        public String initials;

        public AppointmentDetail(String id, String initials, String name, String department, AppointmentStatus status, String time, String reason, String previousDiagnosis) {
            this.id = id;
            this.initials = initials;
            this.name = name;
            this.department = department;
            this.status = status;
            this.time = time;
            this.reason = reason;
            this.previousDiagnosis = previousDiagnosis;
        }
    }

    private final List<AppointmentDetail> allAppointments = new ArrayList<>();
    private final MutableLiveData<List<AppointmentDetail>> filteredAppointments = new MutableLiveData<>();
    private final MutableLiveData<AppointmentStatus> currentFilter = new MutableLiveData<>(AppointmentStatus.ALL);
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");

    public DoctorAppointmentsViewModel() {
        loadMockData();
        applyFilterAndSearch();
    }

    private void loadMockData() {
        allAppointments.add(new AppointmentDetail("1", "JB", "John Banda", "GENERAL OPD", AppointmentStatus.APPROVED, "10:30 AM", "Fever and headache", "Hypertension"));
        allAppointments.add(new AppointmentDetail("2", "MP", "Mary Phiri", "DENTAL", AppointmentStatus.PENDING, "11:00 AM", "Tooth pain", null));
        allAppointments.add(new AppointmentDetail("3", "SJ", "Sarah Jenkins", "CARDIOLOGY", AppointmentStatus.APPROVED, "12:15 PM", "Post-Op Checkup", null));
        allAppointments.add(new AppointmentDetail("4", "AM", "Alexander Mercer", "GENERAL MEDICINE", AppointmentStatus.COMPLETED, "09:00 AM", "Regular checkup", "Diabetes"));
    }

    public void setFilter(AppointmentStatus status) {
        currentFilter.setValue(status);
        applyFilterAndSearch();
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
        applyFilterAndSearch();
    }

    private void applyFilterAndSearch() {
        AppointmentStatus filter = currentFilter.getValue();
        String query = searchQuery.getValue() == null ? "" : searchQuery.getValue().toLowerCase();

        List<AppointmentDetail> result = allAppointments.stream()
                .filter(a -> filter == AppointmentStatus.ALL || a.status == filter)
                .filter(a -> a.name.toLowerCase().contains(query) || a.id.toLowerCase().contains(query))
                .collect(Collectors.toList());

        filteredAppointments.setValue(result);
    }

    public LiveData<List<AppointmentDetail>> getAppointments() {
        return filteredAppointments;
    }

    public LiveData<AppointmentStatus> getCurrentFilter() {
        return currentFilter;
    }
}
