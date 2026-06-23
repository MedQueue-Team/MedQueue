package com.example.mediqueue.api

import com.google.gson.annotations.SerializedName

object ApiModels {

    data class LoginRequest(
        @SerializedName("email") var email: String = "",
        @SerializedName("password") var password: String = ""
    )

    data class ApiResponse<T>(
        @SerializedName("success") var success: Boolean = false,
        @SerializedName("message") var message: String? = null,
        @SerializedName("data") var data: T? = null
    ) {
        fun isSuccess() = success
    }

    data class LoginResponse(
        @SerializedName("success") @JvmField var success: Boolean = false,
        @SerializedName("token") @JvmField var token: String? = null,
        @SerializedName("role") @JvmField var role: String? = null,
        @SerializedName("message") @JvmField var message: String? = null
    ) {
        fun isSuccess() = success
    }

    data class HealthCheckResponse(
        @SerializedName("status") var status: String? = null,
        @SerializedName("timestamp") var timestamp: String? = null
    )

    data class ApiErrorResponse(
        @SerializedName("message") var message: String? = null,
        @SerializedName("status") var status: Int = 0
    )

    data class BookAppointmentRequest(
        @SerializedName("patientId") var patientId: String = "",
        @SerializedName("doctorId") var doctorId: String = "",
        @SerializedName("date") var date: String = "",
        @SerializedName("time") var time: String = ""
    )

    data class SyncPatientRequest(
        @SerializedName("patientId") var patientId: String = "",
        @SerializedName("fullName") var fullName: String = "",
        @SerializedName("email") var email: String = "",
        @SerializedName("dateOfBirth") var dateOfBirth: String = "",
        @SerializedName("phoneNumber") var phoneNumber: String = "",
        @SerializedName("lastUpdated") var lastUpdated: Long = 0
    )

    data class SyncAppointmentRequest(
        @SerializedName("appointmentId") var appointmentId: String = "",
        @SerializedName("patientId") var patientId: String = "",
        @SerializedName("doctorId") var doctorId: String = "",
        @SerializedName("doctorName") var doctorName: String = "",
        @SerializedName("department") var department: String = "",
        @SerializedName("appointmentDate") var appointmentDate: String = "",
        @SerializedName("appointmentTime") var appointmentTime: String = "",
        @SerializedName("status") var status: String = "",
        @SerializedName("lastUpdated") var lastUpdated: Long = 0
    )

    data class SyncQueueRequest(
        @SerializedName("queueId") var queueId: String = "",
        @SerializedName("patientId") var patientId: String = "",
        @SerializedName("department") var department: String = "",
        @SerializedName("position") var position: Int = 0,
        @SerializedName("estimatedWaitTimeMins") var estimatedWaitTimeMins: Int = 0,
        @SerializedName("status") var status: String = "",
        @SerializedName("lastUpdated") var lastUpdated: Long = 0
    )

    data class DoctorQueueItem(
        @SerializedName("queueId") var queueId: Long = 0,
        @SerializedName("queueNumber") var queueNumber: String = "",
        @SerializedName("patientName") var patientName: String = "",
        @SerializedName("age") var age: Int = 0,
        @SerializedName("gender") var gender: String = "",
        @SerializedName("symptoms") var symptoms: String = "",
        @SerializedName("department") var department: String = "",
        @SerializedName("priorityLevel") var priorityLevel: String = "",
        @SerializedName("status") var status: String = ""
    )
}
