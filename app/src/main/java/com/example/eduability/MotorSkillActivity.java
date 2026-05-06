package com.example.eduability;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MotorSkillActivity extends AppCompatActivity {

    ImageView imgShape;
    TextView tvProgress, tvInstruction;

    TextToSpeech tts;

    int index = 0;

    // 🎯 SHAPES
    int[] shapes = {
            R.drawable.circle1,
            R.drawable.shape_square,
            R.drawable.triangle,
            R.drawable.star,
            R.drawable.heart
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_skill);

        imgShape = findViewById(R.id.imgShape);
        tvProgress = findViewById(R.id.tvProgress);
        tvInstruction = findViewById(R.id.tvInstruction);

        // 🔊 TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                tts.setSpeechRate(0.8f);
                tts.setPitch(1.0f);
                speak("Tap the shape");
            }
        });

        updateUI();

        imgShape.setOnClickListener(v -> {
            speak("Good job!");
            index++;

            if (index < shapes.length) {
                updateUI();
            } else {
                tvInstruction.setText("🎉 Well Done!");
                speak("You did great");
                imgShape.setEnabled(false);
            }
        });
    }

    private void updateUI() {
        imgShape.setImageResource(shapes[index]);
        tvProgress.setText("Round " + (index + 1) + " / " + shapes.length);
        tvInstruction.setText("Tap the shape!");
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

