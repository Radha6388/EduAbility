package com.example.eduability;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class FindLetterGameActivity extends AppCompatActivity {

    TextView tvTargetLetter, tvWord;
    ImageView imgMeaning;
    Button btn1, btn2, btn3, btn4;

    TextToSpeech tts;
    MediaPlayer bgMusic;

    int index = 0;
    int score = 0;
    boolean answered = false;

    // 🔹 ACCESSIBILITY FLAGS
    boolean isTtsOn = true;
    boolean isMusicOn = true;

    // 🎯 GAME DATA (5 LETTERS)
    String[] letters = {"T", "B", "C", "D", "E"};
    String[] words = {
            "T for Teddy",
            "B for Ball",
            "C for Cat",
            "D for Dog",
            "E for Elephant"
    };

    int[] images = {
            R.drawable.teddy,
            R.drawable.b_ball,
            R.drawable.c_cat,
            R.drawable.d_dog,
            R.drawable.e_elephant
    };

    String[][] options = {
            {"T", "A", "S", "L"},
            {"A", "D", "B", "C"},
            {"E", "B", "D", "C"},
            {"F", "D", "E", "B"},
            {"E", "A", "B", "C"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_letter_game);

        tvTargetLetter = findViewById(R.id.tvTargetLetter);
        tvWord = findViewById(R.id.tvWord);
        imgMeaning = findViewById(R.id.imgMeaning);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        // 🔹 READ ACCESSIBILITY SETTINGS
        SharedPreferences prefs =
                getSharedPreferences("accessibility_prefs", MODE_PRIVATE);

        isTtsOn = prefs.getBoolean("tts", true);
        isMusicOn = prefs.getBoolean("music", true);

        // 🎵 BACKGROUND MUSIC
        bgMusic = MediaPlayer.create(this, R.raw.bg_music);
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.4f, 0.4f);

        if (isMusicOn) {
            bgMusic.start();
        }

        // 🔊 TEXT TO SPEECH
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS && isTtsOn) {
                tts.setLanguage(Locale.US);
                tts.setPitch(1.1f);
                tts.setSpeechRate(0.8f);
            }
        });

        loadQuestion();

        btn1.setOnClickListener(v -> checkAnswer(btn1.getText().toString()));
        btn2.setOnClickListener(v -> checkAnswer(btn2.getText().toString()));
        btn3.setOnClickListener(v -> checkAnswer(btn3.getText().toString()));
        btn4.setOnClickListener(v -> checkAnswer(btn4.getText().toString()));
    }

    private void loadQuestion() {

        answered = false;

        tvTargetLetter.setText(letters[index]);
        tvWord.setText(words[index]);
        imgMeaning.setImageResource(images[index]);

        btn1.setText(options[index][0]);
        btn2.setText(options[index][1]);
        btn3.setText(options[index][2]);
        btn4.setText(options[index][3]);

        enableButtons();

        if (isTtsOn) {
            speak("Find the letter " + letters[index]);
        }
    }

    private void checkAnswer(String selected) {

        if (answered) return;
        answered = true;

        if (selected.equalsIgnoreCase(letters[index])) {
            score++;
            if (isTtsOn) speak("Great job! " + words[index]);
            Toast.makeText(this, "🎉 Correct!", Toast.LENGTH_SHORT).show();
        } else {
            if (isTtsOn)
                speak("Oops! The correct answer was " + letters[index]);

            Toast.makeText(this,
                    "❌ Wrong! Answer was " + letters[index],
                    Toast.LENGTH_SHORT).show();
        }

        disableButtons();

        btn1.postDelayed(() -> {
            index++;
            goNext();
        }, 1200);
    }

    private void goNext() {
        if (index < letters.length) {
            loadQuestion();
        } else {

            // 🔥 CALCULATE PROGRESS BASED ON SCORE
            int total = letters.length;
            int progress = (score * 100) / total;

            // ✅ SAVE PROGRESS
            ProgressHelper.saveProgress(this, ProgressHelper.KEY_FIND_LETTER, progress);

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("total", total);
            startActivity(intent);

            finish();
        }
    }

    private void disableButtons() {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
    }

    private void enableButtons() {
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
    }

    private void speak(String text) {
        if (tts != null && isTtsOn) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bgMusic != null && bgMusic.isPlaying()) {
            bgMusic.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bgMusic != null && isMusicOn) {
            bgMusic.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        if (bgMusic != null) {
            bgMusic.stop();
            bgMusic.release();
        }
    }
}
