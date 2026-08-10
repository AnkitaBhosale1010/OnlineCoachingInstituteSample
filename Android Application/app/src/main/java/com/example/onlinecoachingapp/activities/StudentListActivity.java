package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.EnrolledStudentAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListActivity extends AppCompatActivity {

    private RecyclerView recyclerStudents;

    private EnrolledStudentAdapter adapter;

    private List<Student> studentList;

    private ApiService apiService;

    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_list);

        recyclerStudents =
                findViewById(R.id.recyclerStudents);

        recyclerStudents.setLayoutManager(
                new LinearLayoutManager(this)
        );

        studentList = new ArrayList<>();

        adapter =
                new EnrolledStudentAdapter(
                        this,
                        studentList
                );

        recyclerStudents.setAdapter(adapter);

        apiService =
                ApiClient
                        .getRetrofitInstance(this)
                        .create(ApiService.class);

        courseId =
                getIntent()
                        .getLongExtra(
                                "courseId",
                                -1
                        );

        if (courseId == -1) {

            Toast.makeText(
                    this,
                    "Invalid Course",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        loadStudents();
    }

    @Override
    protected void onResume() {

        super.onResume();

        loadStudents();
    }

    private void loadStudents() {

        apiService
                .getCourseStudents(courseId)
                .enqueue(new Callback<List<Student>>() {

                    @Override
                    public void onResponse(
                            Call<List<Student>> call,
                            Response<List<Student>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            studentList.clear();

                            studentList.addAll(
                                    response.body()
                            );

                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    StudentListActivity.this,
                                    "Unable to load students",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<Student>> call,
                            Throwable t) {

                        Toast.makeText(
                                StudentListActivity.this,
                                "Error: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}