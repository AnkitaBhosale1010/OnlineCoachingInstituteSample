package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Course;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    private TextView txtCourseTitle;
    private TextView txtDescription;
    private TextView txtDuration;
    private TextView txtLevel;
    private TextView txtPrice;

    private MaterialButton btnUploadLecture;
    private MaterialButton btnUploadMaterial;
    private MaterialButton btnCreateAssignment;
    private MaterialButton btnCreateQuiz;
    private MaterialButton btnStudents;

    private ApiService apiService;

    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        initViews();

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        courseId = getIntent().getLongExtra("courseId",-1);

        if(courseId==-1){
            Toast.makeText(this,
                    "Invalid Course",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        toolbar.setNavigationOnClickListener(v->finish());

        loadCourse();

        buttonClicks();

    }

    private void initViews(){

        toolbar=findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        txtCourseTitle=findViewById(R.id.txtCourseTitle);
        txtDescription=findViewById(R.id.txtDescription);
        txtDuration=findViewById(R.id.txtDuration);
        txtLevel=findViewById(R.id.txtLevel);
        txtPrice=findViewById(R.id.txtPrice);

        btnUploadLecture=findViewById(R.id.btnUploadLecture);
        btnUploadMaterial=findViewById(R.id.btnUploadMaterial);
        btnCreateAssignment=findViewById(R.id.btnCreateAssignment);
        btnCreateQuiz=findViewById(R.id.btnCreateQuiz);
        btnStudents=findViewById(R.id.btnStudents);

    }

    private void loadCourse(){

        apiService.getCourse(courseId)
                .enqueue(new Callback<Course>() {

                    @Override
                    public void onResponse(Call<Course> call,
                                           Response<Course> response) {

                        if(response.isSuccessful()
                                && response.body()!=null){

                            Course course=response.body();

                            txtCourseTitle.setText(course.getTitle());

                            txtDescription.setText(course.getDescription());

                            txtDuration.setText(
                                    "Duration : "+course.getDuration());

                            txtLevel.setText(
                                    "Level : "+course.getLevel());

                            txtPrice.setText(
                                    "Price : ₹"+course.getPrice());

                        }

                    }

                    @Override
                    public void onFailure(Call<Course> call,
                                          Throwable t) {

                        Toast.makeText(
                                CourseDetailActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void buttonClicks(){

        btnUploadLecture.setOnClickListener(v->{

            Intent intent=new Intent(
                    this,
                    UploadLectureActivity.class);

            intent.putExtra("courseId",courseId);

            startActivity(intent);

        });


        btnUploadMaterial.setOnClickListener(v -> {

            Intent intent = new Intent(
                    CourseDetailActivity.this,
                    UploadStudyMaterialActivity.class
            );

            intent.putExtra(
                    "courseId",
                    courseId
            );

            startActivity(intent);

        });

        btnCreateAssignment.setOnClickListener(v -> {

            Intent intent = new Intent(
                    CourseDetailActivity.this,
                    AssignmentListActivity.class
            );

            intent.putExtra(
                    "courseId",
                    courseId
            );

            startActivity(intent);

        });

        btnCreateQuiz.setOnClickListener(v -> {

            Intent intent = new Intent(
                    CourseDetailActivity.this,
                    QuizListActivity.class
            );

            intent.putExtra(
                    "courseId",
                    courseId
            );

            startActivity(intent);

        });

        btnStudents.setOnClickListener(v->{

            Intent intent=new Intent(
                    this,
                    StudentListActivity.class);

            intent.putExtra("courseId",courseId);

            startActivity(intent);

        });

    }

}