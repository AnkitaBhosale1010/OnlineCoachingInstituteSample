package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Assignment;
import com.example.onlinecoachingapp.model.AssignmentRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateAssignmentActivity extends AppCompatActivity {


    EditText edtTitle, edtDescription, edtDeadline, edtMarks;
    Button btnCreate;

    ApiService apiService;

    Long courseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);


        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        edtDeadline = findViewById(R.id.edtDeadline);
        edtMarks = findViewById(R.id.edtMarks);

        btnCreate = findViewById(R.id.btnCreate);



        courseId = getIntent()
                .getLongExtra("courseId", -1);



        if(courseId == -1){

            Toast.makeText(
                    this,
                    "Course ID missing",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }



        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);



        btnCreate.setOnClickListener(v -> {


            String title = edtTitle.getText()
                    .toString()
                    .trim();

            String description = edtDescription.getText()
                    .toString()
                    .trim();

            String deadline = edtDeadline.getText()
                    .toString()
                    .trim();

            String marksText = edtMarks.getText()
                    .toString()
                    .trim();



            if(title.isEmpty() ||
                    description.isEmpty() ||
                    deadline.isEmpty() ||
                    marksText.isEmpty()){


                Toast.makeText(
                        this,
                        "Fill all fields",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }



            AssignmentRequest request =
                    new AssignmentRequest(
                            title,
                            description,
                            deadline
                    );



            apiService.createAssignment(
                            courseId,
                            request
                    )
                    .enqueue(new Callback<ApiResponse<Assignment>>() {


                        @Override
                        public void onResponse(
                                Call<ApiResponse<Assignment>> call,
                                Response<ApiResponse<Assignment>> response) {


                            Log.e("CREATE_ASSIGNMENT",
                                    "Code : " + response.code());


                            if(response.body()!=null){

                                Log.e("CREATE_ASSIGNMENT",
                                        "Message : " + response.body().getMessage());
                            }


                            if(response.isSuccessful()){


                                Toast.makeText(
                                        CreateAssignmentActivity.this,
                                        "Assignment Created",
                                        Toast.LENGTH_SHORT
                                ).show();

                                finish();

                            }
                            else{


                                Toast.makeText(
                                        CreateAssignmentActivity.this,
                                        "Error : "+response.code(),
                                        Toast.LENGTH_SHORT
                                ).show();

                            }

                        }
                        @Override
                        public void onFailure(
                                Call<ApiResponse<Assignment>> call,
                                Throwable t) {


                            Toast.makeText(
                                    CreateAssignmentActivity.this,
                                    t.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();

                        }

                    });


        });


    }

}