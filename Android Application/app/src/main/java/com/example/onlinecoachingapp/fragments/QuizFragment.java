package com.example.onlinecoachingapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.QuizAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.QuizApi;
import com.example.onlinecoachingapp.model.Quiz;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizFragment extends Fragment {

    RecyclerView recyclerView;

    QuizAdapter adapter;

    List<Quiz> quizList;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_quiz,
                container,
                false);

        recyclerView = view.findViewById(R.id.recyclerQuiz);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        quizList = new ArrayList<>();

        adapter = new QuizAdapter(
                getContext(),
                quizList);

        recyclerView.setAdapter(adapter);

        loadQuiz();

        return view;
    }

    private void loadQuiz() {

        Long courseId = 1L;

        QuizApi api = ApiClient
                .getRetrofitInstance(requireContext())
                .create(QuizApi.class);

        api.getCourseQuiz(courseId)
                .enqueue(new Callback<List<Quiz>>() {

                    @Override
                    public void onResponse(
                            Call<List<Quiz>> call,
                            Response<List<Quiz>> response) {

                        Log.e("QUIZ",
                                "Code : " + response.code());

                        if(response.isSuccessful()
                                && response.body()!=null){

                            quizList.clear();

                            quizList.addAll(response.body());

                            adapter.notifyDataSetChanged();

                        }
                        else{

                            Toast.makeText(
                                    getContext(),
                                    "No Quiz Found",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<Quiz>> call,
                            Throwable t) {

                        Toast.makeText(
                                getContext(),
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();

                        Log.e("QUIZ",
                                t.getMessage(),
                                t);
                    }
                });
    }
}