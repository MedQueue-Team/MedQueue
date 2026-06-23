package com.clinical.manager.health.checks.Data.Model;



/*
 * Login response from backend.
 */
public class LoginResponse {

    private boolean success;

    private String token;

    private String role;

    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
