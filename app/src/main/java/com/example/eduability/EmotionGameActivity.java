package com.example.eduability;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Locale;

public class EmotionGameActivity extends AppCompatActivity {

    TextView tvTitle, tvQuestion, tvProgress;
    ImageView imgEmotion, btnBack, btnSound;
    Button btn1, btn2, btn3, btn4;

    TextToSpeech tts;
    MediaPlayer bgMusic;

    boolean soundOn = true;
    boolean locked = false;
    boolean ttsFinished = false;

    int index = 0;
    int score = 0;

    // 🔹 DATA
    String[] persons = {
            "Meet Radha!",
            "Meet Ram!",
            "Meet Krishna!",
            "Meet Shambhu!"
    };

    String[] questions = {
            "How is Radha feeling?",
            "How is Ram feeling?",
            "How is Krishna feeling?",
            "How is Shambhu feeling?"
    };

    int[] images = {
            R.drawable.emoji_surprised,
            R.drawable.emoji_happy,
            R.drawable.emoji_sad,
            R.drawable.emoji_angry
    };

    String[][] options = {
            {"Surprised", "Excited", "Sad", "Bored"},
            {"Angry", "Happy", "Sad", "Scared"},
            {"Bored", "Happy", "Excited", "Sad"},
            {"Angry", "Happy", "Sad", "Excited"}
    };

    String[] correctAnswers = {
            "Surprised",
            "Happy",
            "Sad",
            "Angry"
    };

    String[] explanations = {
            "Wide eyes and open mouth show surprise!",
            "A big smile shows happiness!",
            "Tears and low energy show sadness!",
            "Frowning face shows anger!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_game);

        tvTitle = findViewById(R.id.tvTitle);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvProgress = findViewById(R.id.tvProgress);
        imgEmotion = findViewById(R.id.imgEmotion);

        btn1 = findViewById(R.id.btnOption1);
        btn2 = findViewById(R.id.btnOption2);
        btn3 = findViewById(R.id.btnOption3);
        btn4 = findViewById(R.id.btnOption4);

        btnBack = findViewById(R.id.btnBack);
        btnSound = findViewById(R.id.btnSound);

        // 🔹 ACCESSIBILITY PREFS
        SharedPreferences prefs =
                getSharedPreferences("accessibility_prefs", MODE_PRIVATE);

        soundOn = prefs.getBoolean("music", true);
        boolean ttsOn = prefs.getBoolean("tts", true);

        // 🎵 BACKGROUND MUSIC
        bgMusic = MediaPlayer.create(this, R.raw.trap_bgm);
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.3f, 0.3f);
        if (soundOn) bgMusic.start();

        // 🔊 TTS
        if (ttsOn) {
            tts = new TextToSpeech(this, status -> {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                    tts.setPitch(1.1f);
                    tts.setSpeechRate(0.7f);

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            ttsFinished = false;
                        }

                        @Override
                        public void onDone(String utteranceId) {
                            ttsFinished = true;
                        }

                        @Override
                        public void onError(String utteranceId) {
                            ttsFinished = true;
                        }
                    });
                }
            });
        }

        btnBack.setOnClickListener(v -> finish());

        btnSound.setOnClickListener(v -> {
            soundOn = !soundOn;
            btnSound.setAlpha(soundOn ? 1f : 0.4f);
            if (soundOn) bgMusic.start();
            else bgMusic.pause();
        });

        loadQuestion();

        btn1.setOnClickListener(v -> handleClick(btn1.getText().toString()));
        btn2.setOnClickListener(v -> handleClick(btn2.getText().toString()));
        btn3.setOnClickListener(v -> handleClick(btn3.getText().toString()));
        btn4.setOnClickListener(v -> handleClick(btn4.getText().toString()));
    }

    private void loadQuestion() {
        locked = false;

        tvTitle.setText(persons[index]);
        tvQuestion.setText(questions[index]);
        tvProgress.setText("Q " + (index + 1) + " / " + persons.length);
        imgEmotion.setImageResource(images[index]);

        btn1.setText(options[index][0]);
        btn2.setText(options[index][1]);
        btn3.setText(options[index][2]);
        btn4.setText(options[index][3]);

        speak(questions[index]);
    }

    private void handleClick(String selected) {
        if (locked) return;
        locked = true;

        boolean correct = selected.equals(correctAnswers[index]);
        if (correct) score++;

        speak(explanations[index]);
        showResultDialog(correct, explanations[index]);
    }

    private void showResultDialog(boolean isCorrect, String explanation) {

        View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_emotion_result, null);

        TextView tvResultTitle = dialogView.findViewById(R.id.tvResultTitle);
        TextView tvExplanation = dialogView.findViewById(R.id.tvExplanation);

        tvResultTitle.setText(isCorrect ? "🎉 Correct!" : "❌ Try Again!");
        tvExplanation.setText(explanation);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT)
        );

        dialog.show();

        dialogView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ttsFinished) {
                    dialog.dismiss();   // ✅ POPUP CLOSES
                    loadNext();         // ✅ NEXT QUESTION LOADS
                } else {
                    dialogView.postDelayed(this, 300);
                }
            }
        }, 300);
    }

    private void loadNext() {
        index++;
        if (index < persons.length) {
            loadQuestion();
        } else {

            // 🔥 CALCULATE PROGRESS BASED ON SCORE
            int total = persons.length;
            int progress = (score * 100) / total;

            // ✅ SAVE EMOTION PROGRESS
            ProgressHelper.saveProgress(this, ProgressHelper.KEY_EMOTION, progress);

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("total", total);
            startActivity(intent);

            finish();
        }
    }

    private void speak(String text) {
        if (tts != null && soundOn) {
            ttsFinished = false; // ✅ RESET BEFORE SPEAKING
            HashMap<String, String> map = new HashMap<>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "EMOTION_TTS");
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bgMusic != null && bgMusic.isPlaying()) bgMusic.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bgMusic != null && soundOn) bgMusic.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if (bgMusic != null) bgMusic.release();
    }
}
