package com.example.onlinecoachingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onlinecoachingapp.R;

public class SplashActivity extends AppCompatActivity {

    // Splash screen duration (3 seconds)
    private static final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Wait for 3 seconds, then open LoginActivity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);

            // Close SplashActivity
            finish();

        }, SPLASH_TIME);
    }
}