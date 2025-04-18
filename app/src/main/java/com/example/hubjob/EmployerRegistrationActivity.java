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

public class EmployerRegistrationActivity extends AppCompatActivity {

    private EditText fullNameField, emailField, phoneField, passwordField, confirmPasswordField;
    private Button registerButton;
    private TextView loginText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_registration);

        mAuth = FirebaseAuth.getInstance();

        fullNameField = findViewById(R.id.fullNameField);
        emailField = findViewById(R.id.emailField);
        phoneField = findViewById(R.id.phoneField);
        passwordField = findViewById(R.id.passwordField);
        confirmPasswordField = findViewById(R.id.confirmPasswordField);
        registerButton = findViewById(R.id.registerButton);
        loginText = findViewById(R.id.loginText);

        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerRegistrationActivity.this, EmployerActivity.class);
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

            if (!email.endsWith("@dut4life.ac.za")) {
                emailField.setError("Email must be a DUT4Life address.");
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
                            Toast.makeText(this, "Employer registration successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, EmployerActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}
