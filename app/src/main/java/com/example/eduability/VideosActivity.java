package com.example.eduability;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VideosActivity extends AppCompatActivity {

    LinearLayout cardAlphabetVideo, cardNumbersVideo, cardShapesVideo, cardRhymesVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        cardAlphabetVideo = findViewById(R.id.cardAlphabetVideo);
        cardNumbersVideo = findViewById(R.id.cardNumbersVideo);
        cardShapesVideo = findViewById(R.id.cardShapesVideo);
        cardRhymesVideo = findViewById(R.id.cardRhymesVideo);

        cardAlphabetVideo.setOnClickListener(v -> {
            Intent intent = new Intent(VideosActivity.this, VideoPlayerActivity.class);
            intent.putExtra("videoTitle", "Alphabet Video");
            intent.putExtra("videoResId", R.raw.alphabet); // change this to your file name
            startActivity(intent);
        });

        cardNumbersVideo.setOnClickListener(v -> {
            Intent intent = new Intent(VideosActivity.this, NumbersVideoActivity.class);
            intent.putExtra("videoTitle", "Numbers Video");
            intent.putExtra("videoResId", R.raw.numbers);
            startActivity(intent);
        });

        cardShapesVideo.setOnClickListener(v -> {
            Intent intent = new Intent(VideosActivity.this, ShapesVideoActivity.class);
            intent.putExtra("videoTitle", "Shapes Video");
            intent.putExtra("videoResId", R.raw.shapes);
            startActivity(intent);
        });

        cardRhymesVideo.setOnClickListener(v -> {
            Intent intent = new Intent(VideosActivity.this, RhymesVideoActivity.class);
            intent.putExtra("videoTitle", "Rhymes Video");
            intent.putExtra("videoResId", R.raw.rhymes);
            startActivity(intent);
        });
    }

    private void openVideo(String videoUrl, String videoName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, videoName + " cannot be opened", Toast.LENGTH_SHORT).show();
        }
    }
}