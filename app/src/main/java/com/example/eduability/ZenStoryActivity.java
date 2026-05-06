package com.example.eduability;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class ZenStoryActivity extends AppCompatActivity {

    TextView storyText;
    MaterialButton btnStartStory;

    MediaPlayer riverSound;
    MediaPlayer birdSound;

    TextToSpeech tts;

    Handler handler = new Handler();

    int index = 0;

    // 🌿 UPDATED REAL STORY
    String[] story = {
            "Close your eyes gently...",
            "Take a slow deep breath...",
            "You are walking on a soft path near a peaceful river...",
            "The water is flowing slowly beside you...",
            "You can hear birds singing softly in the trees...",
            "The air feels cool and fresh...",
            "You feel safe and calm...",
            "You walk a little further and find a quiet place to sit...",
            "You sit near the river and watch the water flow...",
            "The gentle sound makes your mind peaceful...",
            "Take another deep breath in...",
            "And slowly breathe out...",
            "Feel your body relaxing...",
            "Your shoulders are light...",
            "Your mind is quiet...",
            "You are safe...",
            "You are calm...",
            "Stay here for a moment and enjoy this peaceful place...",
            "Whenever you are ready...",
            "Slowly open your eyes..."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zen_story);

        storyText = findViewById(R.id.storyText);
        btnStartStory = findViewById(R.id.btnStartStory);

        // 🎧 INIT SOUNDS (UNCHANGED)
        riverSound = MediaPlayer.create(this, R.raw.river);
        birdSound = MediaPlayer.create(this, R.raw.birds);

        riverSound.setLooping(true);
        birdSound.setLooping(true);

        // 🔊 BALANCE SOUND
        riverSound.setVolume(0.7f, 0.7f);
        birdSound.setVolume(0.3f, 0.3f);

        // 🔊 INIT TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                tts.setSpeechRate(0.75f); // slow calming speed
                tts.setPitch(1.0f);
            }
        });

        // ▶ START BUTTON
        btnStartStory.setOnClickListener(v -> startStory());
    }

    // 🌿 START STORY
    private void startStory() {

        btnStartStory.setEnabled(false);

        riverSound.start();
        birdSound.start();

        index = 0;

        handler.postDelayed(this::playNextLine, 1500);
    }

    // 📖 STORY FLOW
    private void playNextLine() {

        if (index < story.length) {

            String line = story[index];

            storyText.setText(line);

            // 🔥 smooth fade animation
            storyText.setAlpha(0f);
            storyText.animate().alpha(1f).setDuration(1500);

            speak(line);

            index++;

            // 🔥 more natural timing
            handler.postDelayed(this::playNextLine, 6000);
        }
    }

    // 🔊 SPEAK FUNCTION
    private void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    // 🔄 PAUSE
    @Override
    protected void onPause() {
        super.onPause();

        if (riverSound != null && riverSound.isPlaying())
            riverSound.pause();

        if (birdSound != null && birdSound.isPlaying())
            birdSound.pause();
    }

    // 🔄 RESUME
    @Override
    protected void onResume() {
        super.onResume();

        if (riverSound != null)
            riverSound.start();

        if (birdSound != null)
            birdSound.start();
    }

    // 🧹 CLEANUP
    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacksAndMessages(null);

        if (riverSound != null) {
            riverSound.release();
            riverSound = null;
        }

        if (birdSound != null) {
            birdSound.release();
            birdSound = null;
        }

        if (tts != null) {
            tts.shutdown();
        }
    }
}