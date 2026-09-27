# MedQueue

## AI-Powered Hospital Queue and Appointment Management System

MedQueue is a healthcare management application designed to improve patient flow, appointment management, queue coordination, and communication between patients and healthcare staff.

The system provides an Android mobile application supported by a Spring Boot backend. It is designed around the needs of **patients, doctors, receptionists, and administrators**, providing each role with functionality appropriate to their responsibilities.

MedQueue also explores the use of Artificial Intelligence to improve healthcare operations through intelligent triage, waiting-time prediction, medical-record summarization, and patient assistance.

---

## Problem Statement

Healthcare facilities can experience challenges such as:

* Long and unpredictable patient waiting times
* Manual queue management
* Inefficient appointment handling
* Limited visibility of patient flow
* Difficulty coordinating patients between receptionists and doctors
* Delays in accessing relevant patient information
* Limited digital tools for assisting patients with healthcare navigation

MedQueue aims to provide a centralized digital platform that helps healthcare facilities manage these processes more efficiently.

---

## Objectives

The main objectives of MedQueue are to:

* Digitize hospital queue management
* Simplify appointment booking and management
* Improve coordination between patients, receptionists, and doctors
* Provide doctors with access to relevant patient information
* Maintain digital medical records
* Support offline data access and synchronization
* Provide administrative insights through analytics
* Explore AI-assisted healthcare workflow improvements

---

# Key Features

## Patient Management

Patients can:

* Register and authenticate securely
* Manage their patient profile
* Upload a profile picture
* Book appointments
* View appointments
* Join the hospital queue
* Track their queue information
* Access medical records
* View relevant notifications
* Complete smart triage information where available

---

## Appointment Management

The system supports appointment workflows including:

* Appointment booking
* Appointment retrieval
* Appointment approval
* Appointment rejection
* Doctor appointment management
* Receptionist appointment management
* Patient appointment history

---

## Queue Management

MedQueue provides digital queue management for healthcare facilities.

Queue functionality includes:

* Patient queue registration
* Queue status management
* Queue priority management
* Queue number management
* Department assignment
* Queue deletion
* Doctor waiting queue
* Patient calling
* Starting consultations
* Completing consultations

---

## Doctor Workflow

Doctors can:

* View patients waiting in the queue
* Call patients
* Start consultations
* Complete consultations
* Access relevant patient information
* Create medical records
* Complete medical records

---

## Receptionist Management

Receptionists can manage hospital queues and appointments through dedicated functionality.

This includes:

* Viewing the current queue
* Updating queue status
* Updating queue priority
* Updating queue numbers
* Changing queue departments
* Removing queue entries
* Viewing appointments
* Approving appointments
* Rejecting appointments

---

## Administration

Administrators can:

* View hospital dashboard analytics
* Create doctor schedules
* View doctor schedules
* Monitor hospital operations

---

# AI Integration

MedQueue is designed with AI-assisted healthcare functionality in mind.

The repository currently documents several proposed AI integration areas, including:

### 1. AI-Powered Smart Triage

Natural Language Processing can be used to analyze symptoms entered by patients and assist with assigning an appropriate priority level such as:

* Critical
* Urgent
* Normal

The purpose is to support healthcare staff in prioritizing patients based on available information.

### 2. Intelligent Waiting-Time Prediction

Machine-learning models can be used to estimate patient waiting times using information such as:

* Historical consultation duration
* Doctor specialty
* Patient volume
* Queue size

This can provide patients with more useful estimates of their expected waiting time.

### 3. Medical Record Summarization

An AI/LLM component can generate concise patient summaries from medical history to help doctors quickly review relevant information before a consultation.

### 4. AI Patient Assistant

A conversational AI assistant can help patients:

* Navigate the application
* Understand available services
* Get answers to common questions
* Understand the appointment process
* Access general healthcare information

These AI integration areas are documented in the project's `AI_INTEGRATIONS.md` file.

> **Important:** AI-generated information is intended to support healthcare workflows and should not replace professional medical diagnosis or clinical decision-making.

---

# Technology Stack

## Android Application

* Java
* Kotlin
* Android SDK
* XML layouts
* Retrofit
* Room Database
* SharedPreferences / local session management
* Gradle

## Backend

* Java
* Spring Boot
* Maven
* RESTful APIs
* JWT-based authentication
* PostgreSQL

## Architecture and Components

The Android application contains several architectural components, including:

* API service layer
* Repository layer
* Local database layer
* Entity models
* Session management
* Role resolution
* Dependency injection
* Connectivity monitoring
* Background synchronization

---

# Application Architecture

MedQueue follows a client-server architecture.

```text
┌───────────────────────────────────────────────┐
│                 Android App                  │
│                                               │
│  Java + Kotlin + XML                         │
│                                               │
│  ┌────────────┐   ┌────────────┐             │
│  │ UI Layer   │   │ Repository │             │
│  └─────┬──────┘   └─────┬──────┘             │
│        │                │                    │
│        └────────┬───────┘                    │
│                 │                            │
│        ┌────────▼────────┐                   │
│        │ Retrofit / API  │                   │
│        └────────┬────────┘                   │
│                 │                            │
│        ┌────────▼────────┐                   │
│        │ Local Room DB   │                   │
│        └─────────────────┘                   │
└─────────────────┬─────────────────────────────┘
                  │
                  │ REST API
                  │
        ┌─────────▼──────────┐
        │  Spring Boot API   │
        │      Backend       │
        └─────────┬──────────┘
                  │
          ┌───────▼────────┐
          │   PostgreSQL   │
          │    Database    │
          └────────────────┘
```

---

# Offline Data and Synchronization

MedQueue includes components for handling connectivity and synchronization.

The Android application contains:

* `ConnectivityListener`
* `ConnectivityReceiver`
* `SyncManager`
* `SyncWorker`

These components provide the foundation for handling changes in network connectivity and synchronizing locally stored information with the backend.

The application also uses a local database layer containing entities such as:

* Appointments
* Consultations
* Patients
* Queue entries
* Medical history
* Notifications
* Triage information
* Users

This architecture allows the application to maintain local data while communicating with the backend when connectivity is available.

---

# Project Structure

The repository is organized into an Android application and Spring Boot backend.

```text
MedQueue/
│
├── app/
│   └── src/
│       ├── androidTest/
│       └── main/
│           ├── java/com/example/mediqueue/
│           │
│           ├── api/
│           │   ├── ApiModels.kt
│           │   ├── ApiService.java
│           │   ├── BackendConfig.java
│           │   ├── NetworkErrorHandler.java
│           │   ├── NetworkHelper.java
│           │   └── RetrofitClient.java
│           │
│           ├── core/
│           │   ├── RoleResolver.java
│           │   ├── SessionManager.java
│           │   └── UserRole.kt
│           │
│           ├── data/
│           │   ├── local/
│           │   │   ├── dao/
│           │   │   ├── entities/
│           │   │   ├── AppDatabase.java
│           │   │   └── Converters.java
│           │   │
│           │   └── repository/
│           │
│           ├── di/
│           │   ├── DatabaseModule.java
│           │   └── NetworkModule.java
│           │
│           ├── sync/
│           │   ├── ConnectivityListener.java
│           │   ├── ConnectivityReceiver.java
│           │   ├── SyncManager.java
│           │   └── SyncWorker.java
│           │
│           ├── ui/
│           ├── MainActivity.java
│           └── MediQueueApplication.java
│
├── backend/
│   └── health-checks/
│       ├── src/
│       ├── uploads/
│       ├── pom.xml
│       └── mvnw
│
├── gradle/
│   └── wrapper/
│
├── temp_designs/
├── AI_INTEGRATIONS.md
├── RECOMMENDATIONS.md
├── build.gradle
├── gradle.properties
└── gradlew
```

---

# API Architecture

The Android application communicates with the Spring Boot backend through REST APIs.

The API layer is organized around:

* Authentication
* Patient management
* Queue management
* Appointments
* Doctor operations
* Receptionist operations
* Medical records
* Administration
* File uploads

Retrofit is used on the Android side to communicate with the backend.

---

# Authentication and Authorization

MedQueue uses authenticated API communication to protect user-specific functionality.

The application includes:

* User sessions
* Role resolution
* Authentication management
* Protected API communication

Different application functionality is exposed according to the user's role.

Supported roles include:

* Patient
* Doctor
* Receptionist
* Administrator

---

# Database

The backend uses a relational database for persistent application data.

The Android application also maintains a local Room database for local data storage and synchronization.

Local entities include:

* `AppointmentEntity`
* `ConsultationEntity`
* `MedicalHistory`
* `Notification`
* `PatientEntity`
* `QueueEntity`
* `TriagePatient`
* `User`

---

# Getting Started

## Prerequisites

Install the following tools before running the project:

* Android Studio
* Java Development Kit
* Android SDK
* Gradle
* Maven
* PostgreSQL
* Git

---

## Clone the Repository

```bash
git clone https://github.com/MedQueue-Team/MedQueue.git
cd MedQueue
```

The main application is located inside:

```text
MedQueue/
```

---

# Running the Backend

Navigate to the backend directory:

```bash
cd MedQueue/backend/health-checks
```

Run the Spring Boot application using the Maven wrapper.

On Linux/macOS:

```bash
./mvnw spring-boot:run
```

On Windows:

```cmd
mvnw.cmd spring-boot:run
```

Configure the required database connection and environment-specific settings before starting the backend.

> Never commit database passwords, JWT secrets, API keys, or other sensitive credentials to the repository.

---

# Running the Android Application

1. Open the `MedQueue` project in Android Studio.
2. Allow Gradle to synchronize the project.
3. Configure the backend URL in the application configuration.
4. Make sure the backend is running.
5. Connect an Android device or start an Android emulator.
6. Build and run the application.

For Android Emulator development, the host machine is commonly accessed using:

```text
http://10.0.2.2:<PORT>
```

The exact port should match the backend configuration.

---

# Future Improvements

Potential future improvements documented for the project include:

* Real-time queue updates
* Push notifications
* Improved JWT session management
* Stronger input validation
* Telehealth/video consultation
* More advanced offline synchronization
* AI-powered triage
* Intelligent waiting-time prediction
* Medical-record summarization
* AI patient assistant

Some of these are planned enhancements rather than completed features.

---

# Screenshots

Screenshots and UI documentation can be added here as the application interface continues to be finalized.

Example:

```text
screens/
├── login.png
├── patient-dashboard.png
├── queue.png
├── appointments.png
├── doctor-dashboard.png
└── admin-dashboard.png
```

---

# Team

MedQueue is a collaborative project developed by:

### Samson Chibau Mumba

**Co-Developer**

Contributions include:

* Android application development
* Backend development
* REST API integration
* Database integration
* Authentication and authorization
* Application architecture
* AI-assisted functionality

### Ivwananji Silungwe

**Co-Developer**

Contributed to the development and implementation of the MedQueue healthcare management platform.

---

# Project Repository

GitHub:

https://github.com/MedQueue-Team/MedQueue

---

# Project Status

MedQueue is an actively developed healthcare technology project.

The system is being developed as a practical solution for improving patient flow, appointment management, queue coordination, and healthcare service delivery through software and AI-assisted technologies.

---

## Disclaimer

MedQueue is a software development and academic project.

The system is not intended to replace qualified healthcare professionals, clinical judgment, or emergency medical services. AI-assisted features should be treated as supportive tools rather than medical diagnosis systems.
