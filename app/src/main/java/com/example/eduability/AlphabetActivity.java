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

public class AlphabetActivity extends AppCompatActivity {

    TextView tvAlphabet, tvWord;
    ImageView imgAlphabet;
    Button btnSpeak, btnPrev, btnNext, btnMusic;

    TextToSpeech tts;
    AudioManager audioManager;
    MediaPlayer bgMusic;

    int index = 0;

    boolean isMusicOn = true;
    boolean isTtsOn = true;

    String[] letters = {"A", "B", "C", "D", "E", "F"};
    String[] words = {
            "A for Apple",
            "B for Ball",
            "C for Cat",
            "D for Dog",
            "E for Elephant",
            "F for Fish"
    };

    int[] images = {
            R.drawable.a_apple,
            R.drawable.b_ball,
            R.drawable.c_cat,
            R.drawable.d_dog,
            R.drawable.e_elephant,
            R.drawable.f_fish
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        tvAlphabet = findViewById(R.id.tvAlphabet);
        tvWord = findViewById(R.id.tvWord);
        imgAlphabet = findViewById(R.id.imgAlphabet);
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

        bgMusic = MediaPlayer.create(this, R.raw.trap_bgm);
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.25f, 0.25f);

        if (isMusicOn) {
            bgMusic.start();
        }

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS && isTtsOn) {
                tts.setLanguage(Locale.US);
                tts.setPitch(1.1f);
                tts.setSpeechRate(0.8f);
            }
        });

        updateUI();

        btnSpeak.setOnClickListener(v -> {
            if (isTtsOn) {
                speak(words[index]);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (index < letters.length - 1) {
                index++;
                updateUI();

                if (isTtsOn) {
                    speak(words[index]);
                }

                int progress = ((index + 1) * 100) / letters.length;
                ProgressHelper.saveProgress(this, ProgressHelper.KEY_ALPHABET, progress);

            } else {
                ProgressHelper.saveProgress(this, ProgressHelper.KEY_ALPHABET, 100);
                Toast.makeText(this, "🎉 You completed A to F!", Toast.LENGTH_SHORT).show();
            }
        });

        btnPrev.setOnClickListener(v -> {
            if (index > 0) {
                index--;
                updateUI();

                if (isTtsOn) {
                    speak(words[index]);
                }
            }
        });

        if (btnMusic != null) {
            btnMusic.setOnClickListener(v -> toggleMusic());
        }
    }

    private void updateUI() {
        tvAlphabet.setText(letters[index]);
        tvWord.setText(words[index]);
        imgAlphabet.setImageResource(images[index]);
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

        if (bgMusic.isPlaying()) {
            bgMusic.pause();
            Toast.makeText(this, "🔇 Music Off", Toast.LENGTH_SHORT).show();
            isMusicOn = false;
        } else {
            bgMusic.start();
            Toast.makeText(this, "🎶 Music On", Toast.LENGTH_SHORT).show();
            isMusicOn = true;
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
            if (bgMusic.isPlaying()) {
                bgMusic.stop();
            }
            bgMusic.release();
            bgMusic = null;
        }
    }
}