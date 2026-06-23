package com.clinical.manager.health.checks.Data.Model;



/*
 * Login request body.
 */
public class LoginRequest {

    private String email;

    private String password;

    public LoginRequest(
            String email,
            String password) {

        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}