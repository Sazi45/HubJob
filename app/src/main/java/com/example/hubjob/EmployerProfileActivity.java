package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class EmployerProfileActivity extends AppCompatActivity {

    EditText companyName, industry, contactPerson, phoneNumber, emailField,
            address, city, suburb, postalCode;
    Button saveButton;
    FirebaseFirestore db;
    String profileDocId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        db = FirebaseFirestore.getInstance();

        companyName = findViewById(R.id.companyNameField);
        industry = findViewById(R.id.industryField);
        contactPerson = findViewById(R.id.contactPersonField);
        phoneNumber = findViewById(R.id.phoneField);
        emailField = findViewById(R.id.emailField);
        address = findViewById(R.id.addressField);
        city = findViewById(R.id.cityField);
        suburb = findViewById(R.id.suburbField);
        postalCode = findViewById(R.id.postalCodeField);
        saveButton = findViewById(R.id.saveButton);

        loadEmployerProfile();

        saveButton.setOnClickListener(v -> saveProfile());
    }

    private void loadEmployerProfile() {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        db.collection("EmployerProfiles")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!snapshot.isEmpty()) {
                        for (QueryDocumentSnapshot doc : snapshot) {
                            profileDocId = doc.getId();
                            companyName.setText(doc.getString("companyName"));
                            industry.setText(doc.getString("industry"));
                            contactPerson.setText(doc.getString("contactPerson"));
                            phoneNumber.setText(doc.getString("phoneNumber"));
                            emailField.setText(doc.getString("email"));
                            address.setText(doc.getString("address"));
                            city.setText(doc.getString("city"));
                            suburb.setText(doc.getString("suburb"));
                            postalCode.setText(doc.getString("postalCode"));
                        }
                    }
                });
    }

    private void saveProfile() {
        String companyNameStr = companyName.getText().toString().trim();
        String industryStr = industry.getText().toString().trim();
        String contactPersonStr = contactPerson.getText().toString().trim();
        String phoneStr = phoneNumber.getText().toString().trim();
        String emailStr = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String addressStr = address.getText().toString().trim();
        String cityStr = city.getText().toString().trim();
        String suburbStr = suburb.getText().toString().trim();
        String postalStr = postalCode.getText().toString().trim();

        if (companyNameStr.isEmpty() || industryStr.isEmpty() || contactPersonStr.isEmpty() ||
                phoneStr.isEmpty() || addressStr.isEmpty() || cityStr.isEmpty() ||
                suburbStr.isEmpty() || postalStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> profile = new HashMap<>();
        profile.put("companyName", companyNameStr);
        profile.put("industry", industryStr);
        profile.put("contactPerson", contactPersonStr);
        profile.put("phoneNumber", phoneStr);
        profile.put("email", emailStr);
        profile.put("address", addressStr);
        profile.put("city", cityStr);
        profile.put("suburb", suburbStr);
        profile.put("postalCode", postalStr);

        if (profileDocId != null) {
            db.collection("EmployerProfiles").document(profileDocId)
                    .set(profile)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, EmployerDashboardActivity.class));
                    });
        } else {
            db.collection("EmployerProfiles").add(profile)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, EmployerDashboardActivity.class));
                    });
        }
    }
}
