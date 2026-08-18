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
import com.example.onlinecoachingapp.model.BatchDto;
import com.example.onlinecoachingapp.model.BatchResponseDto;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateBatchActivity extends AppCompatActivity {


    private EditText etBatchName;
    private EditText etTrainerName;
    private EditText etStartDate;
    private EditText etEndDate;
    private EditText etStatus;


    private Button btnCreateBatch;


    private ApiService apiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_batch);



        etBatchName =
                findViewById(R.id.etBatchName);


        etTrainerName =
                findViewById(R.id.etTrainerName);


        etStartDate =
                findViewById(R.id.etStartDate);


        etEndDate =
                findViewById(R.id.etEndDate);


        etStatus =
                findViewById(R.id.etStatus);



        btnCreateBatch =
                findViewById(R.id.btnCreateBatch);



        apiService =
                ApiClient
                        .getRetrofitInstance(this)
                        .create(ApiService.class);



        etStartDate.setOnClickListener(v ->
                showDatePicker(etStartDate)
        );


        etEndDate.setOnClickListener(v ->
                showDatePicker(etEndDate)
        );



        btnCreateBatch.setOnClickListener(v ->
                createBatch()
        );

    }




    private void showDatePicker(EditText editText) {


        Calendar calendar =
                Calendar.getInstance();



        DatePickerDialog dialog =
                new DatePickerDialog(

                        this,

                        (view, year, month, dayOfMonth) -> {


                            String date =
                                    String.format(
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




    private void createBatch() {


        String batchName =
                etBatchName.getText()
                        .toString()
                        .trim();


        String trainerName =
                etTrainerName.getText()
                        .toString()
                        .trim();


        String startDate =
                etStartDate.getText()
                        .toString()
                        .trim();


        String endDate =
                etEndDate.getText()
                        .toString()
                        .trim();


        String status =
                etStatus.getText()
                        .toString()
                        .trim();



        if(batchName.isEmpty()
                || trainerName.isEmpty()
                || startDate.isEmpty()
                || endDate.isEmpty()
                || status.isEmpty()) {


            Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
            ).show();


            return;

        }

        BatchDto batchDto =
                new BatchDto();


        batchDto.setBatchName(batchName);

        batchDto.setTrainerName(trainerName);

        batchDto.setStartDate(startDate);

        batchDto.setEndDate(endDate);

        batchDto.setStatus(status);


        apiService.createBatch(batchDto)

                .enqueue(new Callback<BatchResponseDto>() {



                    @Override
                    public void onResponse(
                            Call<BatchResponseDto> call,
                            Response<BatchResponseDto> response) {



                        if(response.isSuccessful()
                                && response.body()!=null) {



                            Toast.makeText(
                                    CreateBatchActivity.this,
                                    "Batch Created Successfully",
                                    Toast.LENGTH_SHORT
                            ).show();



                            finish();



                        }
                        else {


                            Toast.makeText(
                                    CreateBatchActivity.this,
                                    "Failed To Create Batch",
                                    Toast.LENGTH_SHORT
                            ).show();


                        }

                    }

                    @Override
                    public void onFailure(
                            Call<BatchResponseDto> call,
                            Throwable t) {

                        Toast.makeText(
                                CreateBatchActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

}