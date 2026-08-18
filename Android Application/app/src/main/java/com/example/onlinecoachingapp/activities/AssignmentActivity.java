package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.AssignmentAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Assignment;
import com.example.onlinecoachingapp.model.Submission;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import com.example.onlinecoachingapp.session.SessionManager;
import com.example.onlinecoachingapp.utils.FileUtils;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentActivity extends AppCompatActivity {

    private RecyclerView recyclerAssignments;
    private AssignmentAdapter adapter;
    private ApiService apiService;
    private Long courseId;
    private Uri selectedFileUri;

    private SessionManager sessionManager;
    private Long studentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        SessionManager sessionManager = new SessionManager(this);
        Long studentId = sessionManager.getStudentId();

        recyclerAssignments =
                findViewById(R.id.recyclerAssignments);
        recyclerAssignments.setLayoutManager(new LinearLayoutManager(this));

        courseId = getIntent().getLongExtra("courseId", 0L);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        loadAssignments();
    }

    private void loadAssignments() {

        apiService.getAssignments(courseId)
                .enqueue(new Callback<List<Assignment>>() {

                    @Override
                    public void onResponse(
                            Call<List<Assignment>> call,
                            Response<List<Assignment>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            adapter = new AssignmentAdapter(
                                    AssignmentActivity.this,
                                    response.body()
                            );

                            recyclerAssignments.setAdapter(adapter);

                        } else {

                            Toast.makeText(
                                    AssignmentActivity.this,
                                    "Assignments not found",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<Assignment>> call,
                            Throwable t) {

                        Toast.makeText(
                                AssignmentActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                });

    }

    /*
     * Modern File Picker
     */

    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK
                                && result.getData() != null) {

                            selectedFileUri =
                                    result.getData().getData();

                            if (selectedFileUri != null) {

                                uploadAssignment();

                            }

                        }

                    });

    private void uploadAssignment() {

        if (selectedFileUri == null) {

            Toast.makeText(
                    this,
                    "Please select a file",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        Long assignmentId = adapter.getSelectedAssignmentId();

        if (assignmentId == null) {

            Toast.makeText(
                    this,
                    "Assignment not selected",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        File file = FileUtils.getFile(this, selectedFileUri);

        if (file == null) {

            Toast.makeText(
                    this,
                    "Unable to read file",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        RequestBody requestFile =
                RequestBody.create(
                        file,
                        MediaType.parse("*/*"));

        MultipartBody.Part body =
                MultipartBody.Part.createFormData(
                        "file",
                        file.getName(),
                        requestFile);

        RequestBody remarks =
                RequestBody.create(
                        "Submitted from Android App",
                        MediaType.parse("text/plain"));

        apiService.submitAssignment(
                assignmentId,
                studentId,
                body,
                remarks
        ).enqueue(new Callback<Submission>() {

            @Override
            public void onResponse(
                    Call<Submission> call,
                    Response<Submission> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            AssignmentActivity.this,
                            "Assignment Uploaded Successfully",
                            Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(
                            AssignmentActivity.this,
                            "Upload Failed : " + response.code(),
                            Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(
                    Call<Submission> call,
                    Throwable t) {

                Toast.makeText(
                        AssignmentActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }

        });

    }

}