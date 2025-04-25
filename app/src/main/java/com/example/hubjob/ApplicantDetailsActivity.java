package com.example.hubjob;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ApplicantDetailsActivity extends AppCompatActivity {

    private JobApplicationModel application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_details);

        application = (JobApplicationModel) getIntent().getSerializableExtra("application_data");

        if (application != null) {
            ((TextView) findViewById(R.id.tv_full_name)).setText(application.getFirstName() + " " + application.getLastName());
            ((TextView) findViewById(R.id.tv_email)).setText(application.getUserEmail());
            ((TextView) findViewById(R.id.tv_gender)).setText(application.getGender());
            ((TextView) findViewById(R.id.tv_race)).setText(application.getRace());
            ((TextView) findViewById(R.id.tv_education)).setText(application.getHighestEducation());
            ((TextView) findViewById(R.id.tv_skills)).setText(application.getSkills());
            ((TextView) findViewById(R.id.tv_city)).setText(application.getCity());
            ((TextView) findViewById(R.id.tv_postal)).setText(application.getPostalCode());
            ((TextView) findViewById(R.id.tv_job_title)).setText(application.getJobTitle());
            ((TextView) findViewById(R.id.tv_description)).setText(application.getJobDescription());
            ((TextView) findViewById(R.id.tv_location)).setText(application.getJobLocation());
            ((TextView) findViewById(R.id.tv_salary)).setText(application.getJobSalary());
            ((TextView) findViewById(R.id.tv_experience)).setText(application.getJobExperience());
            ((TextView) findViewById(R.id.tv_category)).setText(application.getJobCategory());
            ((TextView) findViewById(R.id.tv_posted_by)).setText(application.getJobPostedBy());
        }
    }
}
