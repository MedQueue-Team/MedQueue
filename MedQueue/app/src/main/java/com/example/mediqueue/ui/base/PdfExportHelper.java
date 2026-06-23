package com.example.mediqueue.ui.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.widget.Toast;

import com.example.mediqueue.data.local.entities.MedicalHistory;
import com.example.mediqueue.ui.receptionist.TriagePatient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfExportHelper {

    public static void exportMedicalHistoryToPdf(Context context, List<MedicalHistory> historyList) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        
        paint.setTextSize(18f);
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLUE);
        canvas.drawText("MediQueue Medical Report", 20, 40, paint);
        
        paint.setTextSize(10f);
        paint.setFakeBoldText(false);
        paint.setColor(Color.GRAY);
        canvas.drawText("Patient ID: LSK-KNH-004582", 20, 60, paint);
        
        canvas.drawLine(20, 70, 280, 70, paint);
        
        paint.setColor(Color.BLACK);
        int y = 90;
        for (MedicalHistory history : historyList) {
            if (y > 550) break;
            
            paint.setFakeBoldText(true);
            canvas.drawText(history.getDate() + " - " + history.getTitle(), 20, y, paint);
            
            y += 15;
            paint.setFakeBoldText(false);
            canvas.drawText("Location: " + history.getLocation(), 20, y, paint);
            
            y += 15;
            canvas.drawText("Provider: " + history.getProvider(), 20, y, paint);
            
            y += 30;
        }

        document.finishPage(page);

        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Medical_Report.pdf");

        try {
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(context, "PDF Exported to: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error exporting PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        document.close();
    }

    public static void exportTriageAssessmentToPdf(Context context, TriagePatient patient) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        // Header
        paint.setTextSize(18f);
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLUE);
        canvas.drawText("Triage Assessment Report", 20, 40, paint);

        paint.setTextSize(10f);
        paint.setFakeBoldText(false);
        paint.setColor(Color.GRAY);
        canvas.drawText("Generated via MediQueue Smart Triage", 20, 60, paint);

        // Divider
        canvas.drawLine(20, 70, 280, 70, paint);

        // Patient Details
        paint.setColor(Color.BLACK);
        paint.setTextSize(12f);
        paint.setFakeBoldText(true);
        int y = 100;
        canvas.drawText("Patient Name: " + patient.getName(), 20, y, paint);
        y += 20;
        canvas.drawText("Patient ID: " + patient.getId(), 20, y, paint);
        y += 20;
        canvas.drawText("Age/Gender: " + patient.getAge() + " / " + patient.getGender(), 20, y, paint);
        y += 30;

        // Clinical Vitals
        canvas.drawText("Clinical Vitals:", 20, y, paint);
        y += 20;
        paint.setFakeBoldText(false);
        canvas.drawText("Blood Pressure: " + (patient.getBloodPressure() != null ? patient.getBloodPressure() : "N/A"), 30, y, paint);
        y += 15;
        canvas.drawText("Temperature: " + (patient.getTemperature() != null ? patient.getTemperature() + "°C" : "N/A"), 30, y, paint);
        y += 15;
        canvas.drawText("Pulse Rate: " + (patient.getPulseRate() != null ? patient.getPulseRate() + " BPM" : "N/A"), 30, y, paint);
        y += 30;

        // Assessment Summary
        paint.setFakeBoldText(true);
        canvas.drawText("Priority Level: " + patient.getPriority(), 20, y, paint);
        y += 25;
        canvas.drawText("Primary Complaint:", 20, y, paint);
        y += 15;
        paint.setFakeBoldText(false);
        
        // Simple text wrapping for complaint
        String complaint = patient.getPrimaryComplaint() != null ? patient.getPrimaryComplaint() : "No complaint recorded.";
        if (complaint.length() > 40) {
            canvas.drawText(complaint.substring(0, 40), 30, y, paint);
            y += 15;
            canvas.drawText(complaint.substring(40, Math.min(complaint.length(), 80)), 30, y, paint);
        } else {
            canvas.drawText(complaint, 30, y, paint);
        }

        document.finishPage(page);

        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), 
                "Triage_Report_" + patient.getId() + ".pdf");

        try {
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(context, "Triage Report Exported", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Export Failed", Toast.LENGTH_SHORT).show();
        }

        document.close();
    }
}
