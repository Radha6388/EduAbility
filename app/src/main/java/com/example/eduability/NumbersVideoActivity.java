package com.example.eduability;

import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class NumbersVideoActivity extends AppCompatActivity {

    private VideoView videoViewNumbers;
    private Button btnPlayNumbersVideo, btnBackNumbers;
    private ImageView imgNumbersMascot;
    private TextView tvNumbersVideoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_video);

        videoViewNumbers = findViewById(R.id.videoViewNumbers);
        btnPlayNumbersVideo = findViewById(R.id.btnPlayNumbersVideo);
        btnBackNumbers = findViewById(R.id.btnBackNumbers);
        imgNumbersMascot = findViewById(R.id.imgNumbersMascot);
        tvNumbersVideoTitle = findViewById(R.id.tvNumbersVideoTitle);

        String videoTitle = getIntent().getStringExtra("videoTitle");
        int videoResId = getIntent().getIntExtra("videoResId", -1);

        if (videoTitle != null) {
            tvNumbersVideoTitle.setText(videoTitle);
        }

        if (videoResId != -1) {
            String videoPath = "android.resource://" + getPackageName() + "/" + videoResId;
            Uri uri = Uri.parse(videoPath);

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoViewNumbers);
            videoViewNumbers.setMediaController(mediaController);
            videoViewNumbers.setVideoURI(uri);

            videoViewNumbers.setOnPreparedListener(mp -> videoViewNumbers.seekTo(100));
        }

        btnPlayNumbersVideo.setOnClickListener(v -> {
            if (!videoViewNumbers.isPlaying()) {
                videoViewNumbers.start();
            }
        });

        btnBackNumbers.setOnClickListener(v -> finish());

        startMascotWaveAnimation();
    }

    private void startMascotWaveAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                -8f, 8f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1.0f
        );
        rotateAnimation.setDuration(500);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        imgNumbersMascot.startAnimation(rotateAnimation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoViewNumbers != null && videoViewNumbers.isPlaying()) {
            videoViewNumbers.pause();
        }
    }
}