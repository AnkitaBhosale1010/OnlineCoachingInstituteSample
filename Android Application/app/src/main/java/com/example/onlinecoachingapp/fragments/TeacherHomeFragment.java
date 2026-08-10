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
import com.example.onlinecoachingapp.activities.CreateCourseActivity;
import com.example.onlinecoachingapp.activities.SelectCourseActivity;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Assignment;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.model.Lecture;
import com.example.onlinecoachingapp.model.Quiz;
import com.example.onlinecoachingapp.session.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ApiService apiService;

    private int totalLectures = 0;
    private int totalAssignments = 0;
    private int totalQuizzes = 0;

    private int completedCourseCalls = 0;
    private int totalCourseCalls = 0;

    public TeacherHomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_teacher_home,
                container,
                false
        );

        sessionManager = new SessionManager(requireContext());

        apiService = ApiClient
                .getRetrofitInstance(requireContext())
                .create(ApiService.class);

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

        txtWelcome.setText(
                "Welcome, " + sessionManager.getName()
        );

        // Initial values
        txtTotalCourses.setText("0");
        txtTotalLectures.setText("0");
        txtAssignments.setText("0");
        txtQuizzes.setText("0");

        setupQuickActions();

        loadDashboardData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        /*
         * Refresh dashboard whenever teacher comes back
         * from Create Course / Upload Lecture / Assignment / Quiz.
         */
        if (apiService != null) {
            loadDashboardData();
        }
    }

    private void setupQuickActions() {

        // Create Course
        btnCreateCourse.setOnClickListener(v -> {

            startActivity(new Intent(
                    requireContext(),
                    CreateCourseActivity.class
            ));

        });

        // Upload Lecture
        btnUploadLecture.setOnClickListener(v -> {

            Intent intent = new Intent(
                    requireContext(),
                    SelectCourseActivity.class
            );

            intent.putExtra("action", "lecture");

            startActivity(intent);

        });

        // Study Material
        btnStudyMaterial.setOnClickListener(v -> {

            Intent intent = new Intent(
                    requireContext(),
                    SelectCourseActivity.class
            );

            intent.putExtra("action", "material");

            startActivity(intent);

        });

        // Assignment
        btnAssignment.setOnClickListener(v -> {

            Intent intent = new Intent(
                    requireContext(),
                    SelectCourseActivity.class
            );

            intent.putExtra("action", "assignment");

            startActivity(intent);

        });

        // Quiz
        btnQuiz.setOnClickListener(v -> {

            Intent intent = new Intent(
                    requireContext(),
                    SelectCourseActivity.class
            );

            intent.putExtra("action", "quiz");

            startActivity(intent);

        });
    }

    private void loadDashboardData() {

        Long teacherId = sessionManager.getTeacherId();

        if (teacherId == null) {

            Toast.makeText(
                    requireContext(),
                    "Teacher not logged in",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        apiService.getTeacherCourses(teacherId)
                .enqueue(new Callback<List<Course>>() {

                    @Override
                    public void onResponse(
                            Call<List<Course>> call,
                            Response<List<Course>> response) {

                        if (!isAdded()) {
                            return;
                        }

                        if (response.isSuccessful()
                                && response.body() != null) {

                            List<Course> courses = response.body();

                            // Courses count
                            txtTotalCourses.setText(
                                    String.valueOf(courses.size())
                            );

                            // Reset other counts
                            totalLectures = 0;
                            totalAssignments = 0;
                            totalQuizzes = 0;

                            completedCourseCalls = 0;
                            totalCourseCalls = courses.size();

                            /*
                             * No courses means all other
                             * counters remain zero.
                             */
                            if (courses.isEmpty()) {

                                updateCounters();

                                return;
                            }

                            /*
                             * Fetch lectures, assignments and
                             * quizzes for every teacher course.
                             */
                            for (Course course : courses) {

                                Long courseId =
                                        course.getCourseId();

                                if (courseId == null) {

                                    completedCourseCalls++;
                                    checkAllCallsCompleted();

                                    continue;
                                }

                                loadCourseLectures(courseId);
                                loadCourseAssignments(courseId);
                                loadCourseQuizzes(courseId);
                            }

                        } else {

                            Toast.makeText(
                                    requireContext(),
                                    "Unable to load teacher courses",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<Course>> call,
                            Throwable t) {

                        if (!isAdded()) {
                            return;
                        }

                        Toast.makeText(
                                requireContext(),
                                "Error loading courses: "
                                        + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void loadCourseLectures(Long courseId) {

        apiService.getCourseLectures(courseId)
                .enqueue(new Callback<ApiResponse<List<Lecture>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<Lecture>>> call,
                            Response<ApiResponse<List<Lecture>>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getData() != null) {

                            totalLectures +=
                                    response.body().getData().size();
                        }

                        completedCourseCalls++;
                        checkAllCallsCompleted();
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<Lecture>>> call,
                            Throwable t) {

                        completedCourseCalls++;
                        checkAllCallsCompleted();
                    }
                });
    }

    private void loadCourseAssignments(Long courseId) {

        apiService.getAssignments(courseId)
                .enqueue(new Callback<List<Assignment>>() {

                    @Override
                    public void onResponse(
                            Call<List<Assignment>> call,
                            Response<List<Assignment>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            totalAssignments +=
                                    response.body().size();
                        }

                        completedCourseCalls++;
                        checkAllCallsCompleted();
                    }

                    @Override
                    public void onFailure(
                            Call<List<Assignment>> call,
                            Throwable t) {

                        completedCourseCalls++;
                        checkAllCallsCompleted();
                    }
                });
    }

    private void loadCourseQuizzes(Long courseId) {

        apiService.getQuizzes(courseId)
                .enqueue(new Callback<List<Quiz>>() {

                    @Override
                    public void onResponse(
                            Call<List<Quiz>> call,
                            Response<List<Quiz>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            totalQuizzes +=
                                    response.body().size();
                        }

                        completedCourseCalls++;
                        checkAllCallsCompleted();
                    }

                    @Override
                    public void onFailure(
                            Call<List<Quiz>> call,
                            Throwable t) {

                        completedCourseCalls++;
                        checkAllCallsCompleted();
                    }
                });
    }

    private void checkAllCallsCompleted() {

        /*
         * Three API calls are made for every course:
         *
         * Lecture
         * Assignment
         * Quiz
         *
         * Therefore expected calls = courses × 3.
         */
        int expectedCalls = totalCourseCalls * 3;

        if (completedCourseCalls >= expectedCalls) {

            updateCounters();
        }
    }

    private void updateCounters() {

        if (!isAdded()) {
            return;
        }

        txtTotalLectures.setText(
                String.valueOf(totalLectures)
        );

        txtAssignments.setText(
                String.valueOf(totalAssignments)
        );

        txtQuizzes.setText(
                String.valueOf(totalQuizzes)
        );
    }
}