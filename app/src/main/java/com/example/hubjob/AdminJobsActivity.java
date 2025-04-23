package com.example.hubjob;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminJobsActivity extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    AdminJobsAdapter adapter;
    List<JobPostModel> jobList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_jobs);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_admin_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminJobsAdapter(jobList);
        recyclerView.setAdapter(adapter);

        fetchJobs();
    }

    private void fetchJobs() {
        db.collection("PostedJobs")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(snapshot -> {
                    jobList.clear();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        JobPostModel job = doc.toObject(JobPostModel.class);
                        jobList.add(job);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Log.e("AdminJobsActivity", "Error fetching jobs", e));
    }
}
