package com.example.mediqueue.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mediqueue.api.NetworkErrorHandler;
import com.example.mediqueue.api.ApiModels;
import com.example.mediqueue.data.repository.AuthRepository;
import com.example.mediqueue.ui.base.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * AuthViewModel using Hilt and BaseViewModel patterns
 */
@HiltViewModel
public class AuthViewModel extends BaseViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<ApiModels.LoginResponse> loginResponse = new MutableLiveData<>();
    private final MutableLiveData<ApiModels.LoginResponse> registerResponse = new MutableLiveData<>();

    @Inject
    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<ApiModels.LoginResponse> getLoginResponse() {
        return loginResponse;
    }

    public LiveData<ApiModels.LoginResponse> getRegisterResponse() {
        return registerResponse;
    }

    public void login(String email, String password) {
        setLoading(true);
        authRepository.login(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(ApiModels.LoginResponse response) {
                setLoading(false);
                loginResponse.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                setLoading(false);
                setError(error);
            }
        });
    }

    public void register(String email, String password) {
        setLoading(true);
        authRepository.register(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(ApiModels.LoginResponse response) {
                setLoading(false);
                registerResponse.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                setLoading(false);
                setError(error);
            }
        });
    }
}
