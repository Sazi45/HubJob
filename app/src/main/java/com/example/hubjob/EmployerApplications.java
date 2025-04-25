package com.example.hubjob;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class EmployerApplications extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView noApplicationsText;
    private ApplicationsAdapter adapter;
    private List<JobApplicationModel> applicationList = new ArrayList<>();

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_applications);

        recyclerView = findViewById(R.id.recycler_view_applications);
        noApplicationsText = findViewById(R.id.tv_no_applications);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApplicationsAdapter(this, applicationList);
        recyclerView.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String currentUserEmail = auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : null;

        if (currentUserEmail != null) {
            db.collection("JobApplications")
                    .whereEqualTo("jobPostedBy", currentUserEmail)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (queryDocumentSnapshots.isEmpty()) {
                            noApplicationsText.setVisibility(View.VISIBLE);
                        } else {
                            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                                JobApplicationModel app = doc.toObject(JobApplicationModel.class);
                                applicationList.add(app);
                            }
                            adapter.notifyDataSetChanged();
                            noApplicationsText.setVisibility(View.GONE);
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed to load applications.", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Employer not logged in.", Toast.LENGTH_SHORT).show();
        }
    }
}
