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
import com.example.onlinecoachingapp.adapter.CourseAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.CourseApi;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Course;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseFragment extends Fragment {

    RecyclerView recyclerView;
    CourseAdapter adapter;
    List<Course> courseList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course, container, false);

        recyclerView = view.findViewById(R.id.recyclerCourse);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        courseList = new ArrayList<>();

        adapter = new CourseAdapter(getContext(), courseList);

        recyclerView.setAdapter(adapter);

        loadCourses();

        return view;
    }

    private void loadCourses() {

//        CourseApi api = ApiClient
//                .getRetrofitInstance()
//                .create(CourseApi.class);

        CourseApi api = ApiClient
                .getRetrofitInstance(requireContext())
                .create(CourseApi.class);

        api.getAllCourses().enqueue(new Callback<ApiResponse<List<Course>>>() {

            @Override
            public void onResponse(Call<ApiResponse<List<Course>>> call,
                                   Response<ApiResponse<List<Course>>> response) {

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().isSuccess()) {

                    courseList.clear();

                    courseList.addAll(response.body().getData());

                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(getContext(),
                            "No Courses Found",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Course>>> call,
                                  Throwable t) {

                Toast.makeText(getContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();

                Log.e("COURSE_API", t.getMessage());
            }
        });
    }
}