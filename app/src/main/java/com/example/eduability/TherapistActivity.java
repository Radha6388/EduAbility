package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TherapistActivity extends AppCompatActivity {

    Button btnFocus, btnEmotion, btnMotor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist);

        // Bind buttons
        btnFocus = findViewById(R.id.btnFocus);
        btnEmotion = findViewById(R.id.btnEmotion);
        btnMotor = findViewById(R.id.btnMotor);

        // 🎯 Focus Activities
        btnFocus.setOnClickListener(v ->
                startActivity(new Intent(
                        TherapistActivity.this,
                        FocusActivity.class
                ))
        );

        // 😊 Emotion Regulation
        btnEmotion.setOnClickListener(v ->
                startActivity(new Intent(
                        TherapistActivity.this,
                        EmotionRegulationActivity.class
                ))
        );

        // ✋ Motor Skills
        btnMotor.setOnClickListener(v ->
                startActivity(new Intent(
                        TherapistActivity.this,
                        MotorSkillActivity.class
                ))
        );
    }
}
