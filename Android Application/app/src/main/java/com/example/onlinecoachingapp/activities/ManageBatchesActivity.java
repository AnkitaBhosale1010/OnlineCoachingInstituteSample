package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.BatchAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Batch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ManageBatchesActivity extends AppCompatActivity
        implements BatchAdapter.OnBatchActionListener {


    private RecyclerView recyclerBatches;

    private FloatingActionButton fabAddBatch;

    private Button btnAssignStudent;


    private BatchAdapter adapter;

    private List<Batch> batchList;


    private ApiService apiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_batches);



        recyclerBatches =
                findViewById(R.id.recyclerBatches);



        fabAddBatch =
                findViewById(R.id.fabAddBatch);



        btnAssignStudent =
                findViewById(R.id.btnAssignStudent);




        recyclerBatches.setLayoutManager(
                new LinearLayoutManager(this)
        );



        batchList = new ArrayList<>();



        adapter = new BatchAdapter(
                this,
                batchList,
                this
        );


        recyclerBatches.setAdapter(adapter);




        apiService =
                ApiClient
                        .getRetrofitInstance(this)
                        .create(ApiService.class);




        fabAddBatch.setOnClickListener(v -> {


            Intent intent =
                    new Intent(
                            ManageBatchesActivity.this,
                            CreateBatchActivity.class
                    );


            startActivity(intent);


        });





        btnAssignStudent.setOnClickListener(v -> {


            Intent intent =
                    new Intent(
                            ManageBatchesActivity.this,
                            AssignStudentBatchActivity.class
                    );


            startActivity(intent);


        });





        loadBatches();

    }





    @Override
    protected void onResume() {

        super.onResume();

        loadBatches();

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


                            adapter.notifyDataSetChanged();


                        }
                        else {


                            Toast.makeText(
                                    ManageBatchesActivity.this,
                                    "Unable to load batches",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }


                    }





                    @Override
                    public void onFailure(
                            Call<List<Batch>> call,
                            Throwable t) {


                        Toast.makeText(
                                ManageBatchesActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();


                    }

                });


    }






    @Override
    public void onEdit(Batch batch) {


        Intent intent =
                new Intent(
                        this,
                        EditBatchActivity.class
                );


        intent.putExtra(
                "batch",
                batch
        );


        startActivity(intent);


    }







    @Override
    public void onDelete(Batch batch) {


        new AlertDialog.Builder(this)

                .setTitle("Delete Batch")

                .setMessage("Are you sure?")

                .setPositiveButton(
                        "Yes",
                        (dialog, which) ->
                                deleteBatch(batch)
                )

                .setNegativeButton(
                        "No",
                        null
                )

                .show();


    }






    private void deleteBatch(Batch batch){


        apiService.deleteBatch(
                        batch.getId()
                )

                .enqueue(new Callback<String>() {


                    @Override
                    public void onResponse(
                            Call<String> call,
                            Response<String> response) {


                        if(response.isSuccessful()){


                            Toast.makeText(
                                    ManageBatchesActivity.this,
                                    "Batch Deleted Successfully",
                                    Toast.LENGTH_SHORT
                            ).show();


                            loadBatches();


                        }
                        else {


                            Toast.makeText(
                                    ManageBatchesActivity.this,
                                    "Delete Failed",
                                    Toast.LENGTH_SHORT
                            ).show();


                        }


                    }





                    @Override
                    public void onFailure(
                            Call<String> call,
                            Throwable t) {


                        Toast.makeText(
                                ManageBatchesActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();


                    }

                });


    }






    @Override
    public void onViewStudents(Batch batch) {


        Intent intent =
                new Intent(
                        this,
                        BatchStudentsActivity.class
                );


        intent.putExtra(
                "batchId",
                batch.getId()
        );


        startActivity(intent);


    }


}