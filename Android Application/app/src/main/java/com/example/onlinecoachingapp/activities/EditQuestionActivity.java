package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.QuizQuestion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditQuestionActivity extends AppCompatActivity {

    private EditText etQuestion;
    private EditText etOptionA;
    private EditText etOptionB;
    private EditText etOptionC;
    private EditText etOptionD;
    private EditText etCorrectAnswer;
    private EditText etMarks;

    private Button btnUpdateQuestion;

    private ApiService apiService;
    private QuizQuestion question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        etQuestion=findViewById(R.id.etQuestion);
        etOptionA=findViewById(R.id.etOptionA);
        etOptionB=findViewById(R.id.etOptionB);
        etOptionC=findViewById(R.id.etOptionC);
        etOptionD=findViewById(R.id.etOptionD);
        etCorrectAnswer=findViewById(R.id.etCorrectAnswer);
        etMarks=findViewById(R.id.etMarks);
        btnUpdateQuestion=findViewById(R.id.btnUpdateQuestion);

        apiService=ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        question=(QuizQuestion)getIntent()
                .getSerializableExtra("question");

        if(question==null){
            Toast.makeText(this,"Question not found",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etQuestion.setText(question.getQuestion());
        etOptionA.setText(question.getOptionA());
        etOptionB.setText(question.getOptionB());
        etOptionC.setText(question.getOptionC());
        etOptionD.setText(question.getOptionD());
        etCorrectAnswer.setText(question.getCorrectAnswer());

        if(question.getMarks()!=null){
            etMarks.setText(String.valueOf(question.getMarks()));
        }

        btnUpdateQuestion.setOnClickListener(v->updateQuestion());

    }

    private void updateQuestion(){

        question.setQuestion(etQuestion.getText().toString().trim());
        question.setOptionA(etOptionA.getText().toString().trim());
        question.setOptionB(etOptionB.getText().toString().trim());
        question.setOptionC(etOptionC.getText().toString().trim());
        question.setOptionD(etOptionD.getText().toString().trim());
        question.setCorrectAnswer(etCorrectAnswer.getText().toString().trim());
        question.setMarks(
                Integer.parseInt(
                        etMarks.getText().toString().trim()
                )
        );

        apiService.updateQuestion(
                question.getQuestionId(),
                question
        ).enqueue(new Callback<QuizQuestion>() {

            @Override
            public void onResponse(Call<QuizQuestion> call,
                                   Response<QuizQuestion> response) {

                if(response.isSuccessful()){

                    Toast.makeText(
                            EditQuestionActivity.this,
                            "Question Updated",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();

                }else{

                    Toast.makeText(
                            EditQuestionActivity.this,
                            "Update Failed",
                            Toast.LENGTH_SHORT
                    ).show();

                }

            }

            @Override
            public void onFailure(Call<QuizQuestion> call,
                                  Throwable t) {

                Toast.makeText(
                        EditQuestionActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

    }

}