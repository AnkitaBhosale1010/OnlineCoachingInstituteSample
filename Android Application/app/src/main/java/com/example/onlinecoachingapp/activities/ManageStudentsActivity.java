package com.example.onlinecoachingapp.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.AdminStudentAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageStudentsActivity extends AppCompatActivity {

    private RecyclerView recyclerStudents;

    private AdminStudentAdapter adapter;

    private List<Student> studentList;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);

        recyclerStudents = findViewById(R.id.recyclerStudents);

        recyclerStudents.setLayoutManager(
                new LinearLayoutManager(this)
        );

        studentList = new ArrayList<>();

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        adapter = new AdminStudentAdapter(
                this,
                studentList,
                student -> showDeleteDialog(student)
        );

        recyclerStudents.setAdapter(adapter);

        loadStudents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudents();
    }

    private void loadStudents() {

        apiService.getAllStudents()
                .enqueue(new Callback<ApiResponse<List<Student>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<Student>>> call,
                            Response<ApiResponse<List<Student>>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getData() != null) {

                            studentList.clear();

                            studentList.addAll(
                                    response.body().getData()
                            );

                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    ManageStudentsActivity.this,
                                    "Unable to load students",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<Student>>> call,
                            Throwable t) {

                        Toast.makeText(
                                ManageStudentsActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

    private void showDeleteDialog(Student student) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("Yes", (dialog, which) ->
                        deleteStudent(student.getStudentId()))
                .setNegativeButton("No", null)
                .show();

    }

    private void deleteStudent(Long studentId) {

        apiService.deleteStudent(studentId)
                .enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(
                            Call<String> call,
                            Response<String> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    ManageStudentsActivity.this,
                                    "Student Deleted Successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadStudents();

                        } else {

                            Toast.makeText(
                                    ManageStudentsActivity.this,
                                    "Delete Failed",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<String> call,
                            Throwable t) {

                        Toast.makeText(
                                ManageStudentsActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

}