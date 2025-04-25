package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManageJobPreferenceActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private TextView textViewJobTitle, textViewSkills, textViewLocation;
    private ImageView deleteIcon;  // ImageView for delete icon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_job_preference);

        textViewJobTitle = findViewById(R.id.textViewJobTitle);
        textViewSkills = findViewById(R.id.textViewSkills);
        textViewLocation = findViewById(R.id.textViewLocation);
        deleteIcon = findViewById(R.id.deleteIcon);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        fetchJobPreferences();

        deleteIcon.setOnClickListener(v -> deleteJobPreferences());
    }

    private void fetchJobPreferences() {
        String email = auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : null;

        if (email == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("jobPreferences").document(email)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String jobTitle = documentSnapshot.getString("jobTitle");
                        String skills = documentSnapshot.getString("skills");
                        String location = documentSnapshot.getString("location");

                        textViewJobTitle.setText("Job Title: " + jobTitle);
                        textViewSkills.setText("Skills: " + skills);
                        textViewLocation.setText("Location: " + location);
                    } else {
                        Toast.makeText(this, "No preferences found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load preferences", Toast.LENGTH_SHORT).show());
    }

    private void deleteJobPreferences() {
        String email = auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : null;

        if (email == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("jobPreferences").document(email)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    textViewJobTitle.setText("");
                    textViewSkills.setText("");
                    textViewLocation.setText("");

                    Toast.makeText(this, "Preferences deleted successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ManageJobPreferenceActivity.this, DeleteSuccessActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete preferences", Toast.LENGTH_SHORT).show());
    }
}
