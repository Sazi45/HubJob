package com.example.hubjob;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hubjob.JobApplicationModel;
import com.example.hubjob.MyApplicationAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyApplications extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyApplicationAdapter adapter;
    private List<JobApplicationModel> applicationList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applications);

        recyclerView = findViewById(R.id.myApplicationsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.myApplicationsProgressBar);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        applicationList = new ArrayList<>();
        adapter = new MyApplicationAdapter(applicationList);
        recyclerView.setAdapter(adapter);

        loadMyApplications();
    }

    private void loadMyApplications() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "You need to log in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userEmail = currentUser.getEmail();
        progressBar.setVisibility(View.VISIBLE);

        db.collection("JobApplications")
                .whereEqualTo("userEmail", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful() && task.getResult() != null) {
                        applicationList.clear();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            JobApplicationModel application = doc.toObject(JobApplicationModel.class);
                            applicationList.add(application);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Failed to load applications", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
