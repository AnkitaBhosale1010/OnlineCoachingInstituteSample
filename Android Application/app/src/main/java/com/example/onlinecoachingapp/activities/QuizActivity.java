package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.QuizAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Quiz;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApiService apiService;
    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        recyclerView = findViewById(R.id.recyclerQuiz);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseId = getIntent().getLongExtra("courseId", 0L);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        loadQuizList();
    }

    private void loadQuizList() {

        apiService.getQuizzes(courseId)
                .enqueue(new Callback<List<Quiz>>() {

                    @Override
                    public void onResponse(Call<List<Quiz>> call,
                                           Response<List<Quiz>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            QuizAdapter adapter =
                                    new QuizAdapter(
                                            QuizActivity.this,
                                            response.body());

                            recyclerView.setAdapter(adapter);

                        } else {

                            Toast.makeText(
                                    QuizActivity.this,
                                    "No Quiz Available",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<List<Quiz>> call,
                                          Throwable t) {

                        Toast.makeText(
                                QuizActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }
                });

    }

}