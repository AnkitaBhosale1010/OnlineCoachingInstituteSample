package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.TeacherQuestionAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.QuizQuestion;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionListActivity extends AppCompatActivity {

    private RecyclerView recyclerQuestions;
    private ImageButton btnAddQuestion;

    private TeacherQuestionAdapter adapter;
    private List<QuizQuestion> questionList;

    private ApiService apiService;

    private Long quizId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        recyclerQuestions = findViewById(R.id.recyclerQuestions);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);

        quizId = getIntent().getLongExtra("quizId", -1);

        if (quizId == -1) {

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

        questionList = new ArrayList<>();

        adapter = new TeacherQuestionAdapter(
                this,
                questionList,
                new TeacherQuestionAdapter.OnQuestionActionListener() {

                    @Override
                    public void onEdit(QuizQuestion question) {

                        Intent intent = new Intent(
                                QuestionListActivity.this,
                                EditQuestionActivity.class
                        );

                        intent.putExtra(
                                "question",
                                question
                        );

                        startActivity(intent);

                    }

                    @Override
                    public void onDelete(QuizQuestion question) {

                        deleteQuestion(
                                question.getQuestionId()
                        );

                    }

                });

        recyclerQuestions.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerQuestions.setAdapter(adapter);

        btnAddQuestion.setOnClickListener(v -> {

            Intent intent = new Intent(
                    QuestionListActivity.this,
                    AddQuestionActivity.class
            );

            intent.putExtra(
                    "quizId",
                    quizId
            );

            startActivity(intent);

        });

        loadQuestions();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadQuestions();
    }

    private void loadQuestions() {

        apiService.getQuizQuestions(quizId)
                .enqueue(new Callback<List<QuizQuestion>>() {

                    @Override
                    public void onResponse(
                            Call<List<QuizQuestion>> call,
                            Response<List<QuizQuestion>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            questionList.clear();

                            questionList.addAll(
                                    response.body()
                            );

                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    QuestionListActivity.this,
                                    "Unable to load questions",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<QuizQuestion>> call,
                            Throwable t) {

                        Toast.makeText(
                                QuestionListActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

    private void deleteQuestion(Long questionId) {

        apiService.deleteQuestion(questionId)
                .enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(
                            Call<String> call,
                            Response<String> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    QuestionListActivity.this,
                                    "Question Deleted",
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadQuestions();

                        } else {

                            Toast.makeText(
                                    QuestionListActivity.this,
                                    "Delete Failed",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<String> call,
                            Throwable t) {

                        Toast.makeText(
                                QuestionListActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

}