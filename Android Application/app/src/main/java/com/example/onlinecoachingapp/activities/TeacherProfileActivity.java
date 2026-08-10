package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Teacher;
import com.example.onlinecoachingapp.model.User;
import com.example.onlinecoachingapp.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherProfileActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etQualification;
    private EditText etExpertise;
    private EditText etJoinDate;

    private Button btnUpdateProfile;

    private ApiService apiService;
    private SessionManager sessionManager;

    private Teacher teacher;

    private Long teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etQualification = findViewById(R.id.etQualification);
        etExpertise = findViewById(R.id.etExpertise);
        etJoinDate = findViewById(R.id.etJoinDate);

        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        sessionManager = new SessionManager(this);

        teacherId = sessionManager.getTeacherId();

        if (teacherId == null) {

            Toast.makeText(
                    this,
                    "Teacher not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        loadProfile();

        btnUpdateProfile.setOnClickListener(v -> updateProfile());

    }

    private void loadProfile() {

        apiService.getTeacherById(teacherId)
                .enqueue(new Callback<Teacher>() {

                    @Override
                    public void onResponse(
                            Call<Teacher> call,
                            Response<Teacher> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            teacher = response.body();

                            if (teacher.getUser() != null) {

                                etName.setText(
                                        teacher.getUser().getName());

                                etEmail.setText(
                                        teacher.getUser().getEmail());
                            }

                            etPhone.setText(
                                    teacher.getPhone());

                            etQualification.setText(
                                    teacher.getQualification());

                            etExpertise.setText(
                                    teacher.getExpertise());

                            etJoinDate.setText(
                                    teacher.getJoinDate());

                        } else {

                            Toast.makeText(
                                    TeacherProfileActivity.this,
                                    "Unable to load profile",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<Teacher> call,
                            Throwable t) {

                        Toast.makeText(
                                TeacherProfileActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

    private void updateProfile() {

        if (teacher == null) {
            return;
        }

        if (teacher.getUser() == null) {
            teacher.setUser(new User());
        }

        teacher.getUser().setName(
                etName.getText().toString().trim());

        teacher.getUser().setEmail(
                etEmail.getText().toString().trim());

        teacher.setPhone(
                etPhone.getText().toString().trim());

        teacher.setQualification(
                etQualification.getText().toString().trim());

        teacher.setExpertise(
                etExpertise.getText().toString().trim());

        teacher.setJoinDate(
                etJoinDate.getText().toString().trim());

        apiService.updateTeacher(
                teacherId,
                teacher
        ).enqueue(new Callback<Teacher>() {

            @Override
            public void onResponse(
                    Call<Teacher> call,
                    Response<Teacher> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            TeacherProfileActivity.this,
                            "Profile Updated Successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                } else {

                    Toast.makeText(
                            TeacherProfileActivity.this,
                            "Update Failed",
                            Toast.LENGTH_SHORT
                    ).show();

                }

            }

            @Override
            public void onFailure(
                    Call<Teacher> call,
                    Throwable t) {

                Toast.makeText(
                        TeacherProfileActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

    }

}