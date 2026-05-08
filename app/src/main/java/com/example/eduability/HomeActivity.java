package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    LinearLayout cardLearn, cardPlay, cardRead, cardProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 🔗 Link Cards
        cardLearn = findViewById(R.id.cardLearn);
        cardPlay = findViewById(R.id.cardPlay);
        cardRead = findViewById(R.id.cardRead);
        cardProgress = findViewById(R.id.cardProgress);

        // 🎯 Navigation
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

        // 🔙 FIX BACK BUTTON (IMPORTANT)
        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {

                        // Go back to Main Home instead of exiting app
                        Intent intent = new Intent(HomeActivity.this, MainHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}