package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Quiz;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditQuizActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private EditText etTotalMarks;
    private EditText etQuizDate;

    private Button btnUpdateQuiz;

    private ApiService apiService;

    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quiz);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etTotalMarks = findViewById(R.id.etTotalMarks);
        etQuizDate = findViewById(R.id.etQuizDate);

        btnUpdateQuiz = findViewById(R.id.btnUpdateQuiz);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        quiz = (Quiz) getIntent().getSerializableExtra("quiz");

        if (quiz == null) {

            Toast.makeText(
                    this,
                    "Quiz not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        Toast.makeText(
                this,
                "Quiz ID = " + quiz.getQuizId(),
                Toast.LENGTH_LONG
        ).show();

        etTitle.setText(quiz.getTitle());
        etDescription.setText(quiz.getDescription());

        if (quiz.getTotalMarks() != null) {
            etTotalMarks.setText(
                    String.valueOf(quiz.getTotalMarks())
            );
        }

        etQuizDate.setText(quiz.getQuizDate());

        btnUpdateQuiz.setOnClickListener(v -> updateQuiz());

    }

    private void updateQuiz() {

        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String marks = etTotalMarks.getText().toString().trim();
        String date = etQuizDate.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Enter title");
            return;
        }

        if (description.isEmpty()) {
            etDescription.setError("Enter description");
            return;
        }

        if (marks.isEmpty()) {
            etTotalMarks.setError("Enter total marks");
            return;
        }

        if (date.isEmpty()) {
            etQuizDate.setError("Enter quiz date");
            return;
        }

        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setTotalMarks(Integer.parseInt(marks));
        quiz.setQuizDate(date);

        apiService.updateQuiz(
                quiz.getQuizId(),
                quiz
        ).enqueue(new Callback<Quiz>() {

            @Override
            public void onResponse(
                    Call<Quiz> call,
                    Response<Quiz> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            EditQuizActivity.this,
                            "Quiz Updated Successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();

                } else {

                    String errorMessage = "";

                    try {

                        if (response.errorBody() != null) {

                            errorMessage =
                                    response.errorBody().string();

                        }

                    } catch (IOException e) {

                        errorMessage = e.getMessage();

                    }

                    Toast.makeText(
                            EditQuizActivity.this,
                            "HTTP " + response.code()
                                    + "\n"
                                    + errorMessage,
                            Toast.LENGTH_LONG
                    ).show();

                }

            }

            @Override
            public void onFailure(
                    Call<Quiz> call,
                    Throwable t) {

                Toast.makeText(
                        EditQuizActivity.this,
                        "Network Error : " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();

            }

        });

    }

}