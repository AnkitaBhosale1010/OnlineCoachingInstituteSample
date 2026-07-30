package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.AuthResponse;
import com.example.onlinecoachingapp.model.LoginRequest;
import com.example.onlinecoachingapp.session.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail,etPassword;
    private MaterialButton btnLogin;
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

          TextView txtRegister = findViewById(R.id.txtRegister);

        // Open Register Screen
        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Initialize Views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        sessionManager = new SessionManager(this);
        Log.e("sessionManager: ", String.valueOf(sessionManager));
        // Already logged in
//        if (sessionManager.isLoggedIn()) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }

//        apiService = ApiClient
//                .getRetrofitInstance()
//                .create(ApiService.class);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        btnLogin.setOnClickListener(v -> loginUser());

        findViewById(R.id.txtRegister).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this,
                    RegisterActivity.class));
        });
    }

    private void loginUser() {

        Log.e( "loginUser: ", "loginUser" );

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Enter Email");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Invalid Email");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Enter Password");
            etPassword.requestFocus();
            return;
        }

        LoginRequest request = new LoginRequest(email, password);

        apiService.login(request).enqueue(new Callback<ApiResponse<AuthResponse>>() {

            @Override
            public void onResponse(Call<ApiResponse<AuthResponse>> call,
                                   Response<ApiResponse<AuthResponse>> response) {
                Log.e( "onResponse: ", String.valueOf(response));
                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().isSuccess()) {

                    AuthResponse auth = response.body().getData();

                    // Save Login Session
                    sessionManager.saveUser(
                            auth.getToken(),
                            auth.getName(),
                            auth.getEmail(),
                            auth.getRole()
                    );

                    Toast.makeText(LoginActivity.this,
                            "Login Successful",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(LoginActivity.this,
                            "Invalid Email or Password",
                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<AuthResponse>> call,
                                  Throwable t) {
                Log.e( "onFailure: ", String.valueOf(t));
                Toast.makeText(LoginActivity.this,
                        "Error : " + t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });
    }
}