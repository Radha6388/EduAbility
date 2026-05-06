package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GamesActivity extends AppCompatActivity {

    View cardBalloon, cardMemory, cardCatch, cardBaseball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        cardBalloon = findViewById(R.id.cardBalloon);
        cardMemory = findViewById(R.id.cardMemory);
        cardCatch = findViewById(R.id.cardCatch);
        cardBaseball = findViewById(R.id.cardBaseball);

        cardBalloon.setOnClickListener(v ->
                startActivity(new Intent(GamesActivity.this, TicTacToeActivity.class))
        );

        cardMemory.setOnClickListener(v ->
                startActivity(new Intent(GamesActivity.this, MemoryGameActivity.class))
        );

        // ✅ Catch Game FIXED
        cardCatch.setOnClickListener(v ->
                startActivity(new Intent(GamesActivity.this, CatchGameActivity.class))
        );

        cardBaseball.setOnClickListener(v ->
                startActivity(new Intent(GamesActivity.this, BaseballActivity.class))
        );
    }
}