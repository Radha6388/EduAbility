package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LearningActivity extends AppCompatActivity {

    LinearLayout cardAlphabet, cardNumbers, cardWords, cardShapes, cardColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        // 🔗 Bind views
        cardAlphabet = findViewById(R.id.cardAlphabet);
        cardNumbers  = findViewById(R.id.cardNumbers);
        cardWords    = findViewById(R.id.cardWords);
        cardShapes   = findViewById(R.id.cardShapes);
        cardColors   = findViewById(R.id.cardColors);

        // 🔤 Alphabets
        cardAlphabet.setOnClickListener(v -> {
            Toast.makeText(this, "🔤 Alphabets", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AlphabetActivity.class));
        });

        // 🔢 Numbers
        cardNumbers.setOnClickListener(v -> {
            Toast.makeText(this, "🔢 Numbers", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, NumbersActivity.class));
        });

        // 🐶 Words
        cardWords.setOnClickListener(v -> {
            Toast.makeText(this, "🐶 Basic Words", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, WordsActivity.class));
        });

        // 🔺 Shapes
        cardShapes.setOnClickListener(v -> {
            Toast.makeText(this, "🔺 Shapes", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ShapesActivity.class));
        });

        // 🎨 Colors
        cardColors.setOnClickListener(v -> {
            Toast.makeText(this, "🎨 quiz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, QuizActivity.class));
        });
    }
}