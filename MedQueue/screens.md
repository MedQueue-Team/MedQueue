# MediQueue – Screen Implementation Status & Stitch Prompts

> **Generated**: May 15, 2026  
> **Purpose**: Track which screens from the temp_designs have been implemented in the Android app and provide Google Stitch prompts for screens that still need to be designed.

---

## 📊 Implementation Status Summary

| Status | Count |
|--------|-------|
| ✅ Has Design + Has Implementation | 29 |
| ⚠️ Has Design + **NO** Implementation | 0 |
| 🔴 **NO** Design + **NO** Implementation | 9 |

---

## ✅ Screens WITH Design AND Implementation (Already Done)

These screens exist in both `temp_designs/` and the Android `app/src/main/`:

| # | Design Screen | Android Activity | Layout XML |
|---|--------------|-----------------|------------|
| 1 | `staff_patient_login` | `LoginActivity.kt` | `activity_login.xml` |
| 2 | `patient_dashboard_smart_ai_features` | `PatientDashboardActivity.java` | `activity_patient_dashboard.xml` |
| 3 | `patient_notifications` | `PatientNotificationsActivity.java` | `activity_patient_notifications.xml` |
| 4 | `patient_settings_minimal_redesign` | `SettingsActivity.java` | `activity_settings.xml` |
| 5 | `medical_history` | `MedicalHistoryActivity.java` | `activity_medical_history.xml` |
| 6 | `medical_history_full_view` | `MedicalHistoryActivity.java` | `activity_medical_history.xml` |
| 7 | `patient_records_no_nav` | `MedicalRecordsActivity.java` | `activity_medical_records.xml` |
| 8 | `register_join_queue_updated_nav` | `JoinQueueActivity.java` | `activity_join_queue.xml` |
| 9 | `doctor_queue_dashboard` | `DoctorDashboardActivity.kt` | `activity_doctor_dashboard.xml` |
| 10 | `doctor_settings` | `DoctorSettingsActivity.java` | `activity_doctor_settings.xml` |
| 11 | `nurse_triage_dashboard_home_nav` | `NurseDashboardActivity.kt` | `activity_nurse_dashboard.xml` |
| 12 | `nurse_notifications_organized_sorted` | `NurseNotificationsActivity.java` | `activity_nurse_notifications.xml` |
| 13 | `nurse_records_minimal_professional` | `NurseRecordsActivity.java` | `activity_nurse_records.xml` |
| 14 | `nurse_settings_streamlined` | `NurseSettingsActivity.java` | `activity_nurse_settings.xml` |
| 15 | `triage_assessment_home_nav` | `TriageAssessmentFragment.java` | `fragment_triage_assessment.xml` |
| 16 | `patient_registration_info` | `PatientRegistrationActivity.java` | `activity_patient_registration.xml` |
| 17 | `patient_registration_simplified_header` | `PatientRegistrationActivity.java` | `activity_patient_registration.xml` |
| 18 | `transfer_patient` | `TransferPatientBottomSheet.java` | `bottom_sheet_transfer_patient.xml` |
| 19 | `clinical_precision` | Design System (DESIGN.md only) | N/A |
| 20 | `mediqueue_design_system` | Design System (DESIGN.md only) | N/A |
| 21 | `patient_detail` (exists in app) | `PatientDetailActivity.java` | `activity_patient_detail.xml` |
| 22 | `digital_prescriptions` | `DigitalPrescriptionActivity.kt` | Compose Based |
| 23 | `doctor_appointments_schedule` | `DoctorAppointmentsActivity.kt` | - |
| 24 | `doctor_notifications` | `DoctorNotificationsActivity.java` | `activity_doctor_notifications.xml` |
| 25 | `doctor_patients_list` | `DoctorPatientsListActivity.java` | `activity_doctor_patients_list.xml` |
| 26 | `invoice_summary` | `InvoiceSummaryActivity.java` | `activity_invoice_summary.xml` |
| 27 | `payment_successful` | `PaymentSuccessfulActivity.java` | `activity_payment_successful.xml` |
| 28 | `patient_medical_history_nurse` | `PatientMedicalHistoryFragment.java` | `fragment_patient_medical_history_nurse.xml` |
| 29 | `nurse_queue_management` | `NurseQueueManagementActivity.java` | `activity_nurse_queue_management.xml` |

---

## ⚠️ Screens WITH Design but **NO** Implementation (Need Coding)

These screens have designs in `temp_designs/` but **no corresponding Activity/layout** in the app:

> **NOTE**: All screens previously in this section have been verified as implemented in the codebase.

---

## 🔴 Screens with **NO** Design AND **NO** Implementation (Need Stitch Prompts)

These screens are mentioned in the README/ROADMAP but have neither designs nor implementations. Below are **Google Stitch prompts** to generate them:

---

### Prompt 1: Admin Reports Dashboard

```
Design a mobile screen for a healthcare queue management app called "MediQueue" using the Clinical Precision design system.

Screen: Admin Reports Dashboard
Role: Hospital Administrator

This is the reports and analytics dashboard for hospital administrators. It should include:
- A header with "Reports & Analytics" title and date range selector (This Week / This Month / Custom)
- Key performance indicator cards in a bento grid layout:
  - Total Patients Served (with trend arrow)
  - Average Wait Time (with comparison to last period)
  - Patient Satisfaction Score (percentage with rating)
  - Staff Utilization Rate (percentage with bar chart)
- A "Department Performance" section with horizontal bar charts showing patient throughput by department (General Medicine, Emergency, Pediatrics, etc.)
- A "Peak Hours" heat map or chart showing busiest times
- Export button (Download PDF / CSV)
- Bottom navigation with: Dashboard, Staff, Reports (active), Settings

Design system: Clinical Precision with Primary Blue (#0056b3), Inter font, 8px corner radius, minimal shadows, high-contrast clinical aesthetic. Mobile-first (390px width).
```

---

### Prompt 2: Admin System Configuration

```
Design a mobile screen for a healthcare queue management app called "MediQueue" using the Clinical Precision design system.

Screen: System Configuration
Role: Hospital Administrator

This is the system settings/configuration screen for admins. It should include:
- Header with "System Configuration" title and back arrow
- Sections organized in cards:
  1. "Facility Settings" - Clinic name, operating hours, timezone, capacity limits
  2. "Department Management" - List of departments (General Medicine, Emergency, Pediatrics, etc.) with toggle switches to enable/disable, reorder handle, and "Add Department" button
  3. "Queue Settings" - Max queue capacity, auto-close time, priority escalation thresholds
  4. "Notification Preferences" - Toggle switches for SMS alerts, email notifications, push notifications
  5. "Data & Privacy" - Data retention period, export patient data, HIPAA compliance toggle
- Each section has a subtle card with 12px rounded corners
- Save Changes button at bottom (sticky)

Design system: Clinical Precision with Primary Blue (#0056b3), Inter font, 8px corner radius, clean white backgrounds, minimal shadows. Mobile-first (390px width).
```

---

### Prompt 3: Patient Forgot Password / Password Recovery

```
Design a mobile screen for a healthcare queue management app called "MediQueue" using the Clinical Precision design system.

Screen: Password Recovery
Role: Patient

This is the forgot password / password recovery flow. It should include:
- MediQueue logo and "Reset Password" heading
- Subtitle: "Enter your registered email address and we'll send you a verification code"
- Email input field with mail icon
- "Send Verification Code" primary button (full width, rounded)
- "Back to Login" text link below
- A security info card at the bottom: "Your account security is our priority. The verification code expires in 10 minutes."
- Clean, clinical white background with subtle blue accents
- Trust indicators: lock icon, encrypted connection badge

Design system: Clinical Precision with Primary Blue (#0056b3), Inter font, 8px corner radius, generous whitespace, calming clinical aesthetic. Mobile-first (390px width).
```

---

### Prompt 4: Patient Appointment History

```
Design a mobile screen for a healthcare queue management app called "MediQueue" using the Clinical Precision design system.

Screen: Appointment History
Role: Patient

This is the patient's appointment history and management screen. It should include:
- Header with "My Appointments" title and filter icon
- Tab bar: "Upcoming" (active) | "Past" | "Cancelled"
- Upcoming appointments as cards showing:
  - Doctor name and specialty
  - Date and time with calendar icon
  - Location/clinic name
  - Status pill (Confirmed / Pending)
  - Actions: "Reschedule" outline button, "Cancel" text button
- Past appointments in a simpler list format with:
  - Doctor name, date, diagnosis summary
  - "View Details" and "Book Again" links
- Empty state for cancelled tab with illustration placeholder and "No cancelled appointments" message
- Floating action button: "Book New Appointment" with calendar+ icon
- Bottom navigation with: Home, Queue, Appointments (active), Profile

Design system: Clinical Precision with Primary Blue (#0056b3), Inter font, 8px corner radius, card-based layout with subtle shadows. Mobile-first (390px width).
```

---

### Prompt 5: Patient Profile / Account Management

```
Design a mobile screen for a healthcare queue management app called "MediQueue" using the Clinical Precision design system.

Screen: Patient Profile
Role: Patient

This is the patient's profile and account management screen. It should include:
- Profile header section with:
  - Circular profile photo placeholder (initials fallback)
  - Patient name (large, bold)
  - Patient ID badge (e.g., "LSK-KNH-004582")
  - "Edit Profile" outlined button
- Personal Information card:
  - Full Name, Date of Birth, Gender, Blood Type
  - Phone Number, Email Address
  - Emergency Contact (name and phone)
- Medical Information card:
  - Allergies (listed as pills/chips: "Penicillin", "Sulfa")
  - Chronic Conditions ("Type 2 Diabetes", "Hypertension")
  - Current Medications count with "View All" link
- Insurance Information card:
  - Provider name, Policy number, Coverage type
- Account Actions section:
  - Change Password
  - Notification Preferences
  - Language Settings
  - Delete Account (in error/red color)
- Bottom navigation

Design system: Clinical Precision with Primary Blue (#0056b3), Inter font, 8px corner radius, clean card layout. Mobile-first (390px width).
```

---

### Prompt 6: Doctor Profile Management

```
Design a mobile screen for a healthcare queue management app called "MediQueue" using the Clinical Precision design system.

Screen: Doctor Profile
Role: Doctor

This is the doctor's profile management screen. It should include:
- Profile header with:
  - Profile photo placeholder
  - Dr. [Name], [Specialty] (e.g., "Dr. Sarah Okafor, General Practitioner")
  - Staff ID badge
  - "On Duty" status indicator (green dot)
- Professional Details card:
  - Specialization, License Number, Years of Experience
  - Department assignment, Consultation Room
- Today's Summary card (quick stats):
  - Patients seen today, Remaining in queue, Average consultation time
- Schedule Overview:
  - Mini calendar showing available/busy slots for the week
- Quick Actions grid:
  - View Full Schedule, My Patients, Prescriptions, Clinical Notes
- Account settings link at bottom

Design system: Clinical Precision with Primary Blue (#0056b3), Inter font, 8px corner radius. Mobile-first (390px width).
```

---

### Prompt 7: Enhanced Onboarding / Welcome Screens

```
Design a set of 3 mobile onboarding screens for a healthcare queue management app called "MediQueue" using the Clinical Precision design system.

Screen: Patient Onboarding Carousel
Role: Patient (first-time user)

Screen 1 - "Skip the Wait":
- Large illustration area showing a patient checking their phone with a queue number
- Headline: "Skip the Wait, Not the Care"
- Subtext: "Join queues remotely and get real-time updates on your position"
- Progress dots (1 of 3 active)
- "Next" button and "Skip" text link

Screen 2 - "Your Health, Organized":
- Illustration of medical records organized digitally
- Headline: "Your Health History, Always Available"
- Subtext: "Access your medical records, prescriptions, and appointment history in one place"
- Progress dots (2 of 3)

Screen 3 - "Smart Triage":
- Illustration of AI-assisted symptom checker
- Headline: "AI-Powered Health Guidance"
- Subtext: "Get smart triage assessments to help you understand your symptoms before your visit"
- Progress dots (3 of 3)
- "Get Started" primary button (full width)

Design system: Clinical Precision with Primary Blue (#0056b3), Inter font, generous whitespace, calming medical illustrations style. Mobile-first (390px width).
```

---

### Prompt 8: Admin Staff Management (Enhanced)

```
Design a mobile screen for a healthcare queue management app called "MediQueue" using the Clinical Precision design system.

Screen: Staff Management (Enhanced)
Role: Hospital Administrator

This is an enhanced staff management screen for administrators. It should include:
- Header: "Staff Management" with search icon and add staff (+) button
- Staff summary cards row:
  - Total Staff (count)
  - On Duty (count with green indicator)
  - Off Duty (count with gray indicator)
- Filter chips: All, Doctors, Nurses, Receptionists, Admin
- Staff list as professional cards showing:
  - Staff photo (circular, initials fallback)
  - Name, Role badge (Doctor/Nurse/Admin in colored chip)
  - Department, Shift status (On Duty/Off Duty)
  - Workload indicator: mini progress bar showing patient load
  - Quick actions: message icon, schedule icon, more options
- Each card has subtle left border color-coded by role (blue=doctor, green=nurse, amber=admin)
- Floating action button for "Add New Staff"

Design system: Clinical Precision with Primary Blue (#0056b3), Inter font, 8px corner radius, data-dense but clean layout. Mobile-first (390px width).
```

---

### Prompt 9: Patient Browse Clinics (Enhanced)

```
Design a mobile screen for a healthcare queue management app called "MediQueue" using the Clinical Precision design system.

Screen: Browse Clinics (Enhanced)
Role: Patient

This is an enhanced clinic browsing screen for patients. It should include:
- Header: "Find a Clinic" with location pin showing current area
- Search bar: "Search clinics, specialties, or doctors..."
- Filter row: Distance, Rating, Specialty dropdown, Open Now toggle
- Nearby clinics as rich cards showing:
  - Clinic name (bold)
  - Star rating (4.5/5) and review count
  - Distance ("1.2 km away")
  - Address line
  - Operating hours with open/closed status indicator
  - Available services as small chips (General, Lab, Pharmacy, X-Ray)
  - Current queue length: "12 patients waiting • ~25 min wait"
  - "Join Queue" primary button and "Book Appointment" outline button
- Map toggle button in the corner to switch between list and map view
- Bottom navigation with: Home, Queue, Clinics (active), Profile

Design system: Clinical Precision with Primary Blue (#0056b3), Inter font, 8px corner radius, card-based with subtle shadows. Mobile-first (390px width).
```

---

## 📋 Implementation Priority Order

### Sprint 1 (Highest Priority – Core Workflows)

1. ⚠️ **Doctor Patients List** – Core doctor workflow
2. ⚠️ **Doctor Appointments Schedule** – Core doctor workflow
3. ⚠️ **Digital Prescriptions** – Core doctor workflow
4. ⚠️ **Patient Medical History (Nurse View)** – Core nurse triage workflow
5. ⚠️ **Nurse Queue Management** – Core nurse workflow

### Sprint 2 (Medium Priority – Payment & Notifications)

1. ⚠️ **Invoice Summary** – Payment flow
2. ⚠️ **Payment Successful** – Payment flow completion
3. ⚠️ **Doctor Notifications** – Doctor awareness

### Sprint 3 (Lower Priority – Enhancement Screens, Need Stitch Designs First)

1. 🔴 Patient Forgot Password
2. 🔴 Patient Appointment History
3. 🔴 Patient Profile / Account Management
4. 🔴 Admin Reports Dashboard
5. 🔴 Admin System Configuration
6. 🔴 Admin Staff Management (Enhanced)
7. 🔴 Doctor Profile Management
8. 🔴 Enhanced Onboarding
9. 🔴 Patient Browse Clinics (Enhanced)

---

## 🔧 Architecture Components Still Needed (from ROADMAP Phase 2-3)

These backend/architecture items from the checklist are not yet implemented:

| Component | Status | Notes |
|-----------|--------|-------|
| Navigation Graphs (XML) | ✅ Completed | Defined in nav_graph.xml with destinations for the Nurse user role |
| PatientViewModel (consolidated) | ❌ Not started | Replace activity-specific logic |
| DoctorViewModel (consolidated) | ❌ Not started | Needed for doctor screens |
| AdminViewModel (consolidated) | ❌ Not started | Needed for admin screens |
| NurseViewModel (consolidated) | ❌ Not started | Needed for nurse screens |
| AuthRepository | ✅ Completed | Clean repository with API/Room fallback and Hilt injection |
| PatientRepository | ✅ Completed | Fully integrated local data cache |
| AppointmentRepository | ✅ Completed | Integrated with local and remote appointments |
| QueueRepository | ✅ Completed | Handles patient priority queue listings |
| NotificationRepository | ✅ Completed | Manages patient & staff notifications |
| Room Database Schema | ✅ Completed | Structured Room database with preloaded mock data |
| DAOs (all tables) | ✅ Completed | All required DAOs (User, Patient, Queue, etc.) fully written |
| WebSocket Integration | ❌ Not started | Real-time updates |
| FCM Push Notifications | ❌ Not started | Push alerts |
| WorkManager (Offline Sync) | ❌ Not started | Background sync |
| Biometric Auth (complete) | ❌ Partial | BiometricHelper exists |

---

## 📝 Notes

- All existing designs follow the **Clinical Precision** design system with Primary Blue (#0056b3/#003f87), Inter/Manrope fonts, and 8px corner radii.
- The `code.html` files in temp_designs are reference implementations using Tailwind CSS and can guide Android XML layout creation.
- Screen PNG screenshots are available in each design directory for visual reference.
- The Stitch prompts above are crafted to match the existing design language and can be used directly in Google Stitch to generate new screen designs.
