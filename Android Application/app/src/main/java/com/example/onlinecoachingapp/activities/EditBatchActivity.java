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
import com.example.onlinecoachingapp.model.Batch;
import com.example.onlinecoachingapp.model.BatchResponseDto;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBatchActivity extends AppCompatActivity {

    private EditText etBatchName;
    private EditText etTrainerName;
    private EditText etStartDate;
    private EditText etEndDate;
    private EditText etStatus;

    private Button btnUpdateBatch;

    private ApiService apiService;

    private Batch batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_batch);

        etBatchName = findViewById(R.id.etBatchName);
        etTrainerName = findViewById(R.id.etTrainerName);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etStatus = findViewById(R.id.etStatus);

        btnUpdateBatch = findViewById(R.id.btnUpdateBatch);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        batch = (Batch) getIntent().getSerializableExtra("batch");

        if(batch == null) {
            Toast.makeText(
                    this,
                    "Batch Not Found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        etBatchName.setText(batch.getBatchName());
        etTrainerName.setText(batch.getTrainerName());
        etStartDate.setText(batch.getStartDate());
        etEndDate.setText(batch.getEndDate());
        etStatus.setText(batch.getStatus());

        etStartDate.setOnClickListener(v ->
                showDatePicker(etStartDate));

        etEndDate.setOnClickListener(v ->
                showDatePicker(etEndDate));

        btnUpdateBatch.setOnClickListener(v ->
                updateBatch());
    }


    private void showDatePicker(EditText editText) {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog =
                new DatePickerDialog(
                        this,
                        (view, year, month, dayOfMonth) -> {

                            String date = String.format(
                                    "%04d-%02d-%02d",
                                    year,
                                    month + 1,
                                    dayOfMonth
                            );

                            editText.setText(date);

                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

        dialog.show();
    }


    private void updateBatch() {

        batch.setBatchName(
                etBatchName.getText().toString().trim()
        );

        batch.setTrainerName(
                etTrainerName.getText().toString().trim()
        );

        batch.setStartDate(
                etStartDate.getText().toString().trim()
        );

        batch.setEndDate(
                etEndDate.getText().toString().trim()
        );

        batch.setStatus(
                etStatus.getText().toString().trim()
        );


        apiService.updateBatch(
                batch.getId(),
                batch
        ).enqueue(new Callback<Batch>() {

            @Override
            public void onResponse(
                    Call<Batch> call,
                    Response<Batch> response) {

                if(response.isSuccessful()) {

                    Toast.makeText(
                            EditBatchActivity.this,
                            "Batch Updated Successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();

                } else {

                    Toast.makeText(
                            EditBatchActivity.this,
                            "Update Failed",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }


            @Override
            public void onFailure(
                    Call<Batch> call,
                    Throwable t) {

                Toast.makeText(
                        EditBatchActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}