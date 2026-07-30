package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onlinecoachingapp.R;

public class ResultActivity extends AppCompatActivity {

    TextView txtMarks;
    TextView txtCorrect;
    TextView txtWrong;
    TextView txtTotal;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtMarks = findViewById(R.id.txtMarks);
        txtCorrect = findViewById(R.id.txtCorrect);
        txtWrong = findViewById(R.id.txtWrong);
        txtTotal = findViewById(R.id.txtTotal);

        btnFinish = findViewById(R.id.btnFinish);

        int total = getIntent().getIntExtra("total",0);
        int correct = getIntent().getIntExtra("correct",0);
        int wrong = getIntent().getIntExtra("wrong",0);
        int marks = getIntent().getIntExtra("marks",0);

        txtTotal.setText("Total Questions : " + total);
        txtCorrect.setText("Correct Answers : " + correct);
        txtWrong.setText("Wrong Answers : " + wrong);
        txtMarks.setText("Marks Obtained : " + marks);

        btnFinish.setOnClickListener(v -> finish());
    }
}