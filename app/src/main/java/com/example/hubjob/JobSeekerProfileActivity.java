package com.example.hubjob;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JobSeekerProfileActivity extends AppCompatActivity {

    EditText firstName, lastName, gender, race, city, suburb, postalCode, skills, education;
    Button saveButton, uploadCVButton;
    FirebaseFirestore db;
    String profileDocId = null;
    Uri selectedCVUri = null;
    DBHelper dbHelper;

    // For handling result of file picker
    ActivityResultLauncher<Intent> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_profile);

        db = FirebaseFirestore.getInstance();
        dbHelper = new DBHelper(this);

        // Bind UI elements
        firstName = findViewById(R.id.firstNameField);
        lastName = findViewById(R.id.lastNameField);
        gender = findViewById(R.id.genderField);
        race = findViewById(R.id.raceField);
        city = findViewById(R.id.cityField);
        suburb = findViewById(R.id.suburbField);
        postalCode = findViewById(R.id.postalCodeField);
        skills = findViewById(R.id.skillsField);
        education = findViewById(R.id.highestEducationField);
        saveButton = findViewById(R.id.saveButton);
        uploadCVButton = findViewById(R.id.uploadCVButton);

        loadJobSeekerProfile();

        saveButton.setOnClickListener(v -> saveProfile());
        uploadCVButton.setOnClickListener(v -> openFilePicker());

        // Initialize file picker launcher
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedCVUri = result.getData().getData();
                        Toast.makeText(this, "CV Selected!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadJobSeekerProfile() {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        db.collection("JobSeekerProfile")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!snapshot.isEmpty()) {
                        for (QueryDocumentSnapshot doc : snapshot) {
                            profileDocId = doc.getId();
                            firstName.setText(doc.getString("firstName"));
                            lastName.setText(doc.getString("lastName"));
                            gender.setText(doc.getString("gender"));
                            race.setText(doc.getString("race"));
                            city.setText(doc.getString("city"));
                            suburb.setText(doc.getString("suburb"));
                            postalCode.setText(doc.getString("postalCode"));
                            skills.setText(doc.getString("skills"));
                            education.setText(doc.getString("highestEducation"));
                        }
                    }
                });
    }

    private void saveProfile() {
        String firstNameStr = firstName.getText().toString().trim();
        String lastNameStr = lastName.getText().toString().trim();
        String genderStr = gender.getText().toString().trim();
        String raceStr = race.getText().toString().trim();
        String cityStr = city.getText().toString().trim();
        String suburbStr = suburb.getText().toString().trim();
        String postalStr = postalCode.getText().toString().trim();
        String skillsStr = skills.getText().toString().trim();
        String eduStr = education.getText().toString().trim();
        String emailStr = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        if (firstNameStr.isEmpty() || lastNameStr.isEmpty() || genderStr.isEmpty() ||
                raceStr.isEmpty() || cityStr.isEmpty() || suburbStr.isEmpty() ||
                postalStr.isEmpty() || skillsStr.isEmpty() || eduStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare the profile data
        Map<String, Object> profile = new HashMap<>();
        profile.put("firstName", firstNameStr);
        profile.put("lastName", lastNameStr);
        profile.put("gender", genderStr);
        profile.put("race", raceStr);
        profile.put("email", emailStr);
        profile.put("city", cityStr);
        profile.put("suburb", suburbStr);
        profile.put("postalCode", postalStr);
        profile.put("skills", skillsStr);
        profile.put("highestEducation", eduStr);

        // Save CV to SQLite if selected
        if (selectedCVUri != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedCVUri);
                byte[] cvBytes = readBytes(inputStream);
                boolean saved = dbHelper.saveCV(emailStr, cvBytes);

                if (!saved) {
                    Toast.makeText(this, "Failed to save CV to SQLite", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading CV: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (profileDocId != null) {
            db.collection("JobSeekerProfile").document(profileDocId)
                    .set(profile)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, JobSeekerDashboardActivity.class));
                    });
        } else {
            db.collection("JobSeekerProfile").add(profile)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, JobSeekerDashboardActivity.class));
                    });
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        filePickerLauncher.launch(intent);
    }

    private byte[] readBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
