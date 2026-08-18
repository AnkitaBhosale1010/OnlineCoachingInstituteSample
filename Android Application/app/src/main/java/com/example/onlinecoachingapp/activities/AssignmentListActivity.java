package com.example.onlinecoachingapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.TeacherAssignmentAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Assignment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentListActivity extends AppCompatActivity
        implements TeacherAssignmentAdapter.OnAssignmentActionListener {

    private RecyclerView recyclerAssignments;
    private ImageButton btnAddAssignment;

    private TeacherAssignmentAdapter adapter;
    private List<Assignment> assignmentList;

    private ApiService apiService;

    private ProgressDialog progressDialog;

    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_list);

        recyclerAssignments = findViewById(R.id.recyclerAssignments);
        btnAddAssignment = findViewById(R.id.btnAddAssignment);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Assignments...");
        progressDialog.setCancelable(false);

        assignmentList = new ArrayList<>();

        adapter = new TeacherAssignmentAdapter(
                this,
                assignmentList,
                this
        );

        recyclerAssignments.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerAssignments.setAdapter(adapter);

        courseId = getIntent().getLongExtra("courseId", -1);

        if (courseId == -1) {

            Toast.makeText(
                    this,
                    "Invalid Course",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        btnAddAssignment.setOnClickListener(v -> {

            Intent intent = new Intent(
                    AssignmentListActivity.this,
                    CreateAssignmentActivity.class
            );

            intent.putExtra("courseId", courseId);

            startActivity(intent);

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAssignments();
    }

    private void loadAssignments() {

        progressDialog.show();

        apiService.getAssignments(courseId)
                .enqueue(new Callback<List<Assignment>>() {

                    @Override
                    public void onResponse(
                            Call<List<Assignment>> call,
                            Response<List<Assignment>> response) {

                        progressDialog.dismiss();

                        if (response.isSuccessful()
                                && response.body() != null) {

                            assignmentList.clear();

                            assignmentList.addAll(response.body());

                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    AssignmentListActivity.this,
                                    "Unable to load assignments",
                                    Toast.LENGTH_LONG
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<Assignment>> call,
                            Throwable t) {

                        progressDialog.dismiss();

                        Toast.makeText(
                                AssignmentListActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }

    @Override
    public void onEdit(Assignment assignment) {

        Intent intent = new Intent(
                this,
                EditAssignmentActivity.class
        );

        intent.putExtra("assignment", assignment);

        startActivity(intent);

    }

    @Override
    public void onDelete(Assignment assignment) {

        progressDialog.setMessage("Deleting...");

        progressDialog.show();

        apiService.deleteAssignment(
                assignment.getAssignmentId()
        ).enqueue(new Callback<String>() {

            @Override
            public void onResponse(
                    Call<String> call,
                    Response<String> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    Toast.makeText(
                            AssignmentListActivity.this,
                            "Assignment Deleted",
                            Toast.LENGTH_SHORT
                    ).show();

                    loadAssignments();

                } else {

                    Toast.makeText(
                            AssignmentListActivity.this,
                            "Delete Failed",
                            Toast.LENGTH_SHORT
                    ).show();

                }

            }

            @Override
            public void onFailure(
                    Call<String> call,
                    Throwable t) {

                progressDialog.dismiss();

                Toast.makeText(
                        AssignmentListActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

    }

}