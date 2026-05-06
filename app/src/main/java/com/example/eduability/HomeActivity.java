package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        btnProfile = findViewById(R.id.btnProfile);

        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);
        TextView tvUserEmail = headerView.findViewById(R.id.tvUserEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            tvUserEmail.setText(user.getEmail());
        }

        LinearLayout cardLearn = findViewById(R.id.cardLearn);
        LinearLayout cardPlay = findViewById(R.id.cardPlay);
        LinearLayout cardRead = findViewById(R.id.cardRead);
        LinearLayout cardProgress = findViewById(R.id.cardProgress);

        btnProfile.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START)
        );

        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        } else {
                            Intent intent = new Intent(HomeActivity.this, MainHomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

        navigationView.setNavigationItemSelectedListener(this::handleDrawerClick);

        cardLearn.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, LearningActivity.class))
        );

        cardPlay.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, PlayLearnActivity.class))
        );

        cardRead.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, ReadListenActivity.class))
        );

        cardProgress.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, GuidedLearningActivity.class))
        );
    }

    private boolean handleDrawerClick(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        int id = item.getItemId();

        if (id == R.id.menu_profile) {
            startActivity(new Intent(this, ProfileActivity.class));

        } else if (id == R.id.menu_accessibility) {
            startActivity(new Intent(this, AccessibilityActivity.class));

        } else if (id == R.id.menu_progress) {
            startActivity(new Intent(this, ProgressActivity.class));

        } else if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));

        } else if (id == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        return true;
    }
}