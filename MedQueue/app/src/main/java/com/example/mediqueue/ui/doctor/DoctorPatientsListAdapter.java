package com.example.mediqueue.ui.doctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.databinding.ItemDoctorPatientBinding;

import java.util.List;

public class DoctorPatientsListAdapter extends RecyclerView.Adapter<DoctorPatientsListAdapter.ViewHolder> {

    private List<DoctorPatientsListViewModel.WaitingPatient> patients;

    public DoctorPatientsListAdapter() {
        this.patients = new java.util.ArrayList<>();
    }

    public void setPatients(List<DoctorPatientsListViewModel.WaitingPatient> patients) {
        this.patients = patients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(
                ItemDoctorPatientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoctorPatientsListViewModel.WaitingPatient patient = patients.get(position);
        holder.binding.tvPatientName.setText(patient.name);
        holder.binding.tvQueueNo.setText(patient.id);
        holder.binding.tvDept.setText(patient.department);
        holder.binding.tvWaitTime.setText(patient.waitingTime);
        holder.binding.tvPriorityTag.setText(patient.priority);
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemDoctorPatientBinding binding;

        public ViewHolder(ItemDoctorPatientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
