package com.example.eduability;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class EmotionRegulationActivity extends AppCompatActivity {

    TextView tvBreath, tvInstruction;
    Button btnStart;

    TextToSpeech tts;
    Handler handler = new Handler();

    boolean inhale = true;
    int cycleCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_regulation);

        tvBreath = findViewById(R.id.tvBreath);
        tvInstruction = findViewById(R.id.tvInstruction);
        btnStart = findViewById(R.id.btnStart);

        // 🔊 TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                tts.setSpeechRate(0.7f);
                tts.setPitch(1.0f);
            }
        });

        btnStart.setOnClickListener(v -> {
            cycleCount = 0;
            inhale = true;
            speak("Let's begin calming breathing");
            startBreathing();
        });
    }

    private void startBreathing() {

        if (cycleCount >= 6) { // 6 breaths = calm
            tvBreath.setText("😊");
            tvInstruction.setText("You did great!");
            speak("Well done. You are calm now.");
            return;
        }

        if (inhale) {
            tvBreath.setText("INHALE");
            tvInstruction.setText("Breathe in slowly");
            speak("Inhale");
        } else {
            tvBreath.setText("EXHALE");
            tvInstruction.setText("Breathe out slowly");
            speak("Exhale");
            cycleCount++;
        }

        inhale = !inhale;

        handler.postDelayed(this::startBreathing, 3000);
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
