package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.BatchStudentAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchStudentsActivity extends AppCompatActivity {

    private RecyclerView recyclerStudents;

    private BatchStudentAdapter adapter;

    private List<Student> studentList;

    private ApiService apiService;

    private Long batchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_students);

        recyclerStudents = findViewById(R.id.recyclerStudents);

        recyclerStudents.setLayoutManager(
                new LinearLayoutManager(this)
        );

        studentList = new ArrayList<>();

        adapter = new BatchStudentAdapter(
                this,
                studentList
        );

        recyclerStudents.setAdapter(adapter);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        batchId = getIntent().getLongExtra(
                "batchId",
                -1
        );

        if(batchId != -1){
            loadStudents();
        }
    }


    private void loadStudents(){

        apiService.getBatchStudents(batchId)
                .enqueue(new Callback<List<Student>>() {

                    @Override
                    public void onResponse(
                            Call<List<Student>> call,
                            Response<List<Student>> response) {

                        if(response.isSuccessful()
                                && response.body()!=null){

                            studentList.clear();

                            studentList.addAll(
                                    response.body()
                            );

                            adapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<Student>> call,
                            Throwable t) {

                        Toast.makeText(
                                BatchStudentsActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }
                });
    }
}