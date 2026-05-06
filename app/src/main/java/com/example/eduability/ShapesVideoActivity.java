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

public class ShapesVideoActivity extends AppCompatActivity {

    private VideoView videoViewShapes;
    private Button btnPlayShapesVideo, btnBackShapes;
    private ImageView imgShapesMascot;
    private TextView tvShapesVideoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes_video);

        videoViewShapes = findViewById(R.id.videoViewShapes);
        btnPlayShapesVideo = findViewById(R.id.btnPlayShapesVideo);
        btnBackShapes = findViewById(R.id.btnBackShapes);
        imgShapesMascot = findViewById(R.id.imgShapesMascot);
        tvShapesVideoTitle = findViewById(R.id.tvShapesVideoTitle);

        String videoTitle = getIntent().getStringExtra("videoTitle");
        int videoResId = getIntent().getIntExtra("videoResId", -1);

        if (videoTitle != null) {
            tvShapesVideoTitle.setText(videoTitle);
        }

        if (videoResId != -1) {
            String videoPath = "android.resource://" + getPackageName() + "/" + videoResId;
            Uri uri = Uri.parse(videoPath);

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoViewShapes);
            videoViewShapes.setMediaController(mediaController);
            videoViewShapes.setVideoURI(uri);

            videoViewShapes.setOnPreparedListener(mp -> videoViewShapes.seekTo(100));
        }

        btnPlayShapesVideo.setOnClickListener(v -> {
            if (!videoViewShapes.isPlaying()) {
                videoViewShapes.start();
            }
        });

        btnBackShapes.setOnClickListener(v -> finish());

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
        imgShapesMascot.startAnimation(rotateAnimation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoViewShapes != null && videoViewShapes.isPlaying()) {
            videoViewShapes.pause();
        }
    }
}