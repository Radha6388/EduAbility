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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView tvVideoTitle;
    private Button btnPlayVideo, btnBack;
    private ImageView imgMascot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = findViewById(R.id.videoView);
        tvVideoTitle = findViewById(R.id.tvVideoTitle);
        btnPlayVideo = findViewById(R.id.btnPlayVideo);
        btnBack = findViewById(R.id.btnBack);
        imgMascot = findViewById(R.id.imgMascot);

        btnPlayVideo.bringToFront();
        btnBack.bringToFront();

        String videoTitle = getIntent().getStringExtra("videoTitle");
        int videoResId = getIntent().getIntExtra("videoResId", -1);

        if (videoTitle != null) {
            tvVideoTitle.setText(videoTitle);
        }

        if (videoResId != -1) {
            String videoPath = "android.resource://" + getPackageName() + "/" + videoResId;
            Uri uri = Uri.parse(videoPath);

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);

            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);

            videoView.setOnPreparedListener(mp -> videoView.seekTo(100));
        }

        btnPlayVideo.setOnClickListener(v -> {
            if (!videoView.isPlaying()) {
                videoView.start();
            }
        });

        btnBack.setOnClickListener(v -> finish());

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
        imgMascot.startAnimation(rotateAnimation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null && videoView.isPlaying()) {
            videoView.pause();
        }
    }
}