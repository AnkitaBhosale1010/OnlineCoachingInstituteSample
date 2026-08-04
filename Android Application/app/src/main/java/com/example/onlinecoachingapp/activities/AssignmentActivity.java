package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.AssignmentAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Assignment;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AssignmentAdapter adapter;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        recyclerView = findViewById(R.id.recyclerAssignments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Long courseId = getIntent().getLongExtra("courseId", 0);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        loadAssignments(courseId);
    }

    private void loadAssignments(Long courseId) {

        apiService.getAssignments(courseId)
                .enqueue(new Callback<List<Assignment>>() {

                    @Override
                    public void onResponse(Call<List<Assignment>> call,
                                           Response<List<Assignment>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            adapter = new AssignmentAdapter(
                                    AssignmentActivity.this,
                                    response.body());

                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Assignment>> call,
                                          Throwable t) {

                        Toast.makeText(
                                AssignmentActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}