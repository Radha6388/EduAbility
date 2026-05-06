package com.example.eduability;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Random;

public class FocusActivity extends AppCompatActivity {

    TextView tvTitle, tvInstruction, tvShape, tvScore;
    Button btnCircle, btnSquare, btnTriangle;

    TextToSpeech tts;

    int score = 0;
    int correctIndex = 0;

    // Shapes data (MUST match buttons)
    String[] shapeNames = {"Circle", "Square", "Triangle"};
    String[] shapeSymbols = {"⭕", "⬛", "🔺"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        // Bind views
        tvTitle = findViewById(R.id.tvTitle);
        tvInstruction = findViewById(R.id.tvInstruction);
        tvShape = findViewById(R.id.tvShape);
        tvScore = findViewById(R.id.tvScore);

        btnCircle = findViewById(R.id.btnCircle);
        btnSquare = findViewById(R.id.btnSquare);
        btnTriangle = findViewById(R.id.btnTriangle);

        // Text to Speech
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                tts.setSpeechRate(0.8f);
                tts.setPitch(1.1f);
                speak("Focus game. Tap the correct shape.");
            }
        });

        loadNextShape();

        // Button clicks
        btnCircle.setOnClickListener(v -> checkAnswer(0));
        btnSquare.setOnClickListener(v -> checkAnswer(1));
        btnTriangle.setOnClickListener(v -> checkAnswer(2));
    }

    private void loadNextShape() {
        correctIndex = new Random().nextInt(shapeNames.length);

        tvShape.setText(shapeSymbols[correctIndex]);
        tvInstruction.setText("Tap the " + shapeNames[correctIndex]);

        speak("Find the " + shapeNames[correctIndex]);
    }

    private void checkAnswer(int selectedIndex) {
        if (selectedIndex == correctIndex) {
            score++;
            tvScore.setText("Score: " + score);
            speak("Correct");
            Toast.makeText(this, "✅ Correct!", Toast.LENGTH_SHORT).show();
            loadNextShape();
        } else {
            speak("Try again");
            Toast.makeText(this, "❌ Try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
