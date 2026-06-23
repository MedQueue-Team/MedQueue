package com.example.mediqueue.ui.receptionist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mediqueue.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TransferPatientBottomSheet extends BottomSheetDialogFragment {

    public static TransferPatientBottomSheet newInstance(String patientName) {
        TransferPatientBottomSheet fragment = new TransferPatientBottomSheet();
        Bundle args = new Bundle();
        args.putString("PATIENT_NAME", patientName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_transfer_patient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String name = getArguments() != null ? getArguments().getString("PATIENT_NAME") : "Patient";
        
        view.findViewById(R.id.btnTransfer).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Transferring " + name + "...", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        view.findViewById(R.id.btnClose).setOnClickListener(v -> dismiss());
    }
}
