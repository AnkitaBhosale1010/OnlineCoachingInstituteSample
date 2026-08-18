package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.AssignStudentBatchRequest;
import com.example.onlinecoachingapp.model.Batch;
import com.example.onlinecoachingapp.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignStudentBatchActivity extends AppCompatActivity {

    private Spinner spinnerStudent;
    private Spinner spinnerBatch;

    private Button btnAssign;

    private ApiService apiService;

    private List<Student> studentList;
    private List<Batch> batchList;

    private HashMap<String, Long> studentMap;
    private HashMap<String, Long> batchMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assign_student_batch);


        spinnerStudent =
                findViewById(R.id.spinnerStudent);

        spinnerBatch =
                findViewById(R.id.spinnerBatch);


        btnAssign =
                findViewById(R.id.btnAssign);


        apiService =
                ApiClient
                        .getRetrofitInstance(this)
                        .create(ApiService.class);


        studentList = new ArrayList<>();
        batchList = new ArrayList<>();

        studentMap = new HashMap<>();
        batchMap = new HashMap<>();


        loadStudents();

        loadBatches();


        btnAssign.setOnClickListener(v -> assignStudent());

    }



    private void loadStudents(){

        apiService.getAllStudents()
                .enqueue(new Callback<ApiResponse<List<Student>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<Student>>> call,
                            Response<ApiResponse<List<Student>>> response) {


                        if(response.isSuccessful()
                                && response.body()!=null){


                            studentList.clear();

                            studentList.addAll(
                                    response.body().getData()
                            );


                            List<String> names =
                                    new ArrayList<>();


                            for(Student student : studentList){

                                String name = "Student";

                                if(student.getUser()!=null){

                                    name =
                                            student.getUser().getName();

                                }


                                names.add(name);

                                studentMap.put(
                                        name,
                                        student.getStudentId()
                                );

                            }


                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<>(
                                            AssignStudentBatchActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                                    names
                                    );


                            spinnerStudent.setAdapter(adapter);

                        }

                    }


                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<Student>>> call,
                            Throwable t) {


                        Toast.makeText(
                                AssignStudentBatchActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }



    private void loadBatches(){

        apiService.getAllBatches()
                .enqueue(new Callback<List<Batch>>() {


                    @Override
                    public void onResponse(
                            Call<List<Batch>> call,
                            Response<List<Batch>> response) {


                        if(response.isSuccessful()
                                && response.body()!=null){


                            batchList.clear();

                            batchList.addAll(
                                    response.body()
                            );


                            List<String> names =
                                    new ArrayList<>();


                            for(Batch batch : batchList){

                                String name =
                                        batch.getBatchName();


                                names.add(name);


                                batchMap.put(
                                        name,
                                        batch.getId()
                                );

                            }


                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<>(
                                            AssignStudentBatchActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item,
                                            names
                                    );


                            spinnerBatch.setAdapter(adapter);

                        }

                    }


                    @Override
                    public void onFailure(
                            Call<List<Batch>> call,
                            Throwable t) {


                        Toast.makeText(
                                AssignStudentBatchActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }



    private void assignStudent(){


        String studentName =
                spinnerStudent.getSelectedItem()
                        .toString();


        String batchName =
                spinnerBatch.getSelectedItem()
                        .toString();



        Long studentId =
                studentMap.get(studentName);


        Long batchId =
                batchMap.get(batchName);



        AssignStudentBatchRequest request =
                new AssignStudentBatchRequest();


        request.setStudentId(studentId);

        request.setBatchId(batchId);



        apiService.assignStudentToBatch(request)
                .enqueue(new Callback<Student>() {


                    @Override
                    public void onResponse(
                            Call<Student> call,
                            Response<Student> response) {


                        if(response.isSuccessful()){


                            Toast.makeText(
                                    AssignStudentBatchActivity.this,
                                    "Student Assigned Successfully",
                                    Toast.LENGTH_SHORT
                            ).show();


                            finish();


                        }
                        else{


                            Toast.makeText(
                                    AssignStudentBatchActivity.this,
                                    "Assignment Failed",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }


                    @Override
                    public void onFailure(
                            Call<Student> call,
                            Throwable t) {


                        Toast.makeText(
                                AssignStudentBatchActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

}