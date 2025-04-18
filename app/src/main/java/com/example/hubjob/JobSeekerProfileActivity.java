package com.example.hubjob;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JobSeekerProfileActivity extends AppCompatActivity {

    private TextView profileEmailTextView;
    private EditText firstNameEditText, lastNameEditText, cityEditText, suburbEditText,
            postalCodeEditText, educationEditText, genderEditText, raceEditText, skillsEditText;
    private Button saveProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_profile);

        profileEmailTextView = findViewById(R.id.emailField);

        firstNameEditText = findViewById(R.id.firstNameField);
        lastNameEditText = findViewById(R.id.lastNameField);
        cityEditText = findViewById(R.id.cityField);
        suburbEditText = findViewById(R.id.suburbField);
        postalCodeEditText = findViewById(R.id.postalCodeField);
        educationEditText = findViewById(R.id.highestEducationField);
        genderEditText = findViewById(R.id.genderField);
        raceEditText = findViewById(R.id.raceField);
        skillsEditText = findViewById(R.id.skillsField);

        saveProfileButton = findViewById(R.id.saveButton);
    }

}
