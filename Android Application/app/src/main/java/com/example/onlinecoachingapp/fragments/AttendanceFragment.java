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
import com.example.onlinecoachingapp.adapter.AttendanceAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.AttendanceApi;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Attendance;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceFragment extends Fragment {

    RecyclerView recyclerView;

    AttendanceAdapter adapter;

    List<Attendance> attendanceList;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_attendance,
                container,
                false);

        recyclerView = view.findViewById(R.id.recyclerAttendance);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        attendanceList = new ArrayList<>();

        adapter = new AttendanceAdapter(
                getContext(),
                attendanceList);

        recyclerView.setAdapter(adapter);

        loadAttendance();

        return view;
    }

    private void loadAttendance() {

        Long studentId = 3L;   // Replace later with logged-in student id

        AttendanceApi api = ApiClient
                .getRetrofitInstance(requireContext())
                .create(AttendanceApi.class);

        api.getAttendance(studentId)
                .enqueue(new Callback<ApiResponse<List<Attendance>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<Attendance>>> call,
                            Response<ApiResponse<List<Attendance>>> response) {

                        Log.e("ATTENDANCE",
                                "Code : " + response.code());

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            attendanceList.clear();

                            attendanceList.addAll(
                                    response.body().getData());

                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    getContext(),
                                    "No Attendance Found",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<Attendance>>> call,
                            Throwable t) {

                        Toast.makeText(
                                getContext(),
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();

                        Log.e("ATTENDANCE",
                                t.getMessage(),
                                t);
                    }
                });
    }
}