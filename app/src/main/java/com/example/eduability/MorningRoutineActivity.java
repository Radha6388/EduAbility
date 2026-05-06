package com.example.eduability;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MorningRoutineActivity extends AppCompatActivity {

    ImageView stepImage;
    TextView stepText;

    MediaPlayer mediaPlayer; // 🎵 music player

    int currentStep = 0;

    int[] images = {
            R.drawable.step1,
            R.drawable.step2,
            R.drawable.step3,
            R.drawable.step4,
            R.drawable.step5
    };

    String[] texts = {
            "Wake Up ☀️",
            "Brush Your Teeth 🪥",
            "Take a Bath 🚿",
            "Get Dressed 👕",
            "Eat Breakfast 🍎"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning_routine);

        stepImage = findViewById(R.id.stepImage);
        stepText = findViewById(R.id.stepText);

        // 🎵 INIT MUSIC
        mediaPlayer = MediaPlayer.create(this, R.raw.calm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        updateStep();

        stepImage.setOnClickListener(v -> nextStep());
        stepText.setOnClickListener(v -> nextStep());
    }

    private void nextStep() {
        currentStep++;

        if (currentStep < images.length) {
            updateStep();
        } else {
            Toast.makeText(this, "Routine Completed 🎉", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void updateStep() {
        stepImage.setImageResource(images[currentStep]);
        stepText.setText(texts[currentStep]);

        // animation
        stepImage.setAlpha(0f);
        stepImage.animate().alpha(1f).setDuration(300);
    }

    // 🔥 VERY IMPORTANT (HANDLE LIFECYCLE)

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause(); // pause when app goes background
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null) {
            mediaPlayer.start(); // resume music
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}