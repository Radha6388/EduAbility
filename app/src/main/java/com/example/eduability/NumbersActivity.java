package com.example.eduability;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class NumbersActivity extends AppCompatActivity {

    TextView tvNumber, tvCount;
    ImageView imgNumber;
    Button btnSpeak, btnPrev, btnNext, btnMusic;

    TextToSpeech tts;
    AudioManager audioManager;
    MediaPlayer bgMusic;

    boolean isMusicOn = true;
    boolean isTtsOn = true;
    int index = 0;

    String[] numbers = {"1", "2", "3", "4", "5"};
    String[] words = {"One", "Two", "Three", "Four", "Five"};

    int[] images = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        tvNumber = findViewById(R.id.tvNumber);
        tvCount = findViewById(R.id.tvCount);
        imgNumber = findViewById(R.id.imgNumber);
        btnSpeak = findViewById(R.id.btnSpeak);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnMusic = findViewById(R.id.btnMusic);

        SharedPreferences prefs = getSharedPreferences("accessibility_prefs", MODE_PRIVATE);
        isTtsOn = prefs.getBoolean("tts", true);
        isMusicOn = prefs.getBoolean("music", true);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                (int) (maxVolume * 0.9f),
                0
        );

        bgMusic = MediaPlayer.create(this, R.raw.bg_music);
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.4f, 0.4f);

        if (isMusicOn) bgMusic.start();

        if (isTtsOn) {
            tts = new TextToSpeech(this, status -> {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                    tts.setPitch(1.1f);
                    tts.setSpeechRate(0.8f);
                }
            });
        }

        updateUI();

        btnSpeak.setOnClickListener(v -> {
            if (isTtsOn) speak(words[index]);
        });

        btnNext.setOnClickListener(v -> {

            if (index < numbers.length - 1) {

                index++;
                updateUI();

                if (isTtsOn) speak(words[index]);

                // 🔥 STEP-WISE PROGRESS SAVE
                int progress = ((index + 1) * 100) / numbers.length;
                ProgressHelper.saveProgress(this, ProgressHelper.KEY_NUMBERS, progress);

            } else {

                // ✅ FINAL COMPLETION
                ProgressHelper.saveProgress(this, ProgressHelper.KEY_NUMBERS, 100);

                Toast.makeText(this,
                        "🎉 Numbers completed!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnPrev.setOnClickListener(v -> {
            if (index > 0) {
                index--;
                updateUI();

                if (isTtsOn) speak(words[index]);
            }
        });

        btnMusic.setOnClickListener(v -> toggleMusic());
    }

    private void updateUI() {
        tvNumber.setText(numbers[index]);
        tvCount.setText(words[index]);
        imgNumber.setImageResource(images[index]);
    }

    private void speak(String text) {
        if (tts != null && isTtsOn) {
            Bundle params = new Bundle();
            params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, null);
        }
    }

    private void toggleMusic() {
        if (bgMusic == null) return;

        if (isMusicOn) {
            bgMusic.pause();
            btnMusic.setText("🔇 Music Off");
        } else {
            bgMusic.start();
            btnMusic.setText("🔊 Music On");
        }

        isMusicOn = !isMusicOn;
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
            if (bgMusic.isPlaying()) bgMusic.stop();
            bgMusic.release();
            bgMusic = null;
        }
    }
}