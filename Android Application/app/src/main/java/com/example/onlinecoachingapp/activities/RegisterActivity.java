package com.example.onlinecoachingapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.RegisterRequest;
import com.google.android.material.button.MaterialButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private MaterialButton btnRegister;
    private TextView txtLogin;
    private AutoCompleteTextView spinnerRole;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Views
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);
        spinnerRole = findViewById(R.id.spinnerRole);

        String[] roles = {
                "STUDENT",
                "TEACHER",
                "ADMIN"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                roles
        );

        spinnerRole.setAdapter(adapter);

        // Initialize Retrofit
        apiService = ApiClient.getRetrofitInstance(this)
                .create(ApiService.class);

        // Register Button
        btnRegister.setOnClickListener(v -> registerUser());

        // Login Text
        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this,
                    LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Always register as STUDENT
        //        String role = "STUDENT";
        // for selection role
        String role = spinnerRole.getText().toString().trim();

        // Validation
        if (name.isEmpty()) {
            etName.setError("Enter Name");
            etName.requestFocus();
            return;
        }

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

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        if (role.isEmpty()) {
            spinnerRole.setError("Please select a role");
            spinnerRole.requestFocus();
            return;
        }

        RegisterRequest request = new RegisterRequest(
                        name,
                        email,
                        password,
                        role
                );

        Call<ApiResponse<String>> call = apiService.register(request);

        call.enqueue(new Callback<ApiResponse<String>>() {

            @Override
            public void onResponse(Call<ApiResponse<String>> call,
                                   Response<ApiResponse<String>> response) {

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().isSuccess()) {

                    Toast.makeText(RegisterActivity.this,
                            response.body().getMessage(),
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegisterActivity.this,
                            LoginActivity.class);

                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(RegisterActivity.this,
                            "Registration Failed",
                            Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call,
                                  Throwable t) {

                Toast.makeText(RegisterActivity.this,
                        "Error : " + t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }

        });


    }
}