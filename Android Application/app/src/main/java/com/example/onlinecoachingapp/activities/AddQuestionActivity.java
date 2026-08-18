package com.example.onlinecoachingapp.activities;

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
import com.example.onlinecoachingapp.model.QuizQuestion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText etQuestion;
    private EditText etOptionA;
    private EditText etOptionB;
    private EditText etOptionC;
    private EditText etOptionD;
    private EditText etCorrectAnswer;
    private EditText etMarks;

    private Button btnAddQuestion;

    private ApiService apiService;

    private ProgressDialog progressDialog;

    private Long quizId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);


        etQuestion = findViewById(R.id.etQuestion);
        etOptionA = findViewById(R.id.etOptionA);
        etOptionB = findViewById(R.id.etOptionB);
        etOptionC = findViewById(R.id.etOptionC);
        etOptionD = findViewById(R.id.etOptionD);
        etCorrectAnswer = findViewById(R.id.etCorrectAnswer);
        etMarks = findViewById(R.id.etMarks);

        btnAddQuestion = findViewById(R.id.btnAddQuestion);


        quizId = getIntent()
                .getLongExtra(
                        "quizId",
                        -1
                );


        if(quizId == -1){

            Toast.makeText(
                    this,
                    "Invalid Quiz",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;

        }


        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding Question...");
        progressDialog.setCancelable(false);


        btnAddQuestion.setOnClickListener(v -> addQuestion());

    }


    private void addQuestion(){

        String question =
                etQuestion.getText()
                        .toString()
                        .trim();

        String optionA =
                etOptionA.getText()
                        .toString()
                        .trim();

        String optionB =
                etOptionB.getText()
                        .toString()
                        .trim();

        String optionC =
                etOptionC.getText()
                        .toString()
                        .trim();

        String optionD =
                etOptionD.getText()
                        .toString()
                        .trim();

        String correctAnswer =
                etCorrectAnswer.getText()
                        .toString()
                        .trim();

        String marks =
                etMarks.getText()
                        .toString()
                        .trim();


        if(question.isEmpty()){

            etQuestion.setError("Enter question");
            return;

        }


        if(optionA.isEmpty() ||
                optionB.isEmpty() ||
                optionC.isEmpty() ||
                optionD.isEmpty()){

            Toast.makeText(
                    this,
                    "Enter all options",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }


        QuizQuestion quizQuestion =
                new QuizQuestion();


        quizQuestion.setQuestion(
                question
        );

        quizQuestion.setOptionA(
                optionA
        );

        quizQuestion.setOptionB(
                optionB
        );

        quizQuestion.setOptionC(
                optionC
        );

        quizQuestion.setOptionD(
                optionD
        );

        quizQuestion.setCorrectAnswer(
                correctAnswer
        );


        if(!marks.isEmpty()){

            quizQuestion.setMarks(
                    Integer.parseInt(marks)
            );

        }


        progressDialog.show();


        apiService.addQuestion(
                        quizId,
                        quizQuestion
                )
                .enqueue(new Callback<ApiResponse<QuizQuestion>>() {


                    @Override
                    public void onResponse(
                            Call<ApiResponse<QuizQuestion>> call,
                            Response<ApiResponse<QuizQuestion>> response) {


                        progressDialog.dismiss();


                        if(response.isSuccessful()
                                && response.body()!=null){


                            Toast.makeText(
                                    AddQuestionActivity.this,
                                    response.body().getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();


                            finish();


                        }
                        else{


                            Toast.makeText(
                                    AddQuestionActivity.this,
                                    "Question add failed",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }


                    @Override
                    public void onFailure(
                            Call<ApiResponse<QuizQuestion>> call,
                            Throwable t) {


                        progressDialog.dismiss();


                        Toast.makeText(
                                AddQuestionActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

}