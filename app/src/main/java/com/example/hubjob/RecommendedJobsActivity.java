package com.example.hubjob;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecommendedJobsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecommendedJobsAdapter adapter;
    List<JobPostModel> recommendedJobs = new ArrayList<>();
    FirebaseFirestore db;
    String userCity, userSkills;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_jobs);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recommendedJobsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecommendedJobsAdapter(recommendedJobs);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // Load user profile data
        loadUserProfile();
    }

    // Method to load the user profile (city and skills)
    private void loadUserProfile() {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        db.collection("JobSeekerProfile")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!snapshot.isEmpty()) {
                        for (QueryDocumentSnapshot doc : snapshot) {
                            userCity = doc.getString("city");
                            userSkills = doc.getString("skills");
                            // Once user profile is loaded, fetch recommended jobs
                            fetchRecommendedJobs();
                        }
                    } else {
                        Toast.makeText(this, "Profile not found", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method to fetch recommended jobs based on city and skills
    private void fetchRecommendedJobs() {
        db.collection("PostedJobs")
                .get()
                .addOnSuccessListener(snapshot -> {
                    recommendedJobs.clear();  // Clear previous job data

                    // Loop through all posted jobs and filter based on user's city and skills
                    for (QueryDocumentSnapshot doc : snapshot) {
                        String jobCity = doc.getString("location");
                        String jobSkills = doc.getString("skills");

                        // Filter jobs: Check if location matches and skills also match
                        if (userCity != null && jobCity != null && jobCity.equalsIgnoreCase(userCity)) {
                            // If the job is in the same city, check if skills match
                            if (userSkills != null && jobSkills != null && jobSkills.toLowerCase().contains(userSkills.toLowerCase())) {
                                JobPostModel job = doc.toObject(JobPostModel.class);
                                recommendedJobs.add(job);  // Add the matching job to the list
                            }
                        }
                    }

                    // If no jobs are found, show a message
                    if (recommendedJobs.isEmpty()) {
                        Toast.makeText(this, "No recommended jobs found", Toast.LENGTH_SHORT).show();
                    }

                    // Notify the adapter to update the view
                    adapter.notifyDataSetChanged();
                });
    }
}
