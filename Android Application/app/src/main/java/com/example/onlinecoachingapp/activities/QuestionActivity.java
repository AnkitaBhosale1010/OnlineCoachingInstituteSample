package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.QuizAnswer;
import com.example.onlinecoachingapp.model.QuizAttempt;
import com.example.onlinecoachingapp.model.QuizQuestion;
import com.example.onlinecoachingapp.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {

    TextView txtQuestionNo, txtQuestion;

    RadioGroup radioGroup;

    RadioButton rbA, rbB, rbC, rbD;

    Button btnNext;

    ApiService apiService;

    List<QuizQuestion> questionList = new ArrayList<>();

    int currentPosition = 0;

    Long quizId;

    Long studentId;

    int correctAnswers = 0;

    int wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        txtQuestionNo = findViewById(R.id.txtQuestionNo);
        txtQuestion = findViewById(R.id.txtQuestion);

        radioGroup = findViewById(R.id.radioGroup);

        rbA = findViewById(R.id.rbOptionA);
        rbB = findViewById(R.id.rbOptionB);
        rbC = findViewById(R.id.rbOptionC);
        rbD = findViewById(R.id.rbOptionD);

        btnNext = findViewById(R.id.btnNext);

        quizId = getIntent().getLongExtra("quizId",0);

        SessionManager sessionManager = new SessionManager(this);

        studentId = sessionManager.getStudentId();

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        loadQuestions();

        btnNext.setOnClickListener(v -> submitCurrentAnswer());

    }

    private void loadQuestions(){

        apiService.getQuizQuestions(quizId)
                .enqueue(new Callback<List<QuizQuestion>>() {

                    @Override
                    public void onResponse(Call<List<QuizQuestion>> call,
                                           Response<List<QuizQuestion>> response) {

                        if(response.isSuccessful() &&
                                response.body()!=null){

                            questionList = response.body();

                            showQuestion();

                        }

                    }

                    @Override
                    public void onFailure(Call<List<QuizQuestion>> call,
                                          Throwable t) {

                        Toast.makeText(
                                QuestionActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void showQuestion(){

        QuizQuestion question =
                questionList.get(currentPosition);

        txtQuestionNo.setText(
                "Question "
                        +(currentPosition+1)
                        +"/"
                        +questionList.size());

        txtQuestion.setText(question.getQuestion());

        rbA.setText(question.getOptionA());
        rbB.setText(question.getOptionB());
        rbC.setText(question.getOptionC());
        rbD.setText(question.getOptionD());

        radioGroup.clearCheck();

    }

    private void submitCurrentAnswer(){

        if(radioGroup.getCheckedRadioButtonId()==-1){

            Toast.makeText(this,
                    "Select an option",
                    Toast.LENGTH_SHORT).show();

            return;

        }

        RadioButton selected =
                findViewById(
                        radioGroup.getCheckedRadioButtonId());

        QuizQuestion question =
                questionList.get(currentPosition);

        QuizAnswer answer =
                new QuizAnswer();

        answer.setQuestionId(question.getQuestionId());
        answer.setStudentId(studentId);
        answer.setAnswer(selected.getText().toString());

        apiService.submitAnswer(answer)
                .enqueue(new Callback<ApiResponse<QuizAttempt>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<QuizAttempt>> call,
                            Response<ApiResponse<QuizAttempt>> response) {

                        if(response.isSuccessful()
                                && response.body()!=null){

                            QuizAttempt attempt =
                                    response.body().getData();

                            if(attempt.getObtainedMarks()>0){

                                correctAnswers++;

                            }else{

                                wrongAnswers++;

                            }

                            nextQuestion();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<QuizAttempt>> call,
                            Throwable t) {

                        Toast.makeText(
                                QuestionActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }

                });

    }

    private void nextQuestion(){

        currentPosition++;

        if(currentPosition<questionList.size()){

            showQuestion();

        }else{

            Intent intent =
                    new Intent(
                            this,
                            QuizResultActivity.class);

            intent.putExtra(
                    "quizId",
                    quizId);

            intent.putExtra(
                    "correct",
                    correctAnswers);

            intent.putExtra(
                    "wrong",
                    wrongAnswers);

            intent.putExtra(
                    "total",
                    questionList.size());

            startActivity(intent);

            finish();

        }

    }

}