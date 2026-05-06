package com.example.eduability;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Switch switchTts, switchMusic;
    Button btnResetProgress;

    SharedPreferences accessibilityPrefs;
    SharedPreferences progressPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchTts = findViewById(R.id.switchTts);
        switchMusic = findViewById(R.id.switchMusic);
        btnResetProgress = findViewById(R.id.btnResetProgress);

        // 📦 Preferences
        accessibilityPrefs = getSharedPreferences("accessibility_prefs", MODE_PRIVATE);
        progressPrefs = getSharedPreferences("child_progress", MODE_PRIVATE);

        // 🔄 Load saved values
        switchTts.setChecked(accessibilityPrefs.getBoolean("tts", true));
        switchMusic.setChecked(accessibilityPrefs.getBoolean("music", true));

        // 🔊 TTS toggle
        switchTts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            accessibilityPrefs.edit()
                    .putBoolean("tts", isChecked)
                    .apply();
        });

        // 🎵 Music toggle
        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            accessibilityPrefs.edit()
                    .putBoolean("music", isChecked)
                    .apply();
        });

        // 🔄 Reset progress
        btnResetProgress.setOnClickListener(v -> {
            progressPrefs.edit().clear().apply();
            Toast.makeText(this,
                    "Progress reset successfully",
                    Toast.LENGTH_SHORT).show();
        });
    }
}
