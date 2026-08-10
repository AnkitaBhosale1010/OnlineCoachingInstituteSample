package com.example.onlinecoachingapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.session.SessionManager;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCourseActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etDuration, etLevel, etPrice;
    private Button btnCreate;

    private ApiService apiService;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        initViews();

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        sessionManager = new SessionManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Course...");
        progressDialog.setCancelable(false);

        btnCreate.setOnClickListener(v -> createCourse());
    }

    private void initViews() {

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDuration = findViewById(R.id.etDuration);
        etLevel = findViewById(R.id.etLevel);
        etPrice = findViewById(R.id.etPrice);

        btnCreate = findViewById(R.id.btnCreate);

    }

    private void createCourse() {

        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String duration = etDuration.getText().toString().trim();
        String level = etLevel.getText().toString().trim();
        String price = etPrice.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Enter course title");
            etTitle.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            etDescription.setError("Enter description");
            etDescription.requestFocus();
            return;
        }

        if (duration.isEmpty()) {
            etDuration.setError("Enter duration");
            etDuration.requestFocus();
            return;
        }

        if (level.isEmpty()) {
            etLevel.setError("Enter level");
            etLevel.requestFocus();
            return;
        }

        if (price.isEmpty()) {
            etPrice.setError("Enter price");
            etPrice.requestFocus();
            return;
        }

        Long teacherId = sessionManager.getTeacherId();

        if (teacherId == null || teacherId <= 0) {
            Toast.makeText(this, "Teacher not logged in", Toast.LENGTH_LONG).show();
            return;
        }

        Course course = new Course();

        course.setTitle(title);
        course.setDescription(description);
        course.setDuration(duration);
        course.setLevel(level);
        course.setPrice(new BigDecimal(price));

        progressDialog.show();

        apiService.createCourse(teacherId, course)
                .enqueue(new Callback<ApiResponse<Course>>() {

                    @Override
                    public void onResponse(Call<ApiResponse<Course>> call,
                                           Response<ApiResponse<Course>> response) {

                        progressDialog.dismiss();

                        if (response.isSuccessful() && response.body() != null) {

                            Toast.makeText(CreateCourseActivity.this,
                                    response.body().getMessage(),
                                    Toast.LENGTH_LONG).show();

                            clearFields();

                            finish();

                        } else {

                            Toast.makeText(CreateCourseActivity.this,
                                    "Unable to create course.\nError Code : "
                                            + response.code(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Course>> call,
                                          Throwable t) {

                        progressDialog.dismiss();

                        Toast.makeText(CreateCourseActivity.this,
                                "Network Error : " + t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void clearFields() {

        etTitle.setText("");
        etDescription.setText("");
        etDuration.setText("");
        etLevel.setText("");
        etPrice.setText("");

    }

}