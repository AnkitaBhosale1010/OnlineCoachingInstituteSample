package com.example.onlinecoachingapp.activities;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.TeacherCourseAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.session.SessionManager;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCoursesActivity extends AppCompatActivity
        implements TeacherCourseAdapter.OnCourseActionListener {

    private RecyclerView recyclerCourses;
    private SwipeRefreshLayout swipeRefresh;

    private TeacherCourseAdapter adapter;
    private List<Course> courseList = new ArrayList<>();
    private List<Course> filteredList = new ArrayList<>();

    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Courses");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerCourses = findViewById(R.id.recyclerCourses);
        swipeRefresh = findViewById(R.id.swipeRefresh);

        sessionManager = new SessionManager(this);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        recyclerCourses.setLayoutManager(
                new LinearLayoutManager(this));

        adapter = new TeacherCourseAdapter(
                this,
                courseList,
                this);

        recyclerCourses.setAdapter(adapter);

        loadCourses();

        swipeRefresh.setOnRefreshListener(this::loadCourses);

    }

    private void loadCourses() {

        swipeRefresh.setRefreshing(true);

        apiService.getTeacherCourses(
                        sessionManager.getTeacherId())

                .enqueue(new Callback<ApiResponse<List<Course>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<Course>>> call,
                            Response<ApiResponse<List<Course>>> response) {

                        swipeRefresh.setRefreshing(false);

                        if(response.isSuccessful()
                                && response.body()!=null
                                && response.body().isSuccess()){

                            courseList.clear();
                            filteredList.clear();

                            courseList.addAll(response.body().getData());

                            Toast.makeText(
                                    MyCoursesActivity.this,
                                    "Total Courses = " + courseList.size(),
                                    Toast.LENGTH_LONG
                            ).show();
                            filteredList.addAll(courseList);

                            adapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<Course>>> call,
                            Throwable t) {

                        swipeRefresh.setRefreshing(false);

                        Toast.makeText(
                                MyCoursesActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                });

    }

    private void filter(String text) {

        filteredList.clear();

        String search = text.toLowerCase().trim();

        for (Course course : courseList) {

            if (course.getTitle().toLowerCase().contains(search)
                    || course.getDescription().toLowerCase().contains(search)
                    || course.getLevel().toLowerCase().contains(search)
                    || course.getDuration().toLowerCase().contains(search)) {

                filteredList.add(course);
            }
        }

        adapter.notifyDataSetChanged();

        Toast.makeText(this,
                "Found : " + filteredList.size(),
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onEdit(Course course) {

        Intent intent = new Intent(
                MyCoursesActivity.this,
                EditCourseActivity.class);

        intent.putExtra("courseId", course.getCourseId());
        intent.putExtra("title", course.getTitle());
        intent.putExtra("description", course.getDescription());
        intent.putExtra("duration", course.getDuration());
        intent.putExtra("level", course.getLevel());
        intent.putExtra("price", course.getPrice().toString());

        startActivity(intent);
    }

    @Override
    public void onDelete(Course course) {

        Toast.makeText(
                this,
                "Delete : "+course.getTitle(),
                Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}