package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.model.Enrollment;
import com.example.onlinecoachingapp.session.SessionManager;
import com.google.android.material.button.MaterialButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtDescription;
    private TextView txtDuration;
    private TextView txtLevel;
    private TextView txtPrice;
    private MaterialButton btnEnroll;
    private Long courseId;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        txtDuration = findViewById(R.id.txtDuration);
        txtLevel = findViewById(R.id.txtLevel);
        txtPrice = findViewById(R.id.txtPrice);
        btnEnroll.setOnClickListener(v -> enrollCourse());

        courseId = getIntent().getLongExtra("courseId", 0);

        loadCourse();
    }

    private void enrollCourse() {
        Long studentId = sessionManager.getStudentId();

        if (studentId == 0) {

            Toast.makeText(
                    this,
                    "Student ID not found",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        btnEnroll.setEnabled(false);
        btnEnroll.setText("Enrolling...");

        ApiService api = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        api.enrollCourse(studentId, courseId)
                .enqueue(new Callback<ApiResponse<Enrollment>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<Enrollment>> call,
                            Response<ApiResponse<Enrollment>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            Toast.makeText(
                                    CourseDetailActivity.this,
                                    "Enrollment Successful",
                                    Toast.LENGTH_LONG
                            ).show();

                            btnEnroll.setText("Enrolled");
                            btnEnroll.setEnabled(false);

                        } else {

                            btnEnroll.setEnabled(true);
                            btnEnroll.setText("Enroll Now");

                            Toast.makeText(
                                    CourseDetailActivity.this,
                                    "Enrollment Failed",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    }

                    @Override
                    public void onFailure(
                            Call<   ApiResponse<Enrollment>> call,
                            Throwable t) {

                        btnEnroll.setEnabled(true);
                        btnEnroll.setText("Enroll Now");

                        Toast.makeText(
                                CourseDetailActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }

                });
    }

    private void loadCourse() {

        ApiService api = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        api.getCourse(courseId)
                .enqueue(new Callback<Course>() {

                    @Override
                    public void onResponse(@NonNull Call<Course> call,
                                           @NonNull Response<Course> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            Course course = response.body();

                            txtTitle.setText(course.getTitle());
                            txtDescription.setText(course.getDescription());
                            txtDuration.setText("Duration : " + course.getDuration());
                            txtLevel.setText("Level : " + course.getLevel());
                            txtPrice.setText("₹ " + course.getPrice());

                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<Course> call,
                                          @NonNull Throwable t) {

                    }

                });

    }
}