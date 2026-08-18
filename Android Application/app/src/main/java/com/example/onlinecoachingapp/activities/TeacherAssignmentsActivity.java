package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.AssignmentAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Assignment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TeacherAssignmentsActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    Button addBtn;

    List<Assignment> list = new ArrayList<>();

    AssignmentAdapter adapter;

    Long courseId;

    ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teacher_assignments);


        courseId = getIntent()
                .getLongExtra("courseId", -1);



        Log.e("TEACHER_ASSIGNMENT",
                "Received courseId : " + courseId);



        if(courseId == -1){

            Toast.makeText(
                    this,
                    "Course ID missing",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }



        recyclerView = findViewById(
                R.id.recyclerAssignments);


        addBtn = findViewById(
                R.id.btnAddAssignment);



        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);



        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));



        adapter = new AssignmentAdapter(
                this,
                list
        );


        recyclerView.setAdapter(adapter);



        loadAssignments();



        addBtn.setOnClickListener(v -> {


            Log.e("TEACHER_ASSIGNMENT",
                    "Opening Create Assignment");


            Intent intent =
                    new Intent(
                            TeacherAssignmentsActivity.this,
                            CreateAssignmentActivity.class
                    );


            intent.putExtra(
                    "courseId",
                    courseId
            );


            startActivity(intent);


        });


    }



    private void loadAssignments(){


        apiService.getAssignments(courseId)

                .enqueue(new Callback<List<Assignment>>() {


                    @Override
                    public void onResponse(
                            Call<List<Assignment>> call,
                            Response<List<Assignment>> response) {


                        if(response.isSuccessful()
                                && response.body()!=null){


                            list.clear();

                            list.addAll(response.body());


                            adapter.notifyDataSetChanged();

                        }


                    }


                    @Override
                    public void onFailure(
                            Call<List<Assignment>> call,
                            Throwable t) {


                        Log.e("ASSIGNMENT_ERROR",
                                t.getMessage());

                    }

                });


    }

}