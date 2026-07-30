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
import com.example.onlinecoachingapp.api.QuizApi;
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

    RadioButton radioA, radioB, radioC, radioD;

    Button btnNext;

    List<QuizQuestion> questionList = new ArrayList<>();

    int currentIndex = 0;
    int correct = 0;
    int wrong = 0;
    int marks = 0;

    Long quizId;

    Long studentId = 3L;      // Change later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        txtQuestionNo = findViewById(R.id.txtQuestionNo);
        txtQuestion = findViewById(R.id.txtQuestion);

        radioGroup = findViewById(R.id.radioGroup);

        radioA = findViewById(R.id.radioA);
        radioB = findViewById(R.id.radioB);
        radioC = findViewById(R.id.radioC);
        radioD = findViewById(R.id.radioD);

        btnNext = findViewById(R.id.btnNext);

        quizId = getIntent().getLongExtra("quizId",0);

        loadQuestions();

        btnNext.setOnClickListener(v -> submitAnswer());

    }

    private void loadQuestions() {

        QuizApi api = ApiClient
                .getRetrofitInstance(this)
                .create(QuizApi.class);

        api.getQuizQuestions(quizId)
                .enqueue(new Callback<List<QuizQuestion>>() {

                    @Override
                    public void onResponse(
                            Call<List<QuizQuestion>> call,
                            Response<List<QuizQuestion>> response) {

                        if(response.isSuccessful() &&
                                response.body()!=null){

                            questionList.clear();

                            questionList.addAll(response.body());

                            showQuestion();

                        }else{

                            Toast.makeText(
                                    QuestionActivity.this,
                                    "Questions not found",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<QuizQuestion>> call,
                            Throwable t) {

                        Toast.makeText(
                                QuestionActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                });

    }

    private void showQuestion(){

        QuizQuestion q = questionList.get(currentIndex);

        txtQuestionNo.setText(
                "Question "
                        +(currentIndex+1));

        txtQuestion.setText(q.getQuestion());

        radioA.setText(q.getOptionA());
        radioB.setText(q.getOptionB());
        radioC.setText(q.getOptionC());
        radioD.setText(q.getOptionD());

        radioGroup.clearCheck();

    }

    private void submitAnswer(){

        if(radioGroup.getCheckedRadioButtonId()==-1){

            Toast.makeText(this,
                    "Select Answer",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        RadioButton rb =
                findViewById(
                        radioGroup.getCheckedRadioButtonId());

        String answer =
                rb.getText().toString();

        QuizQuestion question =
                questionList.get(currentIndex);

        QuizAnswer quizAnswer =
                new QuizAnswer(
                        studentId,
                        question.getQuestionId(),
                        answer);

        QuizApi api =
                ApiClient
                        .getRetrofitInstance(this)
                        .create(QuizApi.class);

        api.submitAnswer(quizAnswer)
                .enqueue(new Callback<ApiResponse<QuizAttempt>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<QuizAttempt>> call,
                            Response<ApiResponse<QuizAttempt>> response) {

                        if(response.body()!=null &&
                                response.body().isSuccess()){

                            if(response.body().getData().getObtainedMarks()>0){

                                correct++;

                                marks += response.body()
                                        .getData()
                                        .getObtainedMarks();

                            }else{

                                wrong++;
                            }

                        }

                        currentIndex++;

                        if(currentIndex<questionList.size()){

                            showQuestion();

                        }else{

                            Intent intent =
                                    new Intent(
                                            QuestionActivity.this,
                                            ResultActivity.class);

                            intent.putExtra("total", questionList.size());
                            intent.putExtra("correct", correct);
                            intent.putExtra("wrong", wrong);
                            intent.putExtra("marks", marks);

                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<QuizAttempt>> call,
                            Throwable t) {

                        Toast.makeText(
                                QuestionActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                });
    }
}