package com.example.onlinecoachingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.DashboardResponse;
import com.example.onlinecoachingapp.session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView txtWelcome,txtCourses,txtLectures,txtAssignments,txtQuiz;
    private SessionManager sessionManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,
                container,
                false);

        sessionManager = new SessionManager(requireContext());

        txtWelcome = view.findViewById(R.id.txtWelcome);
        txtCourses = view.findViewById(R.id.txtCourses);
        txtLectures = view.findViewById(R.id.txtLectures);
        txtAssignments = view.findViewById(R.id.txtAssignments);
        txtQuiz = view.findViewById(R.id.txtQuiz);

        loadDashboardFromServer();

        return view;
    }

    private void loadDashboardFromServer() {

        String token = "Bearer " + sessionManager.getToken();

        long studentId = sessionManager.getUserId();

        ApiService api = ApiClient
                .getRetrofitInstance(requireContext())
                .create(ApiService.class);

        api.getDashboard(studentId)
                .enqueue(new Callback<DashboardResponse>() {

                    @Override
                    public void onResponse(Call<DashboardResponse> call,
                                           Response<DashboardResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            DashboardResponse dashboard = response.body();

                            txtCourses.setText(String.valueOf(dashboard.getTotalCourses()));
                            txtLectures.setText(String.valueOf(dashboard.getTotalLectures()));
                            txtAssignments.setText(String.valueOf(dashboard.getPendingAssignments()));
                            txtQuiz.setText(String.valueOf(dashboard.getUpcomingQuizzes()));
                        }
                    }

                    @Override
                    public void onFailure(Call<DashboardResponse> call, Throwable t) {

                        Toast.makeText(requireContext(),
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}