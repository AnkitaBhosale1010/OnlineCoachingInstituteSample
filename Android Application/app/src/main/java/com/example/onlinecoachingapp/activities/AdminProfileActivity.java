package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.User;
import com.example.onlinecoachingapp.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProfileActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;

    private ApiService apiService;
    private SessionManager sessionManager;

    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);

        sessionManager = new SessionManager(this);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        userId = sessionManager.getUserId();

        loadProfile();
    }

    private void loadProfile() {

        if (userId == null || userId == 0) {

            loadSessionData();

            return;
        }

        apiService.getUserById(userId)
                .enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(
                            Call<User> call,
                            Response<User> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            User user = response.body();

                            etName.setText(
                                    user.getName()
                            );

                            etEmail.setText(
                                    user.getEmail()
                            );

                        } else {

                            loadSessionData();

                            Toast.makeText(
                                    AdminProfileActivity.this,
                                    "Unable to Load Profile",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<User> call,
                            Throwable t) {

                        loadSessionData();

                        Toast.makeText(
                                AdminProfileActivity.this,
                                "Using Saved Profile Information",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void loadSessionData() {

        etName.setText(
                sessionManager.getName()
        );

        etEmail.setText(
                sessionManager.getEmail()
        );
    }
}