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
import com.example.onlinecoachingapp.adapter.AssignmentAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.AssignmentApi;
import com.example.onlinecoachingapp.model.Assignment;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentFragment extends Fragment {

    RecyclerView recyclerView;
    AssignmentAdapter adapter;
    List<Assignment> assignmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_assignment,container,false);

        recyclerView=view.findViewById(R.id.recyclerAssignment);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        assignmentList=new ArrayList<>();

        adapter=new AssignmentAdapter(getContext(),assignmentList);

        recyclerView.setAdapter(adapter);

        loadAssignments();

        return view;
    }

    private void loadAssignments() {

        Long courseId = 1L;

        AssignmentApi api = ApiClient
                .getRetrofitInstance(requireContext())
                .create(AssignmentApi.class);

        api.getAssignments(courseId).enqueue(new Callback<List<Assignment>>() {

            @Override
            public void onResponse(Call<List<Assignment>> call,
                                   Response<List<Assignment>> response) {

                Log.d("ASSIGNMENT", "Response Code = " + response.code());

                if (response.body() != null) {
                    Log.d("ASSIGNMENT", "Size = " + response.body().size());
                }

                if (response.isSuccessful() && response.body() != null) {

                    assignmentList.clear();
                    assignmentList.addAll(response.body());

                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(getContext(),
                            "Response Code : " + response.code(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Assignment>> call,
                                  Throwable t) {

                Log.e("ASSIGNMENT", t.getMessage(), t);

                Toast.makeText(getContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}