package com.example.onlinecoachingapp.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "OnlineCoachingApp";

    private static final String KEY_TOKEN = "token";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE = "role";
    private static final String KEY_IS_LOGIN = "isLogin";

    private static final String KEY_USER_ID = "userId";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {

        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        editor = preferences.edit();
    }

    // Save User Details
    public void saveUser(Long userId,String token, String name, String email, String role) {

        editor.putLong(KEY_USER_ID, userId);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ROLE, role);
        editor.putBoolean(KEY_IS_LOGIN, true);

        editor.apply();
    }

    public Long getUserId() {

        return preferences.getLong(KEY_USER_ID, 0);

    }

    // Get Token
    public String getToken() {

        return preferences.getString(KEY_TOKEN, "");

    }

    // Get Name
    public String getName() {

        return preferences.getString(KEY_NAME, "");

    }

    // Get Email
    public String getEmail() {

        return preferences.getString(KEY_EMAIL, "");

    }

    // Get Role
    public String getRole() {

        return preferences.getString(KEY_ROLE, "");

    }

    // Check Login
    public boolean isLoggedIn() {

        return preferences.getBoolean(KEY_IS_LOGIN, false);

    }

    // Logout
    public void logout() {

        editor.clear();
        editor.apply();
    }
}
