package com.example.hubjob;

import android.content.Intent;
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

public class MySavedJobs extends AppCompatActivity implements JobPreferenceAdapter.OnJobClickListener {

    private RecyclerView recyclerView;
    private JobPreferenceAdapter adapter;
    private List<JobPreferenceModel> savedJobs;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_saved_jobs);

        recyclerView = findViewById(R.id.savedJobsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        savedJobs = new ArrayList<>();

        loadSavedJobs();
    }

    private void loadSavedJobs() {
        String currentUserEmail = auth.getCurrentUser() != null ?
                auth.getCurrentUser().getEmail() : "anonymous@example.com";

        db.collection("MyJobPreferences")
                .whereEqualTo("userEmail", currentUserEmail)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    savedJobs.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        JobPreferenceModel job = document.toObject(JobPreferenceModel.class);
                        savedJobs.add(job);
                    }

                    adapter = new JobPreferenceAdapter(savedJobs, this); // Pass listener here
                    recyclerView.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MySavedJobs.this, "Failed to load saved jobs.", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onJobClick(JobPreferenceModel job) {
        Intent intent = new Intent(this, JobDetailsActivity.class);
        intent.putExtra("jobTitle", job.getJobTitle());
        intent.putExtra("description", job.getDescription());
        intent.putExtra("location", job.getLocation());
        intent.putExtra("salary", job.getSalary());
        intent.putExtra("experience", job.getExperience());
        intent.putExtra("skills", job.getSkills());
        intent.putExtra("category", job.getCategory());
        intent.putExtra("postedBy", job.getPostedBy());
        startActivity(intent);
    }
}
