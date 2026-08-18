package com.example.onlinecoachingapp.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Assignment;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAssignmentActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private EditText etDeadline;
    private EditText etTotalMarks;

    private Button btnCreate;

    private ApiService apiService;

    private ProgressDialog progressDialog;

    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);

        initViews();

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Assignment...");
        progressDialog.setCancelable(false);

        courseId = getIntent().getLongExtra("courseId", -1);

        if (courseId == -1) {
            Toast.makeText(this,
                    "Course not selected",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        etDeadline.setOnClickListener(v -> showDatePicker());

        btnCreate.setOnClickListener(v -> createAssignment());
    }

    private void initViews() {

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDeadline = findViewById(R.id.etDeadline);
        etTotalMarks = findViewById(R.id.etTotalMarks);

        btnCreate = findViewById(R.id.btnCreate);
    }

    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {

                    String date = year + "-"
                            + String.format("%02d", month + 1)
                            + "-"
                            + String.format("%02d", dayOfMonth);

                    etDeadline.setText(date);

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void createAssignment() {

        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String deadline = etDeadline.getText().toString().trim();
        String marks = etTotalMarks.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Enter Title");
            return;
        }

        if (description.isEmpty()) {
            etDescription.setError("Enter Description");
            return;
        }

        if (deadline.isEmpty()) {
            etDeadline.setError("Select Deadline");
            return;
        }

        if (marks.isEmpty()) {
            etTotalMarks.setError("Enter Total Marks");
            return;
        }

        Assignment assignment = new Assignment();

        assignment.setTitle(title);
        assignment.setDescription(description);
        assignment.setDeadline(deadline);
        assignment.setTotalMarks(Integer.parseInt(marks));

        progressDialog.show();

        apiService.createAssignment(courseId, assignment)
                .enqueue(new Callback<ApiResponse<Assignment>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<Assignment>> call,
                            Response<ApiResponse<Assignment>> response) {

                        progressDialog.dismiss();

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            Toast.makeText(
                                    CreateAssignmentActivity.this,
                                    response.body().getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();

                            finish();

                        } else {

                            Toast.makeText(
                                    CreateAssignmentActivity.this,
                                    "Failed to create assignment",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<Assignment>> call,
                            Throwable t) {

                        progressDialog.dismiss();

                        Toast.makeText(
                                CreateAssignmentActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}