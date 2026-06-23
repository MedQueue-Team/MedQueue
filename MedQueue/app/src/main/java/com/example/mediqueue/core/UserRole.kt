package com.example.mediqueue.core

enum class UserRole(val displayName: String) {
    PATIENT("Patient"),
    NURSE("Nurse"),
    DOCTOR("Doctor"),
    ADMIN("Clinic Administrator"),
    UNKNOWN("Unknown");

    companion object {
        @JvmStatic
        fun fromString(role: String?): UserRole {
            if (role == null) return UNKNOWN
            return values().find { 
                it.displayName.equals(role, ignoreCase = true) || it.name.equals(role, ignoreCase = true) 
            } ?: UNKNOWN
        }
    }
}
