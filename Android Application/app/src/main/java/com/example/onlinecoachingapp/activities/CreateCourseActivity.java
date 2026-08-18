package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.model.CourseRequest;
import com.example.onlinecoachingapp.session.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCourseActivity extends AppCompatActivity {

    private TextInputEditText edtTitle, edtDescription, edtDuration, edtPrice;
    private AutoCompleteTextView spLevel;
    private MaterialButton btnCreateCourse;
    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create Course");
        }

        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        edtDuration = findViewById(R.id.edtDuration);
        edtPrice = findViewById(R.id.edtPrice);

        spLevel = findViewById(R.id.spLevel);
        btnCreateCourse = findViewById(R.id.btnCreateCourse);

        sessionManager = new SessionManager(this);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        String[] levels = {
                "Beginner",
                "Intermediate",
                "Advanced"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        levels);

        spLevel.setAdapter(adapter);

        btnCreateCourse.setOnClickListener(v -> validateAndCreate());
    }

    private void validateAndCreate() {

        String title = edtTitle.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String duration = edtDuration.getText().toString().trim();
        String price = edtPrice.getText().toString().trim();
        String level = spLevel.getText().toString().trim();

        if(title.isEmpty()){
            edtTitle.setError("Enter title");
            return;
        }

        if(description.isEmpty()){
            edtDescription.setError("Enter description");
            return;
        }

        if(duration.isEmpty()){
            edtDuration.setError("Enter duration");
            return;
        }

        if(price.isEmpty()){
            edtPrice.setError("Enter fees");
            return;
        }

        if(level.isEmpty()){
            Toast.makeText(this,"Select level",Toast.LENGTH_SHORT).show();
            return;
        }

        CourseRequest request = new CourseRequest();

        request.setTitle(title);
        request.setDescription(description);
        request.setDuration(duration);
        request.setPrice(new BigDecimal(price));
        request.setLevel(level);

        // Teacher ID from session
        request.setTeacherId(sessionManager.getTeacherId());

        apiService.createCourse(sessionManager.getTeacherId(),request)
                .enqueue(new Callback<ApiResponse<Course>>() {

                    @Override
                    public void onResponse(Call<ApiResponse<Course>> call,
                                           Response<ApiResponse<Course>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            Toast.makeText(CreateCourseActivity.this,
                                    "Course Created Successfully",
                                    Toast.LENGTH_SHORT).show();

                            finish();

                        } else {

                            Toast.makeText(CreateCourseActivity.this,
                                    "Course Creation Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Course>> call,
                                          Throwable t) {

                        Toast.makeText(CreateCourseActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

    }

}