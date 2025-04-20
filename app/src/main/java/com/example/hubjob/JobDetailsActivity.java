package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class JobDetailsActivity extends AppCompatActivity {

    TextView jobTitleText, descriptionText, locationText, salaryText, experienceText, skillsText, categoryText, postedByText;
    Button ApplyButton, SaveJobButton;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    String jobTitle, description, location, salary, experience, skills, category, postedBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        jobTitleText = findViewById(R.id.jobTitleText);
        descriptionText = findViewById(R.id.descriptionText);
        locationText = findViewById(R.id.locationText);
        salaryText = findViewById(R.id.salaryText);
        experienceText = findViewById(R.id.experienceText);
        skillsText = findViewById(R.id.skillsText);
        categoryText = findViewById(R.id.categoryText);
        postedByText = findViewById(R.id.postedByText);
        ApplyButton = findViewById(R.id.applyNowButton);
        SaveJobButton = findViewById(R.id.saveJobButton);

        // Get data from intent
        jobTitle = getIntent().getStringExtra("jobTitle");
        description = getIntent().getStringExtra("description");
        location = getIntent().getStringExtra("location");
        salary = getIntent().getStringExtra("salary");
        experience = getIntent().getStringExtra("experience");
        skills = getIntent().getStringExtra("skills");
        category = getIntent().getStringExtra("category");
        postedBy = getIntent().getStringExtra("postedBy");

        // Set text
        jobTitleText.setText(jobTitle);
        descriptionText.setText(description);
        locationText.setText(location);
        salaryText.setText(salary);
        experienceText.setText(experience);
        skillsText.setText(skills);
        categoryText.setText(category);
        postedByText.setText(postedBy);

        // Save job logic with duplicate prevention
        SaveJobButton.setOnClickListener(v -> {
            String currentUserEmail = auth.getCurrentUser() != null ?
                    auth.getCurrentUser().getEmail() : "anonymous@example.com";

            db.collection("MyJobPreferences")
                    .whereEqualTo("userEmail", currentUserEmail)
                    .whereEqualTo("jobTitle", jobTitle)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(JobDetailsActivity.this, "You’ve already saved this job.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(JobDetailsActivity.this, MySavedJobs.class));
                            finish();
                        } else {
                            JobPreferenceModel jobPreference = new JobPreferenceModel(
                                    currentUserEmail, jobTitle, description, location, salary,
                                    experience, skills, category, postedBy, System.currentTimeMillis()
                            );

                            db.collection("MyJobPreferences")
                                    .add(jobPreference)
                                    .addOnSuccessListener(documentReference ->
                                            Toast.makeText(JobDetailsActivity.this, "Job saved to preferences!", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e ->
                                            Toast.makeText(JobDetailsActivity.this, "Failed to save job.", Toast.LENGTH_SHORT).show());
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(JobDetailsActivity.this, "Error checking saved jobs.", Toast.LENGTH_SHORT).show());
        });

        // Apply button logic
        ApplyButton.setOnClickListener(v -> {
            String currentUserEmail = auth.getCurrentUser() != null ?
                    auth.getCurrentUser().getEmail() : null;

            if (currentUserEmail == null) {
                Toast.makeText(JobDetailsActivity.this, "You need to log in to apply.", Toast.LENGTH_SHORT).show();
                return;
            }

            db.collection("JobApplications")
                    .whereEqualTo("userEmail", currentUserEmail)
                    .whereEqualTo("jobTitle", jobTitle)
                    .get()
                    .addOnSuccessListener(applicationSnapshot -> {
                        if (!applicationSnapshot.isEmpty()) {
                            Toast.makeText(JobDetailsActivity.this, "You’ve already applied for this job.", Toast.LENGTH_SHORT).show();
                        } else {
                            db.collection("JobSeekerProfile")
                                    .whereEqualTo("email", currentUserEmail)
                                    .get()
                                    .addOnSuccessListener(profileSnapshot -> {
                                        if (!profileSnapshot.isEmpty()) {
                                            var profileDoc = profileSnapshot.getDocuments().get(0);
                                            String firstName = profileDoc.getString("firstName");
                                            String lastName = profileDoc.getString("lastName");
                                            String gender = profileDoc.getString("gender");
                                            String education = profileDoc.getString("highestEducation");
                                            String race = profileDoc.getString("race");
                                            String userSkills = profileDoc.getString("skills");
                                            String city = profileDoc.getString("city");
                                            String postalCode = profileDoc.getString("postalCode");

                                            JobApplicationModel application = new JobApplicationModel(
                                                    currentUserEmail,
                                                    firstName,
                                                    lastName,
                                                    gender,
                                                    education,
                                                    race,
                                                    userSkills,
                                                    city,
                                                    postalCode,
                                                    jobTitle,
                                                    description,
                                                    location,
                                                    salary,
                                                    experience,
                                                    category,
                                                    postedBy,
                                                    System.currentTimeMillis()
                                            );

                                            db.collection("JobApplications")
                                                    .add(application)
                                                    .addOnSuccessListener(docRef ->
                                                            Toast.makeText(JobDetailsActivity.this, "Application submitted successfully!", Toast.LENGTH_SHORT).show())
                                                    .addOnFailureListener(e ->
                                                            Toast.makeText(JobDetailsActivity.this, "Failed to submit application.", Toast.LENGTH_SHORT).show());
                                        } else {
                                            Toast.makeText(JobDetailsActivity.this, "User profile not found.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(JobDetailsActivity.this, "Error fetching user profile.", Toast.LENGTH_SHORT).show());
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(JobDetailsActivity.this, "Error checking application status.", Toast.LENGTH_SHORT).show());
        });
    }
}
