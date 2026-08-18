package com.example.onlinecoachingapp.activities;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.StudyMaterialAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.StudyMaterial;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class TeacherStudyMaterialActivity extends AppCompatActivity {



    EditText edtTitle;

    Button btnSelectPdf,btnUpload;

    TextView txtFileName;


    RecyclerView recyclerMaterials;


    StudyMaterialAdapter adapter;


    ArrayList<StudyMaterial> materialList =
            new ArrayList<>();


    Uri pdfUri;



    ApiService apiService;



    Long courseId = 1L;



    // PDF picker

    ActivityResultLauncher<Intent> pdfPicker =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {


                        if(result.getResultCode()==RESULT_OK
                                && result.getData()!=null){


                            pdfUri =
                                    result.getData().getData();


                            txtFileName.setText(
                                    getFileName(pdfUri)
                            );


                        }


                    });





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teacher_study_material);

        edtTitle = findViewById(R.id.edtMaterialTitle);

        btnSelectPdf = findViewById(R.id.btnSelectPdf);

        btnUpload = findViewById(R.id.btnUpload);

        txtFileName = findViewById(R.id.txtFileName);

        recyclerMaterials = findViewById(R.id.recyclerMaterials);

        apiService =
                ApiClient.getRetrofitInstance(this)
                        .create(ApiService.class);

        recyclerMaterials.setLayoutManager(
                new LinearLayoutManager(this)
        );



        adapter =
                new StudyMaterialAdapter(
                        this,
                        materialList
                );
        recyclerMaterials.setAdapter(adapter);

        btnSelectPdf.setOnClickListener(v -> {
            Toast.makeText(
                    this,
                    "Select button clicked",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

            intent.setType("application/pdf");

            intent.addCategory(Intent.CATEGORY_OPENABLE);

            pdfPicker.launch(intent);

        });

        btnUpload.setOnClickListener(v -> {


            uploadPdf();


        });




        loadMaterials();


    }






    private void uploadPdf(){



        if(pdfUri==null){


            Toast.makeText(
                    this,
                    "Please select PDF",
                    Toast.LENGTH_SHORT
            ).show();


            return;
        }





        String title =
                edtTitle.getText()
                        .toString()
                        .trim();




        if(title.isEmpty()){


            edtTitle.setError(
                    "Enter Material Title"
            );


            return;

        }





        RequestBody titleBody =
                RequestBody.create(
                        MediaType.parse("text/plain"),
                        title
                );



        RequestBody courseBody =
                RequestBody.create(
                        MediaType.parse("text/plain"),
                        String.valueOf(courseId)
                );




        File file =
                com.example.onlinecoachingapp.utils.FileUtils
                        .getFile(
                                this,
                                pdfUri
                        );





        RequestBody fileBody =
                RequestBody.create(
                        MediaType.parse("application/pdf"),
                        file
                );




        MultipartBody.Part pdfPart =
                MultipartBody.Part.createFormData(
                        "file",
                        file.getName(),
                        fileBody
                );





        apiService.uploadStudyMaterial(
                        titleBody,
                        courseBody,
                        pdfPart
                )
                .enqueue(new Callback<String>() {


                    @Override
                    public void onResponse(
                            Call<String> call,
                            Response<String> response) {



                        if(response.isSuccessful()){


                            Toast.makeText(
                                    TeacherStudyMaterialActivity.this,
                                    "PDF Uploaded",
                                    Toast.LENGTH_SHORT
                            ).show();


                            edtTitle.setText("");

                            txtFileName.setText(
                                    "No File Selected"
                            );


                            pdfUri=null;


                            loadMaterials();


                        }
                        else{


                            Toast.makeText(
                                    TeacherStudyMaterialActivity.this,
                                    "Upload Failed : "
                                            +response.code(),
                                    Toast.LENGTH_SHORT
                            ).show();

                        }



                    }



                    @Override
                    public void onFailure(
                            Call<String> call,
                            Throwable t) {



                        Toast.makeText(
                                TeacherStudyMaterialActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();


                    }
                });



    }






    private void loadMaterials(){



        apiService.getMaterials(courseId)

                .enqueue(new Callback<ApiResponse<List<StudyMaterial>>>() {


                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<StudyMaterial>>> call,
                            Response<ApiResponse<List<StudyMaterial>>> response) {


                        if(response.isSuccessful()
                                && response.body()!=null){



                            materialList.clear();



                            materialList.addAll(
                                    response.body()
                                            .getData()
                            );



                            adapter.notifyDataSetChanged();


                        }



                    }



                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<StudyMaterial>>> call,
                            Throwable t) {


                        Toast.makeText(
                                TeacherStudyMaterialActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();


                    }
                });


    }






    private String getFileName(Uri uri){



        Cursor cursor =
                getContentResolver()
                        .query(
                                uri,
                                null,
                                null,
                                null,
                                null
                        );



        if(cursor!=null){


            int index =
                    cursor.getColumnIndex(
                            OpenableColumns.DISPLAY_NAME
                    );


            cursor.moveToFirst();


            String name =
                    cursor.getString(index);



            cursor.close();


            return name;

        }



        return "PDF";

    }


}