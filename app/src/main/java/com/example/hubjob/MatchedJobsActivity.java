package com.example.hubjob;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MatchedJobsActivity extends AppCompatActivity {

    private RecyclerView matchedJobsRecyclerView;
    private JobPostAdapter adapter;
    private List<JobPostModel> matchedJobs;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matched_jobs);

        matchedJobsRecyclerView = findViewById(R.id.matchedJobsRecyclerView);
        matchedJobsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        matchedJobs = new ArrayList<>();
        adapter = new JobPostAdapter(matchedJobs);
        matchedJobsRecyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadUserProfileAndFilterJobs();
    }

    private void loadUserProfileAndFilterJobs() {
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        db.collection("JobSeekerProfile")
                .whereEqualTo("email", currentUserEmail)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot userProfile = querySnapshot.getDocuments().get(0);

                        String userSkills = userProfile.getString("skills").toLowerCase(); // comma-separated
                        String userCity = userProfile.getString("city").toLowerCase();
                        String userCategory = userProfile.getString("preferredCategory").toLowerCase(); // optional

                        fetchAndFilterJobs(userSkills, userCity, userCategory);
                    } else {
                        Toast.makeText(this, "Profile not found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error loading profile.", Toast.LENGTH_SHORT).show());
    }

    private void fetchAndFilterJobs(String userSkills, String userCity, String userCategory) {
        db.collection("PostedJobs")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    matchedJobs.clear();

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        JobPostModel job = doc.toObject(JobPostModel.class);
                        if (job == null) continue;

                        boolean locationMatch = job.getLocation().toLowerCase().equals(userCity);
                        boolean categoryMatch = job.getCategory().toLowerCase().equals(userCategory);
                        boolean skillMatch = false;

                        for (String skill : userSkills.split(",")) {
                            if (job.getSkills().toLowerCase().contains(skill.trim())) {
                                skillMatch = true;
                                break;
                            }
                        }

                        if (locationMatch && skillMatch) {
                            matchedJobs.add(job);
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (matchedJobs.isEmpty()) {
                        Toast.makeText(this, "No matched jobs found.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
