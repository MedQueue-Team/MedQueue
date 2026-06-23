package com.example.mediqueue.core;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class SessionManager {
    private static final String PREF_NAME = "MediQueuePrefs";
    private static final String KEY_USER_ROLE = "user_role";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_TOKEN = "auth_token";
    private static final String KEY_LAST_STAFF_ROLE = "last_staff_role";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    @Inject
    public SessionManager(@ApplicationContext Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String token, UserRole role) {
        editor.putString(KEY_USER_TOKEN, token);
        editor.putString(KEY_USER_ROLE, role.name());
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public UserRole getUserRole() {
        String roleName = sharedPreferences.getString(KEY_USER_ROLE, UserRole.UNKNOWN.name());
        try {
            return UserRole.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            return UserRole.UNKNOWN;
        }
    }

    public void saveLastStaffRole(UserRole role) {
        editor.putString(KEY_LAST_STAFF_ROLE, role.name());
        editor.apply();
    }

    public UserRole getLastStaffRole() {
        String roleName = sharedPreferences.getString(KEY_LAST_STAFF_ROLE, UserRole.UNKNOWN.name());
        try {
            return UserRole.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            return UserRole.UNKNOWN;
        }
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getAuthToken() {
        return sharedPreferences.getString(KEY_USER_TOKEN, null);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
