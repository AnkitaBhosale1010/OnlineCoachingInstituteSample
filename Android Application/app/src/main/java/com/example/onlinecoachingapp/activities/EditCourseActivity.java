package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Course;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCourseActivity extends AppCompatActivity {

    private TextInputEditText edtTitle;
    private TextInputEditText edtDescription;
    private TextInputEditText edtDuration;
    private TextInputEditText edtPrice;

    private AutoCompleteTextView spLevel;

    private MaterialButton btnUpdate;

    private ApiService apiService;

    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Course");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        edtDuration = findViewById(R.id.edtDuration);
        edtPrice = findViewById(R.id.edtPrice);
        spLevel = findViewById(R.id.spLevel);
        btnUpdate = findViewById(R.id.btnUpdate);

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

        courseId = getIntent().getLongExtra("courseId",0);

        edtTitle.setText(getIntent().getStringExtra("title"));
        edtDescription.setText(getIntent().getStringExtra("description"));
        edtDuration.setText(getIntent().getStringExtra("duration"));
        edtPrice.setText(getIntent().getStringExtra("price"));
        spLevel.setText(getIntent().getStringExtra("level"),false);

        btnUpdate.setOnClickListener(v -> updateCourse());
    }

    private void updateCourse() {

        String title = edtTitle.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String duration = edtDuration.getText().toString().trim();
        String price = edtPrice.getText().toString().trim();
        String level = spLevel.getText().toString().trim();

        if(title.isEmpty()){
            edtTitle.setError("Enter Title");
            return;
        }

        if(description.isEmpty()){
            edtDescription.setError("Enter Description");
            return;
        }

        if(duration.isEmpty()){
            edtDuration.setError("Enter Duration");
            return;
        }

        if(price.isEmpty()){
            edtPrice.setError("Enter Price");
            return;
        }

        Course course = new Course();

        course.setCourseId(courseId);
        course.setTitle(title);
        course.setDescription(description);
        course.setDuration(duration);
        course.setLevel(level);
        course.setPrice(new BigDecimal(price));

        apiService.updateCourse(courseId,course)
                .enqueue(new Callback<Course>() {

                    @Override
                    public void onResponse(Call<Course> call,
                                           Response<Course> response) {

                        if(response.isSuccessful()){

                            Toast.makeText(
                                    EditCourseActivity.this,
                                    "Course Updated Successfully",
                                    Toast.LENGTH_SHORT).show();

                            finish();

                        }else{

                            Toast.makeText(
                                    EditCourseActivity.this,
                                    "Update Failed",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Course> call,
                                          Throwable t) {

                        Toast.makeText(
                                EditCourseActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}