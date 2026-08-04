package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.QuizResult;
import com.example.onlinecoachingapp.model.QuizResultDto;
import com.example.onlinecoachingapp.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizResultActivity extends AppCompatActivity {

    private TextView txtTotal, txtCorrect, txtWrong, txtMarks;
    private Button btnFinish;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        txtTotal = findViewById(R.id.txtTotal);
        txtCorrect = findViewById(R.id.txtCorrect);
        txtWrong = findViewById(R.id.txtWrong);
        txtMarks = findViewById(R.id.txtMarks);

        btnFinish = findViewById(R.id.btnFinish);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        SessionManager sessionManager = new SessionManager(this);

        Long studentId = sessionManager.getStudentId();

        Long quizId = getIntent().getLongExtra("quizId",0);

        int total = getIntent().getIntExtra("total",0);
        int correct = getIntent().getIntExtra("correct",0);
        int wrong = getIntent().getIntExtra("wrong",0);

        int marks = correct;

        txtTotal.setText("Total Questions : " + total);
        txtCorrect.setText("Correct Answers : " + correct);
        txtWrong.setText("Wrong Answers : " + wrong);
        txtMarks.setText("Score : " + marks);

        QuizResultDto dto = new QuizResultDto();

        dto.setQuizId(quizId);
        dto.setStudentId(studentId);
        dto.setTotalQuestions(total);
        dto.setCorrectAnswers(correct);
        dto.setWrongAnswers(wrong);
        dto.setTotalMarks(marks);

        apiService.saveQuizResult(dto)
                .enqueue(new Callback<ApiResponse<QuizResult>>() {

                    @Override
                    public void onResponse(Call<ApiResponse<QuizResult>> call,
                                           Response<ApiResponse<QuizResult>> response) {

                        if(response.isSuccessful()){

                            Toast.makeText(
                                    QuizResultActivity.this,
                                    "Quiz Result Saved",
                                    Toast.LENGTH_SHORT).show();

                        }else{

                            Toast.makeText(
                                    QuizResultActivity.this,
                                    "Unable to Save Result",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ApiResponse<QuizResult>> call,
                                          Throwable t) {

                        Toast.makeText(
                                QuizResultActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }
                });

        btnFinish.setOnClickListener(v -> {

            Intent intent = new Intent(
                    QuizResultActivity.this,
                    StudentMainActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);

            finish();

        });

    }
}