package com.example.mediqueue.ui.receptionist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NurseRecordsViewModel extends ViewModel {

    private final MutableLiveData<List<NurseRecord>> records = new MutableLiveData<>();
    private final MutableLiveData<List<NurseRecord>> filteredRecords = new MutableLiveData<>();
    private List<NurseRecord> allRecords = new ArrayList<>();
    private NurseRecord.RecordType activeFilter = NurseRecord.RecordType.ALL;

    public NurseRecordsViewModel() {
        seedData();
    }

    private void seedData() {
        allRecords = Arrays.asList(
            new NurseRecord("LSK-KNH-011452", "Catherine Chen",    34, "F",
                NurseRecord.RecordType.INPATIENT,    "Dr. Mwangi",   "12 May 2026", "Ward 4B", "HIGH"),
            new NurseRecord("LSK-KNH-009871", "James Odhiambo",   57, "M",
                NurseRecord.RecordType.INPATIENT,    "Dr. Otieno",   "12 May 2026", "Ward 2A", "CRITICAL"),
            new NurseRecord("LSK-KNH-013305", "Amara Okafor",     29, "F",
                NurseRecord.RecordType.TRIAGE,       "Unassigned",   "12 May 2026", null,       "HIGH"),
            new NurseRecord("LSK-KNH-010987", "David Kamau",      42, "M",
                NurseRecord.RecordType.CONSULTATION, "Dr. Njoroge",  "11 May 2026", null,       "MEDIUM"),
            new NurseRecord("LSK-KNH-012001", "Faith Wanjiku",    25, "F",
                NurseRecord.RecordType.TRIAGE,       "Unassigned",   "12 May 2026", null,       "LOW"),
            new NurseRecord("LSK-KNH-008654", "Samuel Mutua",     63, "M",
                NurseRecord.RecordType.INPATIENT,    "Dr. Mwangi",   "10 May 2026", "ICU",      "CRITICAL"),
            new NurseRecord("LSK-KNH-014210", "Grace Akinyi",     38, "F",
                NurseRecord.RecordType.CONSULTATION, "Dr. Otieno",   "11 May 2026", null,       "MEDIUM"),
            new NurseRecord("LSK-KNH-007732", "Patrick Njiru",    55, "M",
                NurseRecord.RecordType.TRIAGE,       "Unassigned",   "12 May 2026", null,       "HIGH"),
            new NurseRecord("LSK-KNH-015099", "Lydia Chebet",     31, "F",
                NurseRecord.RecordType.CONSULTATION, "Dr. Njoroge",  "9 May 2026",  null,       "LOW"),
            new NurseRecord("LSK-KNH-006543", "Moses Kipchoge",   70, "M",
                NurseRecord.RecordType.INPATIENT,    "Dr. Wambua",   "8 May 2026",  "Ward 1C",  "MEDIUM")
        );

        records.setValue(allRecords);
        filteredRecords.setValue(allRecords);
    }

    public LiveData<List<NurseRecord>> getFilteredRecords() { return filteredRecords; }

    public void applyFilter(NurseRecord.RecordType type) {
        activeFilter = type;
        applySearchAndFilter(""); // reset search on tab switch
    }

    public void applySearchAndFilter(String query) {
        List<NurseRecord> filtered = new ArrayList<>();
        for (NurseRecord r : allRecords) {
            if ((activeFilter == NurseRecord.RecordType.ALL || r.getRecordType() == activeFilter)
                    && (query.isEmpty()
                    || r.getPatientName().toLowerCase().contains(query.toLowerCase())
                    || r.getPatientId().toLowerCase().contains(query.toLowerCase()))) {
                filtered.add(r);
            }
        }
        filteredRecords.setValue(filtered);
    }
}
