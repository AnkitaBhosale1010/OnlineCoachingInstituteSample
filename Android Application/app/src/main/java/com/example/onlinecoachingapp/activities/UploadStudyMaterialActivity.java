package com.example.onlinecoachingapp.activities;

import android.app.ProgressDialog;
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

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.StudyMaterial;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadStudyMaterialActivity extends AppCompatActivity {

    private EditText etTitle;
    private TextView txtSelectedFile;

    private Button btnChoosePdf;
    private Button btnUpload;

    private Uri pdfUri;

    private Long courseId;

    private ApiService apiService;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_study_material);

        etTitle = findViewById(R.id.etTitle);
        txtSelectedFile = findViewById(R.id.txtSelectedFile);

        btnChoosePdf = findViewById(R.id.btnChoosePdf);
        btnUpload = findViewById(R.id.btnUpload);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Study Material...");
        progressDialog.setCancelable(false);


        Intent intent = getIntent();

        if (intent.hasExtra("courseId")) {

            courseId = intent.getLongExtra("courseId", -1);

        } else {

            courseId = (long) -1;

        }


        if (courseId == -1) {

            Toast.makeText(
                    this,
                    "Course not selected",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;

        }
        btnChoosePdf.setOnClickListener(v -> openPdfPicker());

        btnUpload.setOnClickListener(v -> uploadMaterial());

    }

    private final ActivityResultLauncher<Intent> pdfPickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK &&
                                result.getData() != null &&
                                result.getData().getData() != null) {

                            pdfUri = result.getData().getData();

                            getContentResolver().takePersistableUriPermission(
                                    pdfUri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );

                            txtSelectedFile.setText(getFileName(pdfUri));

                        }

                    });

    private void openPdfPicker() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setType("application/pdf");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

        pdfPickerLauncher.launch(intent);

    }

    private String getFileName(Uri uri) {

        String fileName = "selected.pdf";

        Cursor cursor = getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {

            int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

            if (cursor.moveToFirst() && index != -1) {

                fileName = cursor.getString(index);

            }

            cursor.close();

        }

        return fileName;

    }

    private File createTempFile(Uri uri) throws Exception {

        InputStream inputStream = getContentResolver().openInputStream(uri);

        File file = new File(getCacheDir(), getFileName(uri));

        FileOutputStream outputStream = new FileOutputStream(file);

        byte[] buffer = new byte[4096];

        int length;

        while ((length = inputStream.read(buffer)) != -1) {

            outputStream.write(buffer, 0, length);

        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();

        return file;

    }

    private void uploadMaterial() {

        String title = etTitle.getText().toString().trim();

        if (title.isEmpty()) {

            etTitle.setError("Enter Material Title");
            return;

        }

        if (pdfUri == null) {

            Toast.makeText(
                    this,
                    "Please choose a PDF first",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }

        if (courseId == null || courseId == -1) {

            Toast.makeText(
                    this,
                    "Course not selected",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }

        progressDialog.show();

        try {

            File file = createTempFile(pdfUri);

            RequestBody titleBody = RequestBody.create(
                    title,
                    MediaType.parse("text/plain")
            );

            RequestBody courseBody = RequestBody.create(
                    String.valueOf(courseId),
                    MediaType.parse("text/plain")
            );

            RequestBody fileBody = RequestBody.create(
                    file,
                    MediaType.parse("application/pdf")
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
            ).enqueue(new Callback<ApiResponse<StudyMaterial>>() {

                @Override
                public void onResponse(
                        Call<ApiResponse<StudyMaterial>> call,
                        Response<ApiResponse<StudyMaterial>> response) {

                    progressDialog.dismiss();

                    if (response.isSuccessful()
                            && response.body() != null
                            && response.body().isSuccess()) {

                        Toast.makeText(
                                UploadStudyMaterialActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                        finish();

                    } else {

                        Toast.makeText(
                                UploadStudyMaterialActivity.this,
                                "Upload Failed",
                                Toast.LENGTH_LONG
                        ).show();

                    }

                }

                @Override
                public void onFailure(
                        Call<ApiResponse<StudyMaterial>> call,
                        Throwable t) {

                    progressDialog.dismiss();

                    Toast.makeText(
                            UploadStudyMaterialActivity.this,
                            "Error : " + t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();

                }

            });

        } catch (Exception e) {

            progressDialog.dismiss();

            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();

            e.printStackTrace();

        }

    }

}