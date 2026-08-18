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
import com.example.onlinecoachingapp.model.Assignment;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAssignmentActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private EditText etDeadline;
    private EditText etTotalMarks;

    private Button btnUpdate;

    private ApiService apiService;

    private ProgressDialog progressDialog;

    private Assignment assignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        initViews();

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Assignment...");
        progressDialog.setCancelable(false);

        assignment = (Assignment) getIntent()
                .getSerializableExtra("assignment");

        if (assignment == null) {

            Toast.makeText(
                    this,
                    "Assignment not found",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        loadAssignment();

        etDeadline.setOnClickListener(v -> showDatePicker());

        btnUpdate.setOnClickListener(v -> updateAssignment());

    }

    private void initViews() {

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDeadline = findViewById(R.id.etDeadline);
        etTotalMarks = findViewById(R.id.etTotalMarks);

        btnUpdate = findViewById(R.id.btnUpdate);

    }

    private void loadAssignment() {

        etTitle.setText(assignment.getTitle());

        etDescription.setText(assignment.getDescription());

        etDeadline.setText(assignment.getDeadline());

        etTotalMarks.setText(
                String.valueOf(assignment.getTotalMarks())
        );

    }

    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, day) -> {

                    String date = year + "-"
                            + String.format("%02d", month + 1)
                            + "-"
                            + String.format("%02d", day);

                    etDeadline.setText(date);

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();

    }

    private void updateAssignment() {

        String title = etTitle.getText().toString().trim();

        String description = etDescription.getText().toString().trim();

        String deadline = etDeadline.getText().toString().trim();

        String marks = etTotalMarks.getText().toString().trim();

        if (title.isEmpty()) {

            etTitle.setError("Required");
            return;

        }

        if (description.isEmpty()) {

            etDescription.setError("Required");
            return;

        }

        if (deadline.isEmpty()) {

            etDeadline.setError("Required");
            return;

        }

        if (marks.isEmpty()) {

            etTotalMarks.setError("Required");
            return;

        }

        assignment.setTitle(title);

        assignment.setDescription(description);

        assignment.setDeadline(deadline);

        assignment.setTotalMarks(
                Integer.parseInt(marks)
        );

        progressDialog.show();

        apiService.updateAssignment(
                assignment.getAssignmentId(),
                assignment
        ).enqueue(new Callback<Assignment>() {

            @Override
            public void onResponse(
                    Call<Assignment> call,
                    Response<Assignment> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    Toast.makeText(
                            EditAssignmentActivity.this,
                            "Assignment Updated",
                            Toast.LENGTH_LONG
                    ).show();

                    finish();

                } else {

                    Toast.makeText(
                            EditAssignmentActivity.this,
                            "Update Failed",
                            Toast.LENGTH_LONG
                    ).show();

                }

            }

            @Override
            public void onFailure(
                    Call<Assignment> call,
                    Throwable t) {

                progressDialog.dismiss();

                Toast.makeText(
                        EditAssignmentActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();

            }

        });

    }

}