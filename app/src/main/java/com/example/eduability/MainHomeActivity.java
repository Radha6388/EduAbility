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

public class MainHomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView btnProfile;

    // ✅ UPDATED CARDS
    LinearLayout cardBasicLearning, cardAI, cardDailyLife,
            cardGames, cardProgress, cardVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        btnProfile = findViewById(R.id.btnProfile);

        // ✅ FIND VIEWS
        cardBasicLearning = findViewById(R.id.cardBasicLearning);
        cardAI = findViewById(R.id.cardAI);
        cardDailyLife = findViewById(R.id.cardDailyLife); // 🔥 changed
        cardGames = findViewById(R.id.cardGames);
        cardProgress = findViewById(R.id.cardProgress);
        cardVideo = findViewById(R.id.cardVideo);

        // ✅ NAVIGATION HEADER
        if (navigationView != null) {
            navigationView.setItemIconTintList(null);

            View headerView = navigationView.getHeaderView(0);
            TextView tvUserEmail = headerView.findViewById(R.id.tvUserEmail);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null && tvUserEmail != null) {
                tvUserEmail.setText(user.getEmail());
            }

            navigationView.setNavigationItemSelectedListener(this::handleDrawerClick);
        }

        // ✅ OPEN DRAWER
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }

        // ✅ BACK PRESS
        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        } else {
                            finish();
                        }
                    }
                });

        // 🎓 BASIC LEARNING
        cardBasicLearning.setOnClickListener(view -> {
            startActivity(new Intent(MainHomeActivity.this, HomeActivity.class));
        });

        // 🤖 AI
        cardAI.setOnClickListener(view -> {
            Intent intent = new Intent(MainHomeActivity.this, AIAssistantActivity.class);
            startActivity(intent);
        });

        // 🧭 DAILY LIFE SKILLS (🔥 NEW FEATURE)
        cardDailyLife.setOnClickListener(view -> {
            Intent intent = new Intent(MainHomeActivity.this, DailyLifeActivity.class);
            startActivity(intent);
        });

        // 🎮 GAMES
        cardGames.setOnClickListener(view -> {
            startActivity(new Intent(MainHomeActivity.this, GamesActivity.class));
        });

        // 📊 PROGRESS
        cardProgress.setOnClickListener(view -> {
            startActivity(new Intent(MainHomeActivity.this, ProgressActivity.class));
        });

        // 🎥 VIDEOS
        cardVideo.setOnClickListener(view -> {
            startActivity(new Intent(MainHomeActivity.this, VideosActivity.class));
        });
    }

    // 🔧 DRAWER MENU
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
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return true;
    }
}