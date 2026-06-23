package com.example.mediqueue.api;

import com.example.mediqueue.data.local.AppDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkHelper {
    // Do not keep a static ApiService reference here; obtain it on demand so
    // changes to the RetrofitClient base URL (via setBaseUrl) take effect.

    /**
     * Login with email and password (checks backend first, falls back to Room)
     */
    public static void login(android.content.Context context, String email, String password, OnLoginListener listener) {
        ApiModels.LoginRequest request = new ApiModels.LoginRequest(email, password);
        ApiService apiService = RetrofitClient.getApiService(context);

        apiService.login(request).enqueue(new Callback<ApiModels.ApiResponse<ApiModels.LoginResponse>>() {
            @Override
            public void onResponse(Call<ApiModels.ApiResponse<ApiModels.LoginResponse>> call, Response<ApiModels.ApiResponse<ApiModels.LoginResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiModels.ApiResponse<ApiModels.LoginResponse> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        listener.onLoginSuccess(apiResponse.getData());
                    } else {
                        checkLocalLogin(context, email, password, listener, apiResponse.getMessage());
                    }
                } else {
                    checkLocalLogin(context, email, password, listener, "Network failed (" + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<ApiModels.ApiResponse<ApiModels.LoginResponse>> call, Throwable t) {
                checkLocalLogin(context, email, password, listener, "Connection error: " + t.getMessage());
            }
        });
    }

    private static void checkLocalLogin(android.content.Context context, String email, String password, OnLoginListener listener, String originalError) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            com.example.mediqueue.data.local.entity.User localUser = AppDatabase.getDatabase(context).userDao().login(email, password);
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                if (localUser != null) {
                    ApiModels.LoginResponse response = new ApiModels.LoginResponse();
                    response.token = "local_token_" + System.currentTimeMillis();
                    response.role = localUser.getRole().name();
                    response.message = "Logged in locally via Room";
                    response.success = true;
                    listener.onLoginSuccess(response);
                } else {
                    listener.onLoginFailure(originalError);
                }
            });
        });
    }

    /**
     * Check backend health status
     */
    public static void checkHealth(android.content.Context context, OnHealthCheckListener listener) {
        ApiService apiService = RetrofitClient.getApiService(context);
        apiService.getHealthStatus().enqueue(new Callback<ApiModels.ApiResponse<ApiModels.HealthCheckResponse>>() {
            @Override
            public void onResponse(Call<ApiModels.ApiResponse<ApiModels.HealthCheckResponse>> call, Response<ApiModels.ApiResponse<ApiModels.HealthCheckResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiModels.ApiResponse<ApiModels.HealthCheckResponse> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        listener.onHealthCheckSuccess(apiResponse.getData());
                    } else {
                        listener.onHealthCheckFailure(apiResponse.getMessage() != null ? apiResponse.getMessage() : "Health check failed");
                    }
                } else {
                    listener.onHealthCheckFailure("Health check failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiModels.ApiResponse<ApiModels.HealthCheckResponse>> call, Throwable t) {
                listener.onHealthCheckFailure("Error: " + t.getMessage());
            }
        });
    }

    /**
     * Register new user
     */
    public static void register(android.content.Context context, String email, String password, OnRegisterListener listener) {
        ApiModels.LoginRequest request = new ApiModels.LoginRequest(email, password);
        ApiService apiService = RetrofitClient.getApiService(context);

        apiService.register(request).enqueue(new Callback<ApiModels.ApiResponse<ApiModels.LoginResponse>>() {
            @Override
            public void onResponse(Call<ApiModels.ApiResponse<ApiModels.LoginResponse>> call, Response<ApiModels.ApiResponse<ApiModels.LoginResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiModels.ApiResponse<ApiModels.LoginResponse> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        listener.onRegisterSuccess(apiResponse.getData());
                    } else {
                        listener.onRegisterFailure(apiResponse.getMessage() != null ? apiResponse.getMessage() : "Registration failed");
                    }
                } else {
                    listener.onRegisterFailure("Registration failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiModels.ApiResponse<ApiModels.LoginResponse>> call, Throwable t) {
                listener.onRegisterFailure("Error: " + t.getMessage());
            }
        });
    }

    // ...existing code...

    // Callback interfaces
    public interface OnLoginListener {
        void onLoginSuccess(ApiModels.LoginResponse response);
        void onLoginFailure(String error);
    }

    public interface OnHealthCheckListener {
        void onHealthCheckSuccess(ApiModels.HealthCheckResponse response);
        void onHealthCheckFailure(String error);
    }

    public interface OnRegisterListener {
        void onRegisterSuccess(ApiModels.LoginResponse response);
        void onRegisterFailure(String error);
    }
}
