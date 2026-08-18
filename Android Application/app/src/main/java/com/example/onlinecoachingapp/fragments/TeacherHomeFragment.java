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
import com.example.onlinecoachingapp.activities.CreateCourseActivity;
import com.example.onlinecoachingapp.activities.MyCoursesActivity;
import com.example.onlinecoachingapp.session.SessionManager;

public class TeacherHomeFragment extends Fragment {

    private TextView txtWelcome;
    private TextView txtTotalCourses;
    private TextView txtTotalLectures;
    private TextView txtAssignments;
    private TextView txtQuizzes;

    private Button btnCreateCourse;
    private Button btnUploadLecture;
    private Button btnStudyMaterial;
    private Button btnAssignment;
    private Button btnQuiz;

    private SessionManager sessionManager;

    public TeacherHomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_teacher_home,
                container,
                false);

        sessionManager = new SessionManager(requireContext());

        txtWelcome = view.findViewById(R.id.txtWelcome);
        txtTotalCourses = view.findViewById(R.id.txtTotalCourses);
        txtTotalLectures = view.findViewById(R.id.txtTotalLectures);
        txtAssignments = view.findViewById(R.id.txtAssignments);
        txtQuizzes = view.findViewById(R.id.txtQuizzes);

        btnCreateCourse = view.findViewById(R.id.btnCreateCourse);
        btnUploadLecture = view.findViewById(R.id.btnUploadLecture);
        btnStudyMaterial = view.findViewById(R.id.btnStudyMaterial);
        btnAssignment = view.findViewById(R.id.btnAssignment);
        btnQuiz = view.findViewById(R.id.btnQuiz);

        txtWelcome.setText("Welcome, " + sessionManager.getName());

        // Temporary values
        txtTotalCourses.setText("0");
        txtTotalLectures.setText("0");
        txtAssignments.setText("0");
        txtQuizzes.setText("0");

        btnCreateCourse.setOnClickListener(v -> {

            Intent intent = new Intent(
                    requireContext(),
                    CreateCourseActivity.class
            );

            startActivity(intent);
        });

        // Dashboard API will be added in Part 3

        return view;
    }
}