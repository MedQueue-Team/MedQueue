package com.example.mediqueue.data.repository;

import com.example.mediqueue.api.ApiModels;
import com.example.mediqueue.api.ApiService;
import com.example.mediqueue.data.local.dao.UserDao;
import com.example.mediqueue.data.local.entity.User;
import com.example.mediqueue.data.local.AppDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class AuthRepository extends BaseRepository {

    private final UserDao userDao;
    private final ApiService apiService;

    @Inject
    public AuthRepository(UserDao userDao, ApiService apiService) {
        this.userDao = userDao;
        this.apiService = apiService;
    }

    public interface AuthCallback {
        void onSuccess(ApiModels.LoginResponse response);
        void onFailure(String error);
    }

    public void login(String email, String password, AuthCallback callback) {
        ApiModels.LoginRequest request = new ApiModels.LoginRequest(email, password);

        apiService.login(request).enqueue(new Callback<ApiModels.ApiResponse<ApiModels.LoginResponse>>() {
            @Override
            public void onResponse(Call<ApiModels.ApiResponse<ApiModels.LoginResponse>> call, Response<ApiModels.ApiResponse<ApiModels.LoginResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiModels.ApiResponse<ApiModels.LoginResponse> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        checkLocalLogin(email, password, callback, apiResponse.getMessage());
                    }
                } else {
                    checkLocalLogin(email, password, callback, "Network failed (" + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<ApiModels.ApiResponse<ApiModels.LoginResponse>> call, Throwable t) {
                checkLocalLogin(email, password, callback, "Connection error: " + t.getMessage());
            }
        });
    }

    private void checkLocalLogin(String email, String password, AuthCallback callback, String originalError) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User localUser = userDao.login(email, password);
            if (localUser != null) {
                ApiModels.LoginResponse response = new ApiModels.LoginResponse();
                response.token = "local_token_" + System.currentTimeMillis();
                response.role = localUser.getRole().name();
                response.message = "Logged in locally via Room";
                response.success = true;
                callback.onSuccess(response);
            } else {
                callback.onFailure(originalError);
            }
        });
    }

    public void register(String email, String password, AuthCallback callback) {
        ApiModels.LoginRequest request = new ApiModels.LoginRequest(email, password);

        apiService.register(request).enqueue(new Callback<ApiModels.ApiResponse<ApiModels.LoginResponse>>() {
            @Override
            public void onResponse(Call<ApiModels.ApiResponse<ApiModels.LoginResponse>> call, Response<ApiModels.ApiResponse<ApiModels.LoginResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiModels.ApiResponse<ApiModels.LoginResponse> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        callback.onFailure(apiResponse.getMessage() != null ? apiResponse.getMessage() : "Registration failed");
                    }
                } else {
                    callback.onFailure("Registration failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiModels.ApiResponse<ApiModels.LoginResponse>> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }
}
