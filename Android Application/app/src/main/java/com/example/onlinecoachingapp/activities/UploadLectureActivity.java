package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Lecture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadLectureActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etVideoUrl, etOrder;
    private Button btnUploadLecture;

    private ApiService apiService;

    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_lecture);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etVideoUrl = findViewById(R.id.etVideoUrl);
        etOrder = findViewById(R.id.etOrder);
        btnUploadLecture = findViewById(R.id.btnUploadLecture);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        courseId = getIntent().getLongExtra("courseId", -1);

        if (courseId == -1) {
            Toast.makeText(
                    this,
                    "Course not selected",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        btnUploadLecture.setOnClickListener(v -> uploadLecture());
    }

    private void uploadLecture() {

        Lecture lecture = new Lecture();

        lecture.setTitle(etTitle.getText().toString().trim());
        lecture.setDescription(etDescription.getText().toString().trim());
        lecture.setVideoUrl(etVideoUrl.getText().toString().trim());

        String order = etOrder.getText().toString().trim();

        if (order.isEmpty()) {
            etOrder.setError("Required");
            return;
        }

        lecture.setLectureOrder(Integer.parseInt(order));

        apiService.addLecture(courseId, lecture)
                .enqueue(new Callback<Lecture>() {

                    @Override
                    public void onResponse(Call<Lecture> call,
                                           Response<Lecture> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    UploadLectureActivity.this,
                                    "Lecture Uploaded",
                                    Toast.LENGTH_SHORT
                            ).show();

                            finish();

                        } else {

                            Toast.makeText(
                                    UploadLectureActivity.this,
                                    "Upload Failed",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Lecture> call,
                                          Throwable t) {

                        Toast.makeText(
                                UploadLectureActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }
                });

    }
}