package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JobSeekerRegistrationActivity extends AppCompatActivity {

    private EditText fullNameField;
    private EditText emailField;
    private EditText phoneField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private Button registerButton;
    private TextView loginText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_registration);

        mAuth = FirebaseAuth.getInstance();

        fullNameField = findViewById(R.id.fullNameField);
        emailField = findViewById(R.id.emailField);
        phoneField = findViewById(R.id.phoneField);
        passwordField = findViewById(R.id.passwordField);
        confirmPasswordField = findViewById(R.id.confirmPasswordField);
        registerButton = findViewById(R.id.registerButton);
        loginText = findViewById(R.id.loginText);

        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(JobSeekerRegistrationActivity.this, JobSeekerActivity.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(v -> {
            String fullName = fullNameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String phone = phoneField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String confirmPassword = confirmPasswordField.getText().toString().trim();

            // Validate input
            if (TextUtils.isEmpty(fullName)) {
                fullNameField.setError("Full Name is required.");
                return;
            }

            if (TextUtils.isEmpty(email)) {
                emailField.setError("Email is required.");
                return;
            }

            if (TextUtils.isEmpty(phone)) {
                phoneField.setError("Phone number is required.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordField.setError("Password is required.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                confirmPasswordField.setError("Passwords do not match.");
                return;
            }

            // Register user with Firebase
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(verificationTask -> {
                                            if (verificationTask.isSuccessful()) {
                                                Toast.makeText(JobSeekerRegistrationActivity.this,
                                                        "Registration successful! Please check your email to verify your account.",
                                                        Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(JobSeekerRegistrationActivity.this,
                                                        "Verification email failed to send.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                            Intent intent = new Intent(JobSeekerRegistrationActivity.this, JobSeekerActivity.class);
                                            startActivity(intent);
                                            finish();
                                        });
                            }
                        } else {
                            Toast.makeText(JobSeekerRegistrationActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}
