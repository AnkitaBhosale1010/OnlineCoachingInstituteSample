package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.LectureAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Lecture;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LectureActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private LectureAdapter adapter;
    private List<Lecture> lectureList;

    private ApiService apiService;

    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        recyclerView = findViewById(R.id.recyclerLecture);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        lectureList = new ArrayList<>();

        adapter = new LectureAdapter(this, lectureList);

        recyclerView.setAdapter(adapter);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        courseId = getIntent().getLongExtra(
                "courseId",
                0);

        loadLectures();
    }

    private void loadLectures() {

        progressBar.setVisibility(View.VISIBLE);

        apiService.getCourseLectures(courseId)
                .enqueue(new Callback<ApiResponse<List<Lecture>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<Lecture>>> call,
                            Response<ApiResponse<List<Lecture>>> response) {

                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            lectureList.clear();

                            lectureList.addAll(
                                    response.body().getData());

                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    LectureActivity.this,
                                    "No Lectures Found",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }
                    }


                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<Lecture>>> call,
                            Throwable t) {

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(
                                LectureActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}