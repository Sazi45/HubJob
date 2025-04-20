package com.example.hubjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.PopupMenu;

import com.example.hubjob.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmployerDashboardActivity extends AppCompatActivity {

    // Declare the views
    private TextView welcomeText;
    private CardView cardJobs;
    private CardView cardJobPost;
    private CardView cardManageJobPosts;
    private ImageView menuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_dashboard);

        welcomeText = findViewById(R.id.welcomeText);
        cardJobs = findViewById(R.id.cardJobs);
        cardJobPost = findViewById(R.id.cardJobPost);
        cardManageJobPosts = findViewById(R.id.cardManageJobPosts);
        menuIcon = findViewById(R.id.menuIcon);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            welcomeText.setText("Welcome, " + userEmail);
        } else {
            welcomeText.setText("Welcome, Guest");
        }


        cardManageJobPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployerDashboardActivity.this, AdminJobsActivity.class);
                startActivity(intent);
            }
        });

        cardJobPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployerDashboardActivity.this, PostJobActivity.class);
                startActivity(intent);
            }
        });
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(EmployerDashboardActivity.this, view);

        popupMenu.getMenuInflater().inflate(R.menu.menu_employer_dashboard, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_item_jobs) {
                    Toast.makeText(EmployerDashboardActivity.this, "Job Listings selected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EmployerDashboardActivity.this, PostedJobsActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_item_post_job) {
                    Intent intent = new Intent(EmployerDashboardActivity.this, PostJobActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_item_manage_jobs) {
                    Toast.makeText(EmployerDashboardActivity.this, "Manage Job Posts selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.menu_item_profile) {
                    Toast.makeText(EmployerDashboardActivity.this, "Profile selected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EmployerDashboardActivity.this, EmployerProfileActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_item_settings) {
                    Toast.makeText(EmployerDashboardActivity.this, "Settings selected", Toast.LENGTH_SHORT).show();
                    return true;
                }else if (itemId == R.id.menu_item_logout) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Intent intent = new Intent(EmployerDashboardActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                }

                return false;
            }
        });
        popupMenu.show();
    }
}
