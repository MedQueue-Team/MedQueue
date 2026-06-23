package com.example.mediqueue.ui.admin;

import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mediqueue.R;
import com.example.mediqueue.data.local.dao.UserDao;
import com.example.mediqueue.data.local.entity.User;
import com.example.mediqueue.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AdminViewModel extends BaseViewModel {

    private final UserDao userDao;
    private final MutableLiveData<List<Appointment>> appointments = new MutableLiveData<>();
    private final MutableLiveData<List<Schedule>> schedules = new MutableLiveData<>();
    private final MutableLiveData<List<StaffMember>> staffMembers = new MutableLiveData<>();

    @Inject
    public AdminViewModel(UserDao userDao) {
        this.userDao = userDao;
        loadMockAppointments();
        loadMockSchedules();
        loadMockStaff();
    }

    public LiveData<List<User>> getAllStaff() {
        return userDao.getAllUsers();
    }

    public LiveData<List<StaffMember>> getStaffMembers() {
        return staffMembers;
    }

    public LiveData<List<Appointment>> getAppointments() {
        return appointments;
    }

    public LiveData<List<Schedule>> getSchedules() {
        return schedules;
    }

    public void addSchedule(Schedule schedule) {
        List<Schedule> current = schedules.getValue();
        if (current != null) {
            List<Schedule> updated = new ArrayList<>(current);
            updated.add(schedule);
            schedules.setValue(updated);
        }
    }

    public void removeSchedule(Schedule schedule) {
        List<Schedule> current = schedules.getValue();
        if (current != null) {
            List<Schedule> updated = new ArrayList<>(current);
            updated.remove(schedule);
            schedules.setValue(updated);
        }
    }

    private void loadMockAppointments() {
        List<Appointment> mockAppts = new ArrayList<>();
        mockAppts.add(new Appointment("John Banda", "Dr. Phiri", "General OPD", "Today, 10:30 AM", Appointment.Status.PENDING));
        mockAppts.add(new Appointment("Sarah Moyo", "Dr. Lungu", "Cardiology", "Tomorrow, 09:15 AM", Appointment.Status.APPROVED));
        mockAppts.add(new Appointment("Misozi Tembo", "Dr. Banda", "Pediatrics", "Yesterday, 14:00 PM", Appointment.Status.COMPLETED));
        mockAppts.add(new Appointment("Chansa Mwale", "Dr. Kapuya", "Dermatology", "Cancelled by Admin", Appointment.Status.REJECTED));
        appointments.setValue(mockAppts);
    }

    private void loadMockSchedules() {
        List<Schedule> mockSchedules = new ArrayList<>();
        mockSchedules.add(new Schedule("Dr. John Phiri", "General OPD", "Mon, Wed, Fri", "08:00 - 16:00"));
        mockSchedules.add(new Schedule("Dr. Amina Juma", "Pediatrics", "Tue, Thu, Sat", "09:00 - 15:00"));
        mockSchedules.add(new Schedule("Dr. Robert Smith", "Cardiology", "Mon - Fri", "08:00 - 12:00"));
        schedules.setValue(mockSchedules);
    }

    private void loadMockStaff() {
        List<StaffMember> mockStaff = new ArrayList<>();
        mockStaff.add(new StaffMember("Dr. James Okoro", 92, "General Medicine", 24, R.drawable.ic_person, "Critical", "Overloaded - 8 in queue"));
        mockStaff.add(new StaffMember("Nurse Amina Bello", 45, "Triage Desk", 12, R.drawable.ic_person, "Active", "Processing patients normally"));
        mockStaff.add(new StaffMember("Dr. Sarah Chen", 68, "Pediatrics", 18, R.drawable.ic_person, "On-Break", "Back in 15 mins"));
        mockStaff.add(new StaffMember("Pharm. David Lee", 78, "Pharmacy", 42, R.drawable.ic_person, "On-Duty", "High prescription volume"));
        staffMembers.setValue(mockStaff);
    }
}
