package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostJobActivity extends AppCompatActivity {

    EditText jobTitle, description, location, salary, experience, skills, category;
    Button postButton;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        db = FirebaseFirestore.getInstance();

        jobTitle = findViewById(R.id.jobTitleField);
        description = findViewById(R.id.descriptionField);
        location = findViewById(R.id.locationField);
        salary = findViewById(R.id.salaryField);
        experience = findViewById(R.id.experienceField);
        skills = findViewById(R.id.skillsField);
        category = findViewById(R.id.categoryField);
        postButton = findViewById(R.id.postButton);

        postButton.setOnClickListener(v -> saveJobPost());
    }

    private void saveJobPost() {
        String jobTitleStr = jobTitle.getText().toString().trim();
        String descStr = description.getText().toString().trim();
        String locationStr = location.getText().toString().trim();
        String salaryStr = salary.getText().toString().trim();
        String experienceStr = experience.getText().toString().trim();
        String skillsStr = skills.getText().toString().trim();
        String categoryStr = category.getText().toString().trim();
        String postedBy = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        long timestamp = System.currentTimeMillis();

        if (jobTitleStr.isEmpty() || descStr.isEmpty() || locationStr.isEmpty() ||
                salaryStr.isEmpty() || experienceStr.isEmpty() || skillsStr.isEmpty() || categoryStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        JobPostModel job = new JobPostModel(
                jobTitleStr, descStr, locationStr, salaryStr, experienceStr, skillsStr, categoryStr, postedBy, timestamp
        );

        db.collection("PostedJobs")
                .add(job)
                .addOnSuccessListener(docRef -> {
                    Toast.makeText(this, "Job posted successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PostJobActivity.this, EmployerDashboardActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
