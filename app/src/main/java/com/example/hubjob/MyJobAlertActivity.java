package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MyJobAlertActivity extends AppCompatActivity {

    private EditText editTextJobTitle, editTextSkills, editTextLocation;
    private Button buttonSave;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job_alert);

        editTextJobTitle = findViewById(R.id.editTextJobTitle);
        editTextSkills = findViewById(R.id.editTextSkills);
        editTextLocation = findViewById(R.id.editTextLocation);
        buttonSave = findViewById(R.id.buttonSave);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        buttonSave.setOnClickListener(view -> saveJobPreferences());
    }

    private void saveJobPreferences() {
        String jobTitle = editTextJobTitle.getText().toString().trim();
        String skills = editTextSkills.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String email = auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : null;

        if (email == null || jobTitle.isEmpty() || skills.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill all fields and login", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> jobPref = new HashMap<>();
        jobPref.put("email", email);
        jobPref.put("jobTitle", jobTitle);
        jobPref.put("skills", skills);
        jobPref.put("location", location);

        db.collection("jobPreferences").document(email)
                .set(jobPref)
                .addOnSuccessListener(aVoid -> {
                    startActivity(new Intent(MyJobAlertActivity.this, MyJobAlertSuccessActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    startActivity(new Intent(MyJobAlertActivity.this, MyJobAlertErrorActivity.class));
                    finish();
                });
    }
}
