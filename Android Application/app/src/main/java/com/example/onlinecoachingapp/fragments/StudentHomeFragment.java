package com.example.onlinecoachingapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.activities.AssignmentActivity;
import com.example.onlinecoachingapp.activities.QuizActivity;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.DashboardResponse;
import com.example.onlinecoachingapp.session.SessionManager;
import com.google.android.material.button.MaterialButton;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class StudentHomeFragment extends Fragment {


    private MaterialButton btnMyCourses;
    private MaterialButton btnAssignments;
    private MaterialButton btnQuiz;


    private TextView txtWelcome;
    private TextView txtCourses;
    private TextView txtLectures;
    private TextView txtAssignments;
    private TextView txtQuiz;


    private SessionManager sessionManager;



    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.fragment_student_home,
                container,
                false
        );



        // Buttons

        btnMyCourses =
                view.findViewById(R.id.btnMyCourses);


        btnAssignments =
                view.findViewById(R.id.btnAssignments);


        btnQuiz =
                view.findViewById(R.id.btnQuiz);



        // TextViews

        txtWelcome =
                view.findViewById(R.id.txtWelcome);


        txtCourses =
                view.findViewById(R.id.txtCourses);


        txtLectures =
                view.findViewById(R.id.txtLectures);


        txtAssignments =
                view.findViewById(R.id.txtAssignments);


        txtQuiz =
                view.findViewById(R.id.txtQuiz);



        sessionManager =
                new SessionManager(requireContext());



        txtWelcome.setText(
                "Welcome "
                        + sessionManager.getName()
        );



        loadDashboard();



        // Button Clicks

        setClickListeners();



        return view;

    }






    private void loadDashboard(){



        Long studentId =
                sessionManager.getStudentId();



        if(studentId == null){


            Toast.makeText(
                    requireContext(),
                    "Student ID not found",
                    Toast.LENGTH_SHORT
            ).show();


            return;

        }




        ApiService apiService =
                ApiClient
                        .getRetrofitInstance(requireContext())
                        .create(ApiService.class);





        apiService
                .getDashboard(studentId)
                .enqueue(new Callback<DashboardResponse>() {



                    @Override
                    public void onResponse(
                            Call<DashboardResponse> call,
                            Response<DashboardResponse> response) {



                        if(response.isSuccessful()
                                && response.body()!=null){



                            DashboardResponse data =
                                    response.body();




                            txtCourses.setText(
                                    String.valueOf(
                                            data.getTotalCourses()
                                    )
                            );




                            txtLectures.setText(
                                    String.valueOf(
                                            data.getTotalLectures()
                                    )
                            );




                            txtAssignments.setText(
                                    String.valueOf(
                                            data.getPendingAssignments()
                                    )
                            );




                            txtQuiz.setText(
                                    String.valueOf(
                                            data.getUpcomingQuizzes()
                                    )
                            );



                        }
                        else {



                            Toast.makeText(
                                    requireContext(),
                                    "Dashboard Error : "
                                            + response.code(),
                                    Toast.LENGTH_SHORT
                            ).show();


                        }



                    }





                    @Override
                    public void onFailure(
                            Call<DashboardResponse> call,
                            Throwable t) {



                        Toast.makeText(
                                requireContext(),
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();



                    }


                });



    }

    private void setClickListeners(){
        // My Courses Button


        btnMyCourses.setOnClickListener(v -> {



            getParentFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.fragment_container,
                            new CourseFragment()
                    )
                    .addToBackStack(null)
                    .commit();



        });


        // Assignment Button


        btnAssignments.setOnClickListener(v -> {


            Intent intent =
                    new Intent(
                            requireContext(),
                            AssignmentActivity.class
                    );


            startActivity(intent);


        });


        // Quiz Button


        btnQuiz.setOnClickListener(v -> {


            Intent intent =
                    new Intent(
                            requireContext(),
                            QuizActivity.class
                    );

            startActivity(intent);

        });

    }

}