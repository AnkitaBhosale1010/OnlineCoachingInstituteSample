package com.example.onlinecoachingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.MyCourseAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Enrollment;
import com.example.onlinecoachingapp.session.SessionManager;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCourseFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    MyCourseAdapter adapter;
    List<Enrollment> enrollmentList = new ArrayList<>();
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_my_courses,
                container,
                false);

        recyclerView = view.findViewById(R.id.recyclerMyCourses);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        adapter = new MyCourseAdapter(getContext(),enrollmentList);

        recyclerView.setAdapter(adapter);

        sessionManager =
                new SessionManager(requireContext());

        loadCourses();

        return view;
    }

    private void loadCourses() {

        progressBar.setVisibility(View.VISIBLE);

        ApiService api =
                ApiClient.getRetrofitInstance(requireContext())
                        .create(ApiService.class);

        Long studentId =
                sessionManager.getStudentId();

        api.getMyCourses(studentId)
                .enqueue(new Callback<List<Enrollment>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<Enrollment>> call,
                            @NonNull Response<List<Enrollment>> response) {

                        progressBar.setVisibility(View.GONE);

                        if(response.isSuccessful()
                                && response.body()!=null){

                            enrollmentList.clear();

                            enrollmentList.addAll(
                                    response.body());

                            adapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<Enrollment>> call,
                            @NonNull Throwable t) {

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(
                                getContext(),
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }
                });
    }
}