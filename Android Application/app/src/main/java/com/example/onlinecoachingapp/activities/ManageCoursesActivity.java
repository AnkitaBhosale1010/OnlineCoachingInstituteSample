package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.AdminCourseAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Course;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageCoursesActivity extends AppCompatActivity
        implements AdminCourseAdapter.OnCourseActionListener {

    private RecyclerView recyclerCourses;

    private AdminCourseAdapter adapter;

    private List<Course> courseList;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courses);

        recyclerCourses = findViewById(R.id.recyclerCourses);

        recyclerCourses.setLayoutManager(
                new LinearLayoutManager(this));

        courseList = new ArrayList<>();

        adapter = new AdminCourseAdapter(
                this,
                courseList,
                this);

        recyclerCourses.setAdapter(adapter);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        loadCourses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCourses();
    }

    private void loadCourses() {

        apiService.getAllCourses()
                .enqueue(new Callback<ApiResponse<List<Course>>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<ApiResponse<List<Course>>> call,
                            @NonNull Response<ApiResponse<List<Course>>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getData() != null) {

                            courseList.clear();

                            courseList.addAll(
                                    response.body().getData());

                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    ManageCoursesActivity.this,
                                    "Unable to load courses",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<ApiResponse<List<Course>>> call,
                            @NonNull Throwable t) {

                        Toast.makeText(
                                ManageCoursesActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

    @Override
    public void onEdit(Course course) {

        Intent intent = new Intent(
                this,
                EditCourseActivity.class);

        intent.putExtra("course", course);

        startActivity(intent);

    }

    @Override
    public void onDelete(Course course) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Course")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",
                        (dialog, which) -> deleteCourse(course))
                .setNegativeButton("No", null)
                .show();

    }

    private void deleteCourse(Course course) {

        apiService.deleteCourse(course.getCourseId())
                .enqueue(new Callback<ApiResponse<Void>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<Void>> call,
                            Response<ApiResponse<Void>> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    ManageCoursesActivity.this,
                                    "Course Deleted",
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadCourses();

                        } else {

                            Toast.makeText(
                                    ManageCoursesActivity.this,
                                    "Delete Failed",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<Void>> call,
                            Throwable t) {

                        Toast.makeText(
                                ManageCoursesActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }
}
