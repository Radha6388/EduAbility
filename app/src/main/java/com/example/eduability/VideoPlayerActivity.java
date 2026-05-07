package com.example.eduability;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView tvVideoTitle;
    private Button btnPlayVideo, btnBack;
    private ImageView imgMascot;

    // ✅ QUIZ VARIABLES
    private RadioGroup q1, q2, q3, q4, q5;
    private Button btnSubmitQuiz;
    private TextView tvResult;

    // ✅ SOUND
    private MediaPlayer correctSound, wrongSound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = findViewById(R.id.videoView);
        tvVideoTitle = findViewById(R.id.tvVideoTitle);
        btnPlayVideo = findViewById(R.id.btnPlayVideo);
        btnBack = findViewById(R.id.btnBack);
        imgMascot = findViewById(R.id.imgMascot);

        // ✅ QUIZ CONNECTION
        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);
        q4 = findViewById(R.id.q4);
        q5 = findViewById(R.id.q5);
        btnSubmitQuiz = findViewById(R.id.btnSubmitQuiz);
        tvResult = findViewById(R.id.tvResult);

        // ✅ LOAD SOUND
        correctSound = MediaPlayer.create(this, R.raw.correct);
        wrongSound = MediaPlayer.create(this, R.raw.wrong);

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

        // 🔥 QUIZ LOGIC
        if (btnSubmitQuiz != null) {
            btnSubmitQuiz.setOnClickListener(v -> {

                int score = 0;

                resetColors(q1);
                resetColors(q2);
                resetColors(q3);
                resetColors(q4);
                resetColors(q5);

                // ⚠️ Default answers (change if needed per video)
                score += checkAnswer(q1, R.id.q1a);
                score += checkAnswer(q2, R.id.q2a);
                score += checkAnswer(q3, R.id.q3a);
                score += checkAnswer(q4, R.id.q4a);
                score += checkAnswer(q5, R.id.q5a);

                // 🎵 SOUND
                if(score >= 3){
                    correctSound.start();
                } else {
                    wrongSound.start();
                }

                // 🐼 RESULT
                if(score == 5){
                    tvResult.setText("🐼 Amazing! You got 5/5 🌟🌟🌟");
                } else if(score >= 3){
                    tvResult.setText("🐼 Well Done! You got " + score + "/5 ⭐");
                } else {
                    tvResult.setText("🐼 Try again! You got " + score + "/5 💡");
                }
            });
        }

        startMascotWaveAnimation();
    }

    // ✅ CHECK + HIGHLIGHT
    private int checkAnswer(RadioGroup group, int correctId) {

        if(group == null) return 0;

        int selectedId = group.getCheckedRadioButtonId();

        for(int i = 0; i < group.getChildCount(); i++){
            RadioButton rb = (RadioButton) group.getChildAt(i);

            if(rb.getId() == correctId){
                rb.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            }

            if(rb.getId() == selectedId && selectedId != correctId){
                rb.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        }

        return (selectedId == correctId) ? 1 : 0;
    }

    // ✅ RESET COLORS
    private void resetColors(RadioGroup group){
        if(group == null) return;

        for(int i = 0; i < group.getChildCount(); i++){
            RadioButton rb = (RadioButton) group.getChildAt(i);
            rb.setTextColor(getResources().getColor(android.R.color.black));
        }
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