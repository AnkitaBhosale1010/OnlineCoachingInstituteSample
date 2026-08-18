package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.SelectCourseAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCourseActivity extends AppCompatActivity {

    private RecyclerView recyclerCourses;

    private SelectCourseAdapter adapter;

    private List<Course> courseList;

    private ApiService apiService;

    private SessionManager sessionManager;

    private String action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);


        recyclerCourses = findViewById(R.id.recyclerCourses);


        recyclerCourses.setLayoutManager(
                new LinearLayoutManager(this)
        );


        courseList = new ArrayList<>();


        action = getIntent()
                .getStringExtra("action");


        adapter = new SelectCourseAdapter(
                this,
                courseList,
                course -> openSelectedCourse(course)
        );


        recyclerCourses.setAdapter(adapter);


        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);


        sessionManager = new SessionManager(this);


        loadCourses();

    }

    private void loadCourses(){

        Long teacherId = sessionManager.getTeacherId();


        if(teacherId == null){

            Toast.makeText(
                    this,
                    "Teacher not logged in",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        apiService.getTeacherCourses(teacherId)
                .enqueue(new Callback<List<Course>>() {


                    @Override
                    public void onResponse(
                            Call<List<Course>> call,
                            Response<List<Course>> response) {


                        if(response.isSuccessful()
                                && response.body()!=null){


                            courseList.clear();


                            courseList.addAll(
                                    response.body()
                            );


                            adapter.notifyDataSetChanged();

                        }
                        else{


                            Toast.makeText(
                                    SelectCourseActivity.this,
                                    "Unable to load courses",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<Course>> call,
                            Throwable t) {

                        Toast.makeText(
                                SelectCourseActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }
                });

    }
    private void openSelectedCourse(Course course){
        Intent intent;
        if(action == null){
            Toast.makeText(
                    this,
                    "Invalid Action",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        switch(action){

            case "assignment":

                intent = new Intent(
                        this,
                        AssignmentListActivity.class
                );

                break;

            case "lecture":

                intent = new Intent(
                        this,
                        UploadLectureActivity.class
                );
                break;

            case "material":

                intent = new Intent(
                        this,
                        UploadStudyMaterialActivity.class
                );
                break;

            case "student":

                intent = new Intent(
                        this,
                        StudentListActivity.class
                );
                break;

            case "quiz":

                intent = new Intent(
                        this,
                        QuizListActivity.class
                );
                break;

            default:

                Toast.makeText(
                        this,
                        "Invalid Action",
                        Toast.LENGTH_SHORT
                ).show();

                return;
        }

        intent.putExtra(
                "courseId",
                course.getCourseId()
        );

        startActivity(intent);

    }

}