package com.example.eduability;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class StoryActivity extends AppCompatActivity {

    TextView tvStoryTitle, tvStoryText;
    Button btnListen;
    TextToSpeech tts;

    String storyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        tvStoryTitle = findViewById(R.id.tvStoryTitle);
        tvStoryText = findViewById(R.id.tvStoryText);
        btnListen = findViewById(R.id.btnListen);

        String title = getIntent().getStringExtra("title");
        storyText = getIntent().getStringExtra("story");

        tvStoryTitle.setText(title);
        tvStoryText.setText(storyText);

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                tts.setSpeechRate(0.8f);
            }
        });

        btnListen.setOnClickListener(v -> {
            tts.speak(storyText, TextToSpeech.QUEUE_FLUSH, null, null);
        });
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
