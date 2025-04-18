package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordSuccessActivity extends AppCompatActivity {

    private static final int REDIRECT_DELAY = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_success);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(ForgotPasswordSuccessActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }, REDIRECT_DELAY);
    }
}
