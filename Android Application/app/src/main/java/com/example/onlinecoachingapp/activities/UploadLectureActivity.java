package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.model.LectureRequest;
import com.example.onlinecoachingapp.session.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadLectureActivity extends AppCompatActivity {

    private TextInputEditText edtTitle, edtDescription, edtVideoUrl, edtOrder;
    private Spinner spCourse;
    private Button btnUpload;

    private ApiService apiService;
    private SessionManager sessionManager;

    private List<Course> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_lecture);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Upload Lecture");
        }

        sessionManager = new SessionManager(this);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        edtVideoUrl = findViewById(R.id.edtVideoUrl);
        edtOrder = findViewById(R.id.edtOrder);
        spCourse = findViewById(R.id.spCourse);
        btnUpload = findViewById(R.id.btnUpload);

        loadCourses();

        btnUpload.setOnClickListener(v -> validate());
    }

    private void loadCourses() {

        Long teacherId = sessionManager.getTeacherId();

        if (teacherId == null) {
            Toast.makeText(this,
                    "Teacher ID not found. Please login again.",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        apiService.getTeacherCourses(teacherId)
                .enqueue(new Callback<ApiResponse<List<Course>>>() {

                    @Override
                    public void onResponse(Call<ApiResponse<List<Course>>> call,
                                           Response<ApiResponse<List<Course>>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            courseList.clear();
                            courseList.addAll(response.body().getData());

                            List<String> names = new ArrayList<>();

                            for (Course c : courseList) {
                                names.add(c.getTitle());
                            }

                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<>(
                                            UploadLectureActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item,
                                            names);

                            spCourse.setAdapter(adapter);

                        } else {

                            Toast.makeText(
                                    UploadLectureActivity.this,
                                    "No courses found.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<Course>>> call,
                                          Throwable t) {

                        Toast.makeText(
                                UploadLectureActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void validate() {

        if (edtTitle.getText().toString().trim().isEmpty()) {
            edtTitle.setError("Enter Lecture Title");
            return;
        }

        if (edtDescription.getText().toString().trim().isEmpty()) {
            edtDescription.setError("Enter Description");
            return;
        }

        if (edtVideoUrl.getText().toString().trim().isEmpty()) {
            edtVideoUrl.setError("Enter Video URL");
            return;
        }

        if (edtOrder.getText().toString().trim().isEmpty()) {
            edtOrder.setError("Enter Lecture Number");
            return;
        }

        if (courseList.isEmpty()) {
            Toast.makeText(this,
                    "No course available",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        uploadLecture();
    }

    private void uploadLecture() {

        LectureRequest request = new LectureRequest();

        request.setTitle(edtTitle.getText().toString().trim());
        request.setDescription(edtDescription.getText().toString().trim());
        request.setVideoUrl(edtVideoUrl.getText().toString().trim());
        request.setLectureOrder(
                Integer.parseInt(
                        edtOrder.getText().toString().trim()));

        request.setCourseId(
                courseList.get(
                                spCourse.getSelectedItemPosition())
                        .getCourseId());

        apiService.uploadLecture(request)
                .enqueue(new Callback<ApiResponse<String>>() {

                    @Override
                    public void onResponse(Call<ApiResponse<String>> call,
                                           Response<ApiResponse<String>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            Toast.makeText(
                                    UploadLectureActivity.this,
                                    "Lecture Uploaded Successfully",
                                    Toast.LENGTH_LONG).show();

                            finish();

                        } else {

                            Toast.makeText(UploadLectureActivity.this,
                                    "Code : " + response.code(),
                                    Toast.LENGTH_LONG).show();

                            try {
                                if (response.errorBody() != null) {
                                    System.out.println(response.errorBody().string());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<String>> call, Throwable t) {

                        Toast.makeText(UploadLectureActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();

                        t.printStackTrace();
                    }
                });
    }
}