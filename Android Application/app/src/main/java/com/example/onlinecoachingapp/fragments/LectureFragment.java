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
import com.example.onlinecoachingapp.adapter.LectureAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.LectureApi;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Lecture;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LectureFragment extends Fragment {

    RecyclerView recyclerView;
    LectureAdapter adapter;
    List<Lecture> lectureList;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_lecture,
                container,
                false);

        recyclerView = view.findViewById(R.id.recyclerLecture);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        lectureList = new ArrayList<>();

        adapter = new LectureAdapter(
                getContext(),
                lectureList);

        recyclerView.setAdapter(adapter);

        loadLectures();

        return view;
    }

    private void loadLectures() {

        Long courseId = 2L;

        LectureApi api = ApiClient
                .getRetrofitInstance(requireContext())
                .create(LectureApi.class);

        api.getLectures(courseId)
                .enqueue(new Callback<ApiResponse<List<Lecture>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<Lecture>>> call,
                            Response<ApiResponse<List<Lecture>>> response) {

                        Log.e("LECTURE", "Code = " + response.code());

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            lectureList.clear();
                            lectureList.addAll(response.body().getData());
                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    getContext(),
                                    "No Lectures Found",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<Lecture>>> call,
                            Throwable t) {

                        Toast.makeText(
                                getContext(),
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                        Log.e("LECTURE", t.getMessage(), t);
                    }
                });
    }
}