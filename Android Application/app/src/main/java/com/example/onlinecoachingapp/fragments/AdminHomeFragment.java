package com.example.onlinecoachingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.activities.AdminReportActivity;
import com.example.onlinecoachingapp.activities.ManageBatchesActivity;
import com.example.onlinecoachingapp.activities.ManageCoursesActivity;
import com.example.onlinecoachingapp.activities.ManageStudentsActivity;
import com.example.onlinecoachingapp.activities.ManageTeachersActivity;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Batch;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.model.Student;
import com.example.onlinecoachingapp.model.Teacher;
import com.example.onlinecoachingapp.session.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeFragment extends Fragment {

    private TextView txtWelcome;
    private TextView txtStudents;
    private TextView txtTeachers;
    private TextView txtCourses;
    private TextView txtBatches;

    private Button btnStudents;
    private Button btnTeachers;
    private Button btnCourses;
    private Button btnBatches;
    private Button btnReports;

    private SessionManager sessionManager;
    private ApiService apiService;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_admin_home,
                container,
                false
        );

        sessionManager = new SessionManager(requireContext());

        apiService = ApiClient
                .getRetrofitInstance(requireContext())
                .create(ApiService.class);

        txtWelcome = view.findViewById(R.id.txtWelcome);

        txtStudents = view.findViewById(R.id.txtStudents);
        txtTeachers = view.findViewById(R.id.txtTeachers);
        txtCourses = view.findViewById(R.id.txtCourses);
        txtBatches = view.findViewById(R.id.txtBatches);

        btnStudents = view.findViewById(R.id.btnStudents);
        btnTeachers = view.findViewById(R.id.btnTeachers);
        btnCourses = view.findViewById(R.id.btnCourses);
        btnBatches = view.findViewById(R.id.btnBatches);
        btnReports = view.findViewById(R.id.btnReports);

        txtWelcome.setText(
                "Welcome, " + sessionManager.getName()
        );

        // Quick Actions

        btnStudents.setOnClickListener(v ->
                startActivity(new Intent(
                        requireActivity(),
                        ManageStudentsActivity.class
                ))
        );

        btnTeachers.setOnClickListener(v ->
                startActivity(new Intent(
                        requireActivity(),
                        ManageTeachersActivity.class
                ))
        );

        btnCourses.setOnClickListener(v ->
                startActivity(new Intent(
                        requireActivity(),
                        ManageCoursesActivity.class
                ))
        );

        btnBatches.setOnClickListener(v ->
                startActivity(new Intent(
                        requireActivity(),
                        ManageBatchesActivity.class
                ))
        );

        btnReports.setOnClickListener(v ->
                startActivity(new Intent(
                        requireActivity(),
                        AdminReportActivity.class
                ))
        );

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        /*
         * Reload counts whenever dashboard becomes visible.
         * Therefore:
         * Add Student  -> count increases
         * Delete Student -> count decreases
         * Add Teacher  -> count increases
         * Delete Teacher -> count decreases
         * Add Course   -> count increases
         * Delete Course -> count decreases
         * Add Batch    -> count increases
         * Delete Batch -> count decreases
         */
        loadDashboardCounts();
    }

    private void loadDashboardCounts() {

        // Show loading/default values
        txtStudents.setText("...");
        txtTeachers.setText("...");
        txtCourses.setText("...");
        txtBatches.setText("...");

        loadStudentCount();
        loadTeacherCount();
        loadCourseCount();
        loadBatchCount();
    }

    private void loadStudentCount() {

        apiService.getAllStudents()
                .enqueue(new Callback<ApiResponse<List<Student>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<Student>>> call,
                            Response<ApiResponse<List<Student>>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getData() != null) {

                            int count =
                                    response.body()
                                            .getData()
                                            .size();

                            txtStudents.setText(
                                    String.valueOf(count)
                            );

                        } else {

                            txtStudents.setText("0");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<Student>>> call,
                            Throwable t) {

                        txtStudents.setText("0");
                    }
                });
    }

    private void loadTeacherCount() {

        apiService.getAllTeachers()
                .enqueue(new Callback<List<Teacher>>() {

                    @Override
                    public void onResponse(
                            Call<List<Teacher>> call,
                            Response<List<Teacher>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            int count =
                                    response.body().size();

                            txtTeachers.setText(
                                    String.valueOf(count)
                            );

                        } else {

                            txtTeachers.setText("0");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<Teacher>> call,
                            Throwable t) {

                        txtTeachers.setText("0");
                    }
                });
    }

    private void loadCourseCount() {

        apiService.getAllCourses()
                .enqueue(new Callback<ApiResponse<List<Course>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<Course>>> call,
                            Response<ApiResponse<List<Course>>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getData() != null) {

                            int count =
                                    response.body()
                                            .getData()
                                            .size();

                            txtCourses.setText(
                                    String.valueOf(count)
                            );

                        } else {

                            txtCourses.setText("0");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<Course>>> call,
                            Throwable t) {

                        txtCourses.setText("0");
                    }
                });
    }

    private void loadBatchCount() {

        apiService.getAllBatches()
                .enqueue(new Callback<List<Batch>>() {

                    @Override
                    public void onResponse(
                            Call<List<Batch>> call,
                            Response<List<Batch>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            int count =
                                    response.body().size();

                            txtBatches.setText(
                                    String.valueOf(count)
                            );

                        } else {

                            txtBatches.setText("0");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<Batch>> call,
                            Throwable t) {

                        txtBatches.setText("0");
                    }
                });
    }
}