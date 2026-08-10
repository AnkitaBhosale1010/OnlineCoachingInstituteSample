package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.TeacherCourseAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.session.SessionManager;
import com.example.onlinecoachingapp.teacher.DeleteCourseDialog;
import com.example.onlinecoachingapp.teacher.EditCourseDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCoursesActivity extends AppCompatActivity
        implements TeacherCourseAdapter.OnCourseActionListener {

    private RecyclerView recyclerCourses;
    private ProgressBar progressBar;
    private LinearLayout layoutEmpty;
    private FloatingActionButton fabAddCourse;

    private TeacherCourseAdapter adapter;
    private List<Course> courseList;

    private String module;
    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        recyclerCourses = findViewById(R.id.recyclerCourses);
        progressBar = findViewById(R.id.progressBar);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        fabAddCourse = findViewById(R.id.fabAddCourse);

        recyclerCourses.setLayoutManager(
                new LinearLayoutManager(this));

        courseList = new ArrayList<>();

        adapter = new TeacherCourseAdapter(
                this,
                courseList,
                this);

        recyclerCourses.setAdapter(adapter);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        sessionManager = new SessionManager(this);

        fabAddCourse.setOnClickListener(v -> {

            startActivity(new Intent(
                    MyCoursesActivity.this,
                    CreateCourseActivity.class));

        });

        loadCourses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCourses();
    }

    private void loadCourses() {

        Long teacherId = sessionManager.getTeacherId();

        if (teacherId == null)
            return;

        progressBar.setVisibility(View.VISIBLE);

        apiService.getTeacherCourses(teacherId)
                .enqueue(new Callback<List<Course>>() {

                    @Override
                    public void onResponse(Call<List<Course>> call,
                                           Response<List<Course>> response) {

                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful()
                                && response.body() != null) {

                            courseList.clear();

                            courseList.addAll(response.body());

                            adapter.notifyDataSetChanged();

                            if (courseList.isEmpty()) {

                                layoutEmpty.setVisibility(View.VISIBLE);

                            } else {

                                layoutEmpty.setVisibility(View.GONE);

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<List<Course>> call,
                                          Throwable t) {

                        progressBar.setVisibility(View.GONE);

                    }
                });

    }

    @Override
    public void onView(Course course) {

        Toast.makeText(
                this,
                "Selected Course ID : " + course.getCourseId(),
                Toast.LENGTH_LONG
        ).show();

        Intent intent = new Intent(
                this,
                CourseDetailActivity.class);

        intent.putExtra(
                "courseId",
                course.getCourseId());

        startActivity(intent);

    }

    @Override
    public void onEdit(Course course) {

        EditCourseDialog.show(
                this,
                course,
                apiService,
                () -> loadCourses());

    }

    @Override
    public void onDelete(Course course) {

        DeleteCourseDialog.show(
                this,
                apiService,
                course.getCourseId(),
                () -> loadCourses());

    }

}