package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Course;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCourseActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private EditText etDuration;
    private EditText etLevel;
    private EditText etPrice;

    private Button btnUpdateCourse;

    private ApiService apiService;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDuration = findViewById(R.id.etDuration);
        etLevel = findViewById(R.id.etLevel);
        etPrice = findViewById(R.id.etPrice);

        btnUpdateCourse = findViewById(R.id.btnUpdateCourse);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        course = (Course) getIntent().getSerializableExtra("course");

        if (course == null) {

            Toast.makeText(
                    this,
                    "Course Not Found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        loadData();

        btnUpdateCourse.setOnClickListener(v -> updateCourse());
    }

    private void loadData() {

        etTitle.setText(course.getTitle());
        etDescription.setText(course.getDescription());
        etDuration.setText(course.getDuration());
        etLevel.setText(course.getLevel());

        if (course.getPrice() != null) {
            etPrice.setText(course.getPrice().toPlainString());
        }
    }

    private void updateCourse() {

        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String duration = etDuration.getText().toString().trim();
        String level = etLevel.getText().toString().trim();
        String price = etPrice.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Required");
            return;
        }

        if (description.isEmpty()) {
            etDescription.setError("Required");
            return;
        }

        if (duration.isEmpty()) {
            etDuration.setError("Required");
            return;
        }

        if (level.isEmpty()) {
            etLevel.setError("Required");
            return;
        }

        if (price.isEmpty()) {
            etPrice.setError("Required");
            return;
        }

        course.setTitle(title);
        course.setDescription(description);
        course.setDuration(duration);
        course.setLevel(level);
        course.setPrice(new BigDecimal(price));

        apiService.updateCourse(
                course.getCourseId(),
                course
        ).enqueue(new Callback<Course>() {

            @Override
            public void onResponse(Call<Course> call,
                                   Response<Course> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            EditCourseActivity.this,
                            "Course Updated Successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();

                } else {

                    Toast.makeText(
                            EditCourseActivity.this,
                            "Update Failed",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<Course> call,
                                  Throwable t) {

                Toast.makeText(
                        EditCourseActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}