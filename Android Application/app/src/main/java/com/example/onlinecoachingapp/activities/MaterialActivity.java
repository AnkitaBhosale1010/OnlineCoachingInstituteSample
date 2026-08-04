package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.MaterialAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.StudyMaterial;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialActivity extends AppCompatActivity {

    private RecyclerView recyclerMaterial;
    private ProgressBar progressBar;

    private MaterialAdapter adapter;

    private List<StudyMaterial> materialList;

    private ApiService apiService;

    private Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerMaterial = findViewById(R.id.recyclerMaterial);

        progressBar = findViewById(R.id.progressBar);

        recyclerMaterial.setLayoutManager(
                new LinearLayoutManager(this));

        materialList = new ArrayList<>();

        adapter = new MaterialAdapter(
                this,
                materialList
        );

        recyclerMaterial.setAdapter(adapter);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        courseId = getIntent().getLongExtra(
                "courseId",
                0
        );

        loadMaterials();
    }

    private void loadMaterials() {

        progressBar.setVisibility(View.VISIBLE);

        apiService.getCourseMaterials(courseId)
                .enqueue(new Callback<ApiResponse<List<StudyMaterial>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<StudyMaterial>>> call,
                            Response<ApiResponse<List<StudyMaterial>>> response) {

                        progressBar.setVisibility(View.GONE);

                        if(response.isSuccessful()
                                && response.body()!=null
                                && response.body().isSuccess()) {

                            materialList.clear();

                            materialList.addAll(
                                    response.body().getData());

                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    MaterialActivity.this,
                                    "No Study Materials Found",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<StudyMaterial>>> call,
                            Throwable t) {

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(
                                MaterialActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}