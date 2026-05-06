package com.example.eduability;

import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ShapesActivity extends AppCompatActivity {

    TextView tvShape, tvCelebrate;
    ImageView imgShape;
    Button btnSpeak, btnPrev, btnNext;

    TextToSpeech tts;
    AudioManager audioManager;

    int index = 0;

    String[] shapes = {
            "Circle",
            "Square",
            "Triangle",
            "Rectangle",
            "Star"
    };

    int[] images = {
            R.drawable.circle1,
            R.drawable.square,
            R.drawable.triangle,
            R.drawable.rectangle,
            R.drawable.star
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes);

        tvShape = findViewById(R.id.tvShape);
        tvCelebrate = findViewById(R.id.tvCelebrate);
        imgShape = findViewById(R.id.imgShape);

        btnSpeak = findViewById(R.id.btnSpeak);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        // 🔊 Volume boost
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                (int) (maxVol * 0.9f),
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

        updateUI();

        btnSpeak.setOnClickListener(v -> speak(shapes[index]));

        // ➡ NEXT
        btnNext.setOnClickListener(v -> {

            if (index < shapes.length - 1) {

                index++;
                updateUI();
                speak(shapes[index]);

                // 🔥 STEP-WISE PROGRESS
                int progress = ((index + 1) * 100) / shapes.length;
                ProgressHelper.saveProgress(this, ProgressHelper.KEY_SHAPES, progress);

            } else {

                // ✅ FINAL COMPLETE
                ProgressHelper.saveProgress(this, ProgressHelper.KEY_SHAPES, 100);

                showCelebration();
            }
        });

        // ⬅ PREV
        btnPrev.setOnClickListener(v -> {
            if (index > 0) {
                index--;
                updateUI();
                speak(shapes[index]);
            }
        });
    }

    private void updateUI() {
        tvCelebrate.setVisibility(View.GONE);
        tvShape.setText(shapes[index]);
        imgShape.setImageResource(images[index]);
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
        Toast.makeText(this, "🎉 Shapes Completed!", Toast.LENGTH_SHORT).show();
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