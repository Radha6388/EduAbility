package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class DailyLifeActivity extends AppCompatActivity {

    MaterialCardView cardMorning, cardFocus, cardZen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_life);

        // ================= INIT =================
        cardMorning = findViewById(R.id.cardMorning);
        cardFocus = findViewById(R.id.cardFocus);
        cardZen = findViewById(R.id.cardZen);

        // ================= CARD CLICK =================

        // 🌅 MORNING ROUTINE
        cardMorning.setOnClickListener(v -> {
            animateClick(v);

            Intent intent = new Intent(DailyLifeActivity.this, MorningRoutineActivity.class);
            startActivity(intent);

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // 🎯 FOCUS MODE
        cardFocus.setOnClickListener(v -> {
            animateClick(v);

            Intent intent = new Intent(DailyLifeActivity.this, FocusdailyActivity.class);
            startActivity(intent);

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // 🧘 ZEN STORY
        cardZen.setOnClickListener(v -> {
            animateClick(v);

            Intent intent = new Intent(DailyLifeActivity.this, ZenStoryActivity.class);
            startActivity(intent);

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    // ================= CLICK ANIMATION =================
    private void animateClick(View view) {
        view.animate()
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(100)
                .withEndAction(() ->
                        view.animate().scaleX(1f).scaleY(1f).setDuration(100)
                );
    }
}