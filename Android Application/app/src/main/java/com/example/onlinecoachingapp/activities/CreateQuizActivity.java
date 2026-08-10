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
import com.example.onlinecoachingapp.model.Quiz;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateQuizActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private EditText etMarks;
    private EditText etDate;

    private Button btnCreateQuiz;

    private ApiService apiService;

    private ProgressDialog progressDialog;

    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etMarks = findViewById(R.id.etMarks);
        etDate = findViewById(R.id.etDate);

        etDate.setFocusable(false);
        etDate.setClickable(true);

        etDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    CreateQuizActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {

                        String formattedDate = String.format(
                                "%04d-%02d-%02d",
                                selectedYear,
                                selectedMonth + 1,
                                selectedDay
                        );

                        etDate.setText(formattedDate);

                    },
                    year,
                    month,
                    day
            );

            datePickerDialog.show();

        });

        btnCreateQuiz = findViewById(R.id.btnCreateQuiz);

        courseId = getIntent().getLongExtra("courseId", -1);

        if (courseId == -1) {
            Toast.makeText(this, "Invalid Course", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Quiz...");
        progressDialog.setCancelable(false);

        btnCreateQuiz.setOnClickListener(v -> createQuiz());
    }

    private void createQuiz() {

        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String marks = etMarks.getText().toString().trim();
        String date = etDate.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Enter quiz title");
            return;
        }

        if (description.isEmpty()) {
            etDescription.setError("Enter description");
            return;
        }

        if (marks.isEmpty()) {
            etMarks.setError("Enter total marks");
            return;
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setTotalMarks(Integer.parseInt(marks));
        quiz.setQuizDate(date);

        progressDialog.show();

        apiService.createQuiz(courseId, quiz)
                .enqueue(new Callback<Quiz>() {

                    @Override
                    public void onResponse(Call<Quiz> call, Response<Quiz> response) {

                        progressDialog.dismiss();

                        if (response.isSuccessful() && response.body() != null) {

                            Toast.makeText(
                                    CreateQuizActivity.this,
                                    "Quiz Created Successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                            finish();

                        } else {

                            Toast.makeText(
                                    CreateQuizActivity.this,
                                    "Error Code : " + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Quiz> call, Throwable t) {

                        progressDialog.dismiss();

                        Toast.makeText(
                                CreateQuizActivity.this,
                                "Network Error : " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }

}