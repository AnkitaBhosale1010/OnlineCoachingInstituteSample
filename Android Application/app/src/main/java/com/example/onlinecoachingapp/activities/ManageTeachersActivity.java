package com.example.onlinecoachingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.TeacherAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Teacher;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageTeachersActivity extends AppCompatActivity
        implements TeacherAdapter.OnTeacherActionListener {

    private RecyclerView recyclerTeachers;

    private TeacherAdapter adapter;

    private List<Teacher> teacherList;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_teachers);

        recyclerTeachers = findViewById(R.id.recyclerTeachers);

        recyclerTeachers.setLayoutManager(
                new LinearLayoutManager(this)
        );

        teacherList = new ArrayList<>();

        adapter = new TeacherAdapter(
                this,
                teacherList,
                this
        );

        recyclerTeachers.setAdapter(adapter);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        loadTeachers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTeachers();
    }

    private void loadTeachers() {

        apiService.getAllTeachers()
                .enqueue(new Callback<List<Teacher>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<Teacher>> call,
                            @NonNull Response<List<Teacher>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            teacherList.clear();

                            teacherList.addAll(response.body());

                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    ManageTeachersActivity.this,
                                    "Unable to load teachers",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<Teacher>> call,
                            @NonNull Throwable t) {

                        Toast.makeText(
                                ManageTeachersActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

    @Override
    public void onEdit(Teacher teacher) {

        Intent intent = new Intent(
                this,
                EditTeacherActivity.class
        );

        intent.putExtra("teacher", teacher);

        startActivity(intent);

    }

    @Override
    public void onDelete(Teacher teacher) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Teacher")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",
                        (dialog, which) -> deleteTeacher(teacher))
                .setNegativeButton("No", null)
                .show();

    }

    private void deleteTeacher(Teacher teacher) {

        apiService.deleteTeacher(teacher.getTeacherId())
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call,
                                           Response<Void> response) {

                        if(response.isSuccessful()){

                            Toast.makeText(
                                    ManageTeachersActivity.this,
                                    "Teacher Deleted",
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadTeachers();

                        }else{

                            Toast.makeText(
                                    ManageTeachersActivity.this,
                                    "Delete Failed",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call,
                                          Throwable t) {

                        Toast.makeText(
                                ManageTeachersActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }
                });

    }

}