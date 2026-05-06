package com.example.eduability;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;
import java.util.Random;

public class FocusdailyActivity extends AppCompatActivity {

    TextView timerText, buddyMessage;
    ImageView progressImage;
    MaterialButton btnStart;

    CountDownTimer timer;
    Handler handler = new Handler();

    TextToSpeech tts;

    MediaPlayer popSound;
    MediaPlayer celebrationSound;

    long totalTime = 5 * 60 * 1000;
    long timeLeft = totalTime;

    boolean isRunning = false;

    int lastStage = -1; // 🔥 prevent sound spam

    String[] messages = {
            "You're doing great",
            "Keep going",
            "Awesome focus",
            "Stay strong",
            "Nice work"
    };

    int[] plantStages = {
            R.drawable.plant_stage2,
            R.drawable.plant_stage1,
            R.drawable.plant_stage3,
            R.drawable.plant_stage4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focusdaily);

        timerText = findViewById(R.id.timerText);
        buddyMessage = findViewById(R.id.buddyMessage);
        progressImage = findViewById(R.id.progressImage);
        btnStart = findViewById(R.id.btnStart);

        updateTimer();

        // 🔊 INIT TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                tts.setSpeechRate(0.8f);
                tts.setPitch(1.0f);

                speak("Tap start to begin focusing");
            }
        });

        // 🔊 INIT SOUND EFFECTS
        popSound = MediaPlayer.create(this, R.raw.pop_sound);
        celebrationSound = MediaPlayer.create(this, R.raw.celebration);

        btnStart.setOnClickListener(v -> {
            if (!isRunning) {
                speak("Focus session started. Stay with me.");
                startFocus();
            }
        });
    }

    private void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void startFocus() {
        isRunning = true;
        lastStage = -1; // reset

        buddyMessage.setText("Let's grow your plant");

        startBuddyMessages();

        timer = new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
                updatePlantGrowth();
            }

            @Override
            public void onFinish() {
                isRunning = false;

                buddyMessage.setText("You grew a flower");
                progressImage.setImageResource(R.drawable.plant_stage4);

                speak("Amazing job! You grew a beautiful flower");

                // 🎉 PLAY CELEBRATION SOUND
                if (celebrationSound != null) {
                    celebrationSound.start();
                }
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        timerText.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void updatePlantGrowth() {
        float progress = (float) (totalTime - timeLeft) / totalTime;

        int stage;

        if (progress < 0.25)
            stage = 0;
        else if (progress < 0.5)
            stage = 1;
        else if (progress < 0.75)
            stage = 2;
        else
            stage = 3;

        progressImage.setImageResource(plantStages[stage]);

        // 🔥 PLAY POP ONLY WHEN STAGE CHANGES
        if (stage != lastStage) {
            lastStage = stage;

            if (popSound != null) {
                popSound.start();
            }
        }

        // animation
        progressImage.setScaleX(0.9f);
        progressImage.setScaleY(0.9f);
        progressImage.animate().scaleX(1f).scaleY(1f).setDuration(300);
    }

    private void startBuddyMessages() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    String msg = messages[new Random().nextInt(messages.length)];
                    buddyMessage.setText(msg);

                    speak(msg);

                    handler.postDelayed(this, 7000);
                }
            }
        }, 7000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timer != null) timer.cancel();
        handler.removeCallbacksAndMessages(null);

        // 🔊 CLEAN TTS
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        // 🔊 CLEAN SOUND
        if (popSound != null) {
            popSound.release();
            popSound = null;
        }

        if (celebrationSound != null) {
            celebrationSound.release();
            celebrationSound = null;
        }
    }
}