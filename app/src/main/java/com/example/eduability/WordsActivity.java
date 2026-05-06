package com.example.eduability;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class WordsActivity extends AppCompatActivity {

    TextView tvWord, tvCelebrate;
    Button btnSpeak, btnPrev, btnNext;

    TextToSpeech tts;
    MediaPlayer bgMusic;
    AudioManager audioManager;

    int index = 0;

    String[] words = {
            "Cat","Dog","Ball","Apple","Fish","Car","Hat","Sun","Bat","Cup",
            "Book","Tree","Pen","Bed","Cow","Hen","Bus","Toy","Milk","Star"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        tvWord = findViewById(R.id.tvWord);
        tvCelebrate = findViewById(R.id.tvCelebrate);

        btnSpeak = findViewById(R.id.btnSpeak);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        // 🔊 Volume fix
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                (int) (maxVolume * 0.9f),
                0
        );

        // 🎙 TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                tts.setPitch(1.1f);
                tts.setSpeechRate(0.8f);
            }
        });

        // 🎵 Music
        bgMusic = MediaPlayer.create(this, R.raw.trap_bgm);
        bgMusic.setLooping(true);
        bgMusic.start();

        updateUI();

        btnSpeak.setOnClickListener(v -> speak(tvWord.getText().toString()));

        // ➡ NEXT BUTTON
        btnNext.setOnClickListener(v -> {

            if (index < words.length - 1) {

                index++;
                updateUI();
                speak(words[index]);

                // 🔥 STEP-WISE PROGRESS
                int progress = ((index + 1) * 100) / words.length;
                ProgressHelper.saveProgress(this, ProgressHelper.KEY_WORDS, progress);

            } else {

                // ✅ FINAL COMPLETE
                ProgressHelper.saveProgress(this, ProgressHelper.KEY_WORDS, 100);

                showCelebration();
            }
        });

        // ⬅ PREV BUTTON
        btnPrev.setOnClickListener(v -> {
            if (index > 0) {
                index--;
                updateUI();
                speak(words[index]);
            }
        });
    }

    private void updateUI() {
        tvCelebrate.setVisibility(View.GONE);
        tvWord.setText(words[index]);
    }

    private void speak(String text) {
        if (tts != null) {
            Bundle params = new Bundle();
            params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, null);
        }
    }

    private void showCelebration() {
        tvCelebrate.setVisibility(View.VISIBLE);
        speak("Great job");
        Toast.makeText(this, "🎉 Words Completed!", Toast.LENGTH_SHORT).show();
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
        if (bgMusic != null && !bgMusic.isPlaying()) {
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
            if (bgMusic.isPlaying()) bgMusic.stop();
            bgMusic.release();
        }
    }
}