package com.example.onlinecoachingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.activities.ManageBatchesActivity;
import com.example.onlinecoachingapp.activities.ManageCoursesActivity;
import com.example.onlinecoachingapp.activities.ManageStudentsActivity;
import com.example.onlinecoachingapp.activities.ManageTeachersActivity;
import com.example.onlinecoachingapp.activities.ReportsActivity;
import com.example.onlinecoachingapp.session.SessionManager;

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

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_admin_home,
                container,
                false);

        sessionManager = new SessionManager(requireContext());

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

        txtWelcome.setText("Welcome, " + sessionManager.getName());

        // Temporary values
        txtStudents.setText("0");
        txtTeachers.setText("0");
        txtCourses.setText("0");
        txtBatches.setText("0");

        // Quick Actions

        btnStudents.setOnClickListener(v ->
                startActivity(new Intent(requireActivity(),
                        ManageStudentsActivity.class)));

        btnTeachers.setOnClickListener(v ->
                startActivity(new Intent(requireActivity(),
                        ManageTeachersActivity.class)));

        btnCourses.setOnClickListener(v ->
                startActivity(new Intent(requireActivity(),
                        ManageCoursesActivity.class)));

        btnBatches.setOnClickListener(v ->
                startActivity(new Intent(requireActivity(),
                        ManageBatchesActivity.class)));

        btnReports.setOnClickListener(v ->
                startActivity(new Intent(requireActivity(),
                        ReportsActivity.class)));

        // Dashboard API will be added later

        return view;
    }
}