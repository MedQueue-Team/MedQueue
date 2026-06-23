package com.example.mediqueue.api;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface ApiService {

    // Health check endpoint
    @GET("health")
    Call<ApiModels.ApiResponse<ApiModels.HealthCheckResponse>> getHealthStatus();

    // Authentication endpoints
    @POST("auth/login")
    Call<ApiModels.ApiResponse<ApiModels.LoginResponse>> login(@Body ApiModels.LoginRequest request);

    @POST("users/register")
    Call<ApiModels.ApiResponse<ApiModels.LoginResponse>> register(@Body ApiModels.LoginRequest request);

    // Appointments endpoint (Token is automatically added by AuthInterceptor)
    @GET("appointments")
    Call<ApiModels.ApiResponse<java.util.List<ApiModels.DoctorQueueItem>>> getAppointments();

    // Sync endpoints
    @POST("sync/patients")
    Call<ApiModels.ApiResponse<Void>> syncPatients(@Body List<ApiModels.SyncPatientRequest> requests);

    @POST("sync/appointments")
    Call<ApiModels.ApiResponse<Void>> syncAppointments(@Body List<ApiModels.SyncAppointmentRequest> requests);

    @POST("sync/queue")
    Call<ApiModels.ApiResponse<Void>> syncQueue(@Body List<ApiModels.SyncQueueRequest> requests);
}

