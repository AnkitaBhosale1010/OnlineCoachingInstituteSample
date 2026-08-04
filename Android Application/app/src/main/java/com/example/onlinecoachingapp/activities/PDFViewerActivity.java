package com.example.onlinecoachingapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinecoachingapp.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDFViewerActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdfView);

        String path = getIntent().getStringExtra("path");

        pdfView.fromFile(new File(path))
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .load();

    }
}