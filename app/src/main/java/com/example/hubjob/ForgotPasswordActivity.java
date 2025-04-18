package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailField;
    private Button resetPasswordButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize views
        emailField = findViewById(R.id.emailField);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        mAuth = FirebaseAuth.getInstance();

        // Button click listener
        resetPasswordButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                boolean emailExists = !task.getResult().getSignInMethods().isEmpty();

                                if (emailExists) {
                                    mAuth.sendPasswordResetEmail(email)
                                            .addOnCompleteListener(resetTask -> {
                                                if (resetTask.isSuccessful()) {
                                                    Toast.makeText(ForgotPasswordActivity.this, "Password reset link sent to " + email, Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(ForgotPasswordActivity.this, ForgotPasswordSuccessActivity.class));
                                                    finish();
                                                } else {
                                                    String errorMsg = resetTask.getException() != null ? resetTask.getException().getMessage() : "Reset failed.";
                                                    Toast.makeText(ForgotPasswordActivity.this, "Error: " + errorMsg, Toast.LENGTH_LONG).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, "No account found with this email.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgotPasswordActivity.this, ForgotPasswordFailedActivity.class));
                                }
                            } else {
                                String errorMsg = task.getException() != null ? task.getException().getMessage() : "Email check failed.";
                                Toast.makeText(ForgotPasswordActivity.this, "Error: " + errorMsg, Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
