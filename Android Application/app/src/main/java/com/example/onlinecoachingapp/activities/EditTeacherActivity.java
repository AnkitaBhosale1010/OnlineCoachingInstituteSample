package com.example.onlinecoachingapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Teacher;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTeacherActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etQualification;
    private EditText etExpertise;
    private EditText etJoinDate;

    private Button btnUpdate;

    private Teacher teacher;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teacher);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etQualification = findViewById(R.id.etQualification);
        etExpertise = findViewById(R.id.etExpertise);
        etJoinDate = findViewById(R.id.etJoinDate);

        btnUpdate = findViewById(R.id.btnUpdateTeacher);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        teacher = (Teacher) getIntent().getSerializableExtra("teacher");

        if (teacher == null) {
            Toast.makeText(this, "Teacher Not Found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (teacher.getUser() != null) {
            etName.setText(teacher.getUser().getName());
            etEmail.setText(teacher.getUser().getEmail());
        }

        etPhone.setText(teacher.getPhone());
        etQualification.setText(teacher.getQualification());
        etExpertise.setText(teacher.getExpertise());
        etJoinDate.setText(teacher.getJoinDate());

        etJoinDate.setOnClickListener(v -> showDatePicker());

        btnUpdate.setOnClickListener(v -> updateTeacher());
    }

    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {

                    String date = String.format(
                            "%04d-%02d-%02d",
                            year,
                            month + 1,
                            dayOfMonth
                    );

                    etJoinDate.setText(date);

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void updateTeacher() {

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
                teacher.getTeacherId(),
                teacher
        ).enqueue(new Callback<Teacher>() {

            @Override
            public void onResponse(Call<Teacher> call,
                                   Response<Teacher> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            EditTeacherActivity.this,
                            "Teacher Updated Successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();

                } else {

                    Toast.makeText(
                            EditTeacherActivity.this,
                            "Update Failed",
                            Toast.LENGTH_SHORT
                    ).show();

                }

            }

            @Override
            public void onFailure(Call<Teacher> call,
                                  Throwable t) {

                Toast.makeText(
                        EditTeacherActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

    }

}