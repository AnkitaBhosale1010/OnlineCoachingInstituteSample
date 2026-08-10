package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.AdminReport;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminReportActivity extends AppCompatActivity {

    private TextView txtTotalStudents;
    private TextView txtTotalTeachers;
    private TextView txtTotalCourses;
    private TextView txtTotalBatches;
    private TextView txtTotalEnrollments;
    private TextView txtTotalQuizResults;
    private TextView txtTotalSubmissions;

    private ProgressBar progressBar;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);

        txtTotalStudents = findViewById(R.id.txtTotalStudents);
        txtTotalTeachers = findViewById(R.id.txtTotalTeachers);
        txtTotalCourses = findViewById(R.id.txtTotalCourses);
        txtTotalBatches = findViewById(R.id.txtTotalBatches);
        txtTotalEnrollments = findViewById(R.id.txtTotalEnrollments);
        txtTotalQuizResults = findViewById(R.id.txtTotalQuizResults);
        txtTotalSubmissions = findViewById(R.id.txtTotalSubmissions);

        progressBar = findViewById(R.id.progressBar);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        loadReport();
    }

    private void loadReport() {

        progressBar.setVisibility(View.VISIBLE);

        apiService.getAdminReport()
                .enqueue(new Callback<AdminReport>() {

                    @Override
                    public void onResponse(
                            Call<AdminReport> call,
                            Response<AdminReport> response) {

                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful()
                                && response.body() != null) {

                            AdminReport report = response.body();

                            txtTotalStudents.setText(
                                    String.valueOf(
                                            report.getTotalStudents()
                                    )
                            );

                            txtTotalTeachers.setText(
                                    String.valueOf(
                                            report.getTotalTeachers()
                                    )
                            );

                            txtTotalCourses.setText(
                                    String.valueOf(
                                            report.getTotalCourses()
                                    )
                            );

                            txtTotalBatches.setText(
                                    String.valueOf(
                                            report.getTotalBatches()
                                    )
                            );

                            txtTotalEnrollments.setText(
                                    String.valueOf(
                                            report.getTotalEnrollments()
                                    )
                            );

                            txtTotalQuizResults.setText(
                                    String.valueOf(
                                            report.getTotalQuizResults()
                                    )
                            );

                            txtTotalSubmissions.setText(
                                    String.valueOf(
                                            report.getTotalSubmissions()
                                    )
                            );

                        } else {

                            Toast.makeText(
                                    AdminReportActivity.this,
                                    "Unable to load report",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }
                    }

                    @Override
                    public void onFailure(
                            Call<AdminReport> call,
                            Throwable t) {

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(
                                AdminReportActivity.this,
                                "Error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}