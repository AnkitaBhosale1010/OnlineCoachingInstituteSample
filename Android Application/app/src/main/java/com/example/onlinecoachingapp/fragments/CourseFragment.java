package com.example.onlinecoachingapp.fragments;

import android.util.Log;
import android.os.Bundle;
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
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.model.Enrollment;
import com.example.onlinecoachingapp.session.SessionManager;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseFragment extends Fragment {


    RecyclerView recyclerCourse;

    CourseAdapter adapter;

    List<Course> courseList = new ArrayList<>();

    ApiService apiService;

    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {


        View view =
                inflater.inflate(
                        R.layout.fragment_course,
                        container,
                        false
                );



        recyclerCourse =
                view.findViewById(
                        R.id.recyclerCourse
                );



        recyclerCourse.setLayoutManager(
                new LinearLayoutManager(
                        requireContext()
                )
        );



        sessionManager =
                new SessionManager(
                        requireContext()
                );



        apiService =
                ApiClient
                        .getRetrofitInstance(
                                requireContext()
                        )
                        .create(
                                ApiService.class
                        );



        adapter =
                new CourseAdapter(
                        requireContext(),
                        courseList,
                        course -> enrollCourse(course)
                );


        recyclerCourse.setAdapter(adapter);



        loadCourses();



        return view;

    }

    private void loadCourses(){


        apiService
                .getAllCourses()
                .enqueue(
                        new Callback<ApiResponse<List<Course>>>() {


                            @Override
                            public void onResponse(
                                    Call<ApiResponse<List<Course>>> call,
                                    Response<ApiResponse<List<Course>>> response) {


                                if(response.isSuccessful()
                                        &&
                                        response.body()!=null){



                                    courseList.clear();



                                    courseList.addAll(
                                            response.body().getData()
                                    );


                                    adapter.notifyDataSetChanged();


                                }


                            }




                            @Override
                            public void onFailure(
                                    Call<ApiResponse<List<Course>>> call,
                                    Throwable t) {


                                Toast.makeText(
                                        requireContext(),
                                        t.getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();

                            }
                        }
                );

    }


    private void enrollCourse(Course course) {

        Long studentId = sessionManager.getStudentId();

        Log.d("SESSION", "Student ID = " + studentId);
        Log.d("SESSION", "Course ID = " + course.getCourseId());

        if (studentId == null || studentId == 0) {
            Toast.makeText(requireContext(),
                    "Student ID is missing. Please login again.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        apiService.enrollCourse(studentId, course.getCourseId())
                .enqueue(new Callback<ApiResponse<Enrollment>>() {

                    @Override
                    public void onResponse(Call<ApiResponse<Enrollment>> call,
                                           Response<ApiResponse<Enrollment>> response) {

                        Log.d("ENROLL", "Response Code = " + response.code());

                        if (response.body() != null) {
                            Log.d("ENROLL", "Message = " + response.body().getMessage());
                        }

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            Toast.makeText(requireContext(),
                                    "Course Enrolled Successfully",
                                    Toast.LENGTH_SHORT).show();

                        } else {

                            try {
                                Log.e("ENROLL ERROR",
                                        response.errorBody().string());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(requireContext(),
                                    "Enrollment Failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Enrollment>> call,
                                          Throwable t) {

                        Log.e("ENROLL FAILURE", t.getMessage(), t);

                        Toast.makeText(requireContext(),
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
