package com.example.mediqueue.ui.receptionist;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediqueue.R;

import java.util.List;

public class MedicalEncounterAdapter extends RecyclerView.Adapter<MedicalEncounterAdapter.ViewHolder> {

    private List<MedicalEncounter> encounters;

    public MedicalEncounterAdapter(List<MedicalEncounter> encounters) {
        this.encounters = encounters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medical_encounter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedicalEncounter encounter = encounters.get(position);
        holder.txtDateTime.setText(encounter.getDateTime());
        holder.txtTitle.setText(encounter.getTitle());
        holder.txtFacility.setText(encounter.getFacility());
        holder.txtProvider.setText(encounter.getProvider());
        holder.txtSummary.setText(encounter.getSummary());
        holder.txtStatus.setText(encounter.getStatus());

        if (encounter.isOngoing()) {
            holder.txtStatus.setTextColor(Color.parseColor("#003F87"));
            holder.timelineIndicator.setBackgroundColor(Color.parseColor("#003F87"));
        } else {
            holder.txtStatus.setTextColor(Color.parseColor("#424752"));
            holder.timelineIndicator.setBackgroundColor(Color.parseColor("#C2C6D4"));
        }
    }

    @Override
    public int getItemCount() { return encounters.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDateTime, txtTitle, txtFacility, txtProvider, txtSummary, txtStatus;
        View timelineIndicator;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDateTime = itemView.findViewById(R.id.txtEncounterDate);
            txtTitle = itemView.findViewById(R.id.txtEncounterTitle);
            txtFacility = itemView.findViewById(R.id.txtFacility);
            txtProvider = itemView.findViewById(R.id.txtProvider);
            txtSummary = itemView.findViewById(R.id.txtEncounterSummary);
            txtStatus = itemView.findViewById(R.id.txtEncounterStatus);
            timelineIndicator = itemView.findViewById(R.id.timelineIndicator);
        }
    }
}
