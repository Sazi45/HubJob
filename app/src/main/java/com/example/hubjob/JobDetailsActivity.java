package com.example.hubjob;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class JobDetailsActivity extends AppCompatActivity {

    TextView jobTitleText, descriptionText, locationText, salaryText, experienceText, skillsText, categoryText, postedByText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        jobTitleText = findViewById(R.id.jobTitleText);
        descriptionText = findViewById(R.id.descriptionText);
        locationText = findViewById(R.id.locationText);
        salaryText = findViewById(R.id.salaryText);
        experienceText = findViewById(R.id.experienceText);
        skillsText = findViewById(R.id.skillsText);
        categoryText = findViewById(R.id.categoryText);
        postedByText = findViewById(R.id.postedByText);

        // Get data from Intent
        String jobTitle = getIntent().getStringExtra("jobTitle");
        String description = getIntent().getStringExtra("description");
        String location = getIntent().getStringExtra("location");
        String salary = getIntent().getStringExtra("salary");
        String experience = getIntent().getStringExtra("experience");
        String skills = getIntent().getStringExtra("skills");
        String category = getIntent().getStringExtra("category");
        String postedBy = getIntent().getStringExtra("postedBy");

        jobTitleText.setText(jobTitle);
        descriptionText.setText(description);
        locationText.setText(location);
        salaryText.setText(salary);
        experienceText.setText(experience);
        skillsText.setText(skills);
        categoryText.setText(category);
        postedByText.setText(postedBy);
    }
}
