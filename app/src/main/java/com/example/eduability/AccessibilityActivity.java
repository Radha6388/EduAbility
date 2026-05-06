package com.example.eduability;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class AccessibilityActivity extends AppCompatActivity {

    Switch switchTTS, switchMusic, switchContrast;
    RadioGroup fontGroup;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

        switchTTS = findViewById(R.id.switchTTS);
        switchMusic = findViewById(R.id.switchMusic);
        switchContrast = findViewById(R.id.switchContrast);
        fontGroup = findViewById(R.id.fontGroup);

        prefs = getSharedPreferences("accessibility_prefs", MODE_PRIVATE);

        // LOAD SAVED SETTINGS
        switchTTS.setChecked(prefs.getBoolean("tts", true));
        switchMusic.setChecked(prefs.getBoolean("music", true));
        switchContrast.setChecked(prefs.getBoolean("contrast", false));

        int fontSize = prefs.getInt("font", 1);
        ((RadioButton) fontGroup.getChildAt(fontSize)).setChecked(true);

        // SAVE SETTINGS
        switchTTS.setOnCheckedChangeListener((b, isChecked) ->
                prefs.edit().putBoolean("tts", isChecked).apply()
        );

        switchMusic.setOnCheckedChangeListener((b, isChecked) ->
                prefs.edit().putBoolean("music", isChecked).apply()
        );

        switchContrast.setOnCheckedChangeListener((b, isChecked) ->
                prefs.edit().putBoolean("contrast", isChecked).apply()
        );

        fontGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int index = group.indexOfChild(findViewById(checkedId));
            prefs.edit().putInt("font", index).apply();
        });
    }
}
