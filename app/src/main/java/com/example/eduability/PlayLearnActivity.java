package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PlayLearnActivity extends AppCompatActivity {

    LinearLayout cardFindLetter, cardEmotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_learn);

        cardFindLetter = findViewById(R.id.cardFindLetter);
        cardEmotion = findViewById(R.id.cardEmotion);

        // 🔤 Find Letter Game
        cardFindLetter.setOnClickListener(v ->
                startActivity(new Intent(this, FindLetterGameActivity.class))
        );

        // 😊 Emotion Game
        cardEmotion.setOnClickListener(v ->
                startActivity(new Intent(this, EmotionGameActivity.class))
        );
    }
}
