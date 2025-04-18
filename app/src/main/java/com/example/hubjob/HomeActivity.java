package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    CardView cardJobSeeker, cardEmployer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardJobSeeker = findViewById(R.id.card_job_seeker);
        cardEmployer = findViewById(R.id.card_employer);

        cardJobSeeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, JobSeekerActivity.class);
                startActivity(intent);
            }
        });

//        cardEmployer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, EmployerActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
