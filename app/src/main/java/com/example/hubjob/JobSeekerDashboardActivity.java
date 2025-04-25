package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;

import com.example.hubjob.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JobSeekerDashboardActivity extends AppCompatActivity {

    private TextView welcomeText;
    private FirebaseAuth mAuth;

    private ImageView menuIcon;

    private CardView cardJobs, cardApplications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_dashboard);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Link views
        welcomeText = findViewById(R.id.welcomeText);
        cardJobs = findViewById(R.id.cardJobs);
        cardApplications = findViewById(R.id.cardApplications);
        menuIcon = findViewById(R.id.menuIcon);

        // Set user email in welcome message
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            welcomeText.setText("Welcome, " + email);
        } else {
            welcomeText.setText("Welcome, Job Seeker");
        }


        cardJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobSeekerDashboardActivity.this, JobsActivity.class);
                startActivity(intent);
            }
        });

        cardApplications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobSeekerDashboardActivity.this, MyApplications.class);
                startActivity(intent);
            }
        });

        //Menu Listener
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View view) {
        // Create a PopupMenu instance
        PopupMenu popupMenu = new PopupMenu(JobSeekerDashboardActivity.this, view);

        // Inflate the menu resource
        popupMenu.getMenuInflater().inflate(R.menu.menu_jobseeker_dashboard, popupMenu.getMenu());

        // Set a listener for menu item selection using if-else
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_item_browse_jobs) {
                    Toast.makeText(JobSeekerDashboardActivity.this, "You are browsing jobs", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.menu_item_applied_jobs) {
                    Toast.makeText(JobSeekerDashboardActivity.this, "Jobs you applied for", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(JobSeekerDashboardActivity.this, MyApplications.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_item_saved_jobs) {
                    Toast.makeText(JobSeekerDashboardActivity.this, "Jobs you have saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(JobSeekerDashboardActivity.this, MySavedJobs.class);
                    startActivity(intent);
                    return true;
                }  else if (itemId == R.id.menu_item_manage_job_preference) {
                    Toast.makeText(JobSeekerDashboardActivity.this, "Manage Job Preference", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(JobSeekerDashboardActivity.this, ManageJobPreferenceActivity.class);
                    startActivity(intent);
                    return true;
                }  else if (itemId == R.id.menu_item_recommended_jobs) {
                    Toast.makeText(JobSeekerDashboardActivity.this, "Your jobs recommendation", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(JobSeekerDashboardActivity.this, RecommendedJobsActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_item_set_job_alert) {
                    Toast.makeText(JobSeekerDashboardActivity.this, "Set Job Alert", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(JobSeekerDashboardActivity.this, MyJobAlertActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_item_profile) {
                    Intent intent = new Intent(JobSeekerDashboardActivity.this, JobSeekerProfileActivity.class);
                    startActivity(intent);
                }else if (itemId == R.id.menu_item_logout) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Intent intent = new Intent(JobSeekerDashboardActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        // Show the popup menu
        popupMenu.show();
    }
}
