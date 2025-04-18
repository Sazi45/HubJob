package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordFailedActivity extends AppCompatActivity {

    private static final int REDIRECT_DELAY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_failed);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(ForgotPasswordFailedActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }, REDIRECT_DELAY);
    }
}
