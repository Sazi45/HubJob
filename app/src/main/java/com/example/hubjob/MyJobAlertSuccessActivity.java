package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class MyJobAlertSuccessActivity extends AppCompatActivity {

    private static final int DELAY_MILLIS = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job_alert_success);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MyJobAlertSuccessActivity.this, JobSeekerDashboardActivity.class);
            startActivity(intent);
            finish();
        }, DELAY_MILLIS);
    }
}
