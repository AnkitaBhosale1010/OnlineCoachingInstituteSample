package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.TeacherQuizAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Quiz;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizListActivity extends AppCompatActivity {

    private RecyclerView recyclerQuizzes;
    private ImageButton btnAddQuiz;

    private TeacherQuizAdapter adapter;
    private List<Quiz> quizList;

    private ApiService apiService;

    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        recyclerQuizzes = findViewById(R.id.recyclerQuizzes);
        btnAddQuiz = findViewById(R.id.btnAddQuiz);

        courseId = getIntent().getLongExtra("courseId",-1);

        if(courseId==-1){

            Toast.makeText(
                    this,
                    "Invalid Course",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        quizList = new ArrayList<>();

        adapter = new TeacherQuizAdapter(
                this,
                quizList,
                new TeacherQuizAdapter.OnQuizActionListener() {

                    @Override
                    public void onQuestions(Quiz quiz) {

                        Intent intent = new Intent(
                                QuizListActivity.this,
                                QuestionListActivity.class
                        );

                        intent.putExtra(
                                "quizId",
                                quiz.getQuizId()
                        );

                        startActivity(intent);

                    }

                    @Override
                    public void onEdit(Quiz quiz) {

                        Intent intent = new Intent(
                                QuizListActivity.this,
                                EditQuizActivity.class
                        );

                        intent.putExtra(
                                "quiz",
                                quiz
                        );

                        startActivity(intent);

                    }

                    @Override
                    public void onDelete(Quiz quiz) {

                        deleteQuiz(
                                quiz.getQuizId()
                        );

                    }

                }
        );

        recyclerQuizzes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerQuizzes.setAdapter(adapter);

        btnAddQuiz.setOnClickListener(v -> {

            Intent intent = new Intent(
                    QuizListActivity.this,
                    CreateQuizActivity.class
            );

            intent.putExtra(
                    "courseId",
                    courseId
            );

            startActivity(intent);

        });

        loadQuiz();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadQuiz();
    }

    private void loadQuiz(){

        apiService.getQuizzes(courseId)
                .enqueue(new Callback<List<Quiz>>() {

                    @Override
                    public void onResponse(
                            Call<List<Quiz>> call,
                            Response<List<Quiz>> response) {

                        if(response.isSuccessful()
                                && response.body()!=null){

                            quizList.clear();

                            quizList.addAll(
                                    response.body()
                            );

                            adapter.notifyDataSetChanged();

                        }else{

                            Toast.makeText(
                                    QuizListActivity.this,
                                    "Unable to load quizzes",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<Quiz>> call,
                            Throwable t) {

                        Toast.makeText(
                                QuizListActivity.this,
                                "Network Error : " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

    private void deleteQuiz(Long quizId) {

        apiService.deleteQuiz(quizId)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {

                        if(response.isSuccessful()){

                            Toast.makeText(
                                    QuizListActivity.this,
                                    "Quiz Deleted Successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadQuiz();

                        }else{

                            Toast.makeText(
                                    QuizListActivity.this,
                                    "Delete Failed : " + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t) {

                        Toast.makeText(
                                QuizListActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }



}