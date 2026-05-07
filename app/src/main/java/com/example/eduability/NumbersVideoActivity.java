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

import androidx.appcompat.app.AppCompatActivity;

public class NumbersVideoActivity extends AppCompatActivity {

    private VideoView videoViewNumbers;
    private Button btnPlayNumbersVideo, btnBackNumbers;
    private ImageView imgNumbersMascot;
    private TextView tvNumbersVideoTitle;

    // ✅ Quiz variables
    private RadioGroup q1, q2, q3, q4, q5;
    private Button btnSubmitQuiz;
    private TextView tvResult;

    // ✅ Sound
    private MediaPlayer correctSound, wrongSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_video);

        videoViewNumbers = findViewById(R.id.videoViewNumbers);
        btnPlayNumbersVideo = findViewById(R.id.btnPlayNumbersVideo);
        btnBackNumbers = findViewById(R.id.btnBackNumbers);
        imgNumbersMascot = findViewById(R.id.imgNumbersMascot);
        tvNumbersVideoTitle = findViewById(R.id.tvNumbersVideoTitle);

        // ✅ Connect quiz views
        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);
        q4 = findViewById(R.id.q4);
        q5 = findViewById(R.id.q5);
        btnSubmitQuiz = findViewById(R.id.btnSubmitQuiz);
        tvResult = findViewById(R.id.tvResult);

        // ✅ Load sounds
        correctSound = MediaPlayer.create(this, R.raw.correct);
        wrongSound = MediaPlayer.create(this, R.raw.wrong);

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

        // 🔥 QUIZ LOGIC
        btnSubmitQuiz.setOnClickListener(v -> {

            int score = 0;

            // Reset colors
            resetColors(q1);
            resetColors(q2);
            resetColors(q3);
            resetColors(q4);
            resetColors(q5);

            // ✅ Correct answers
            score += checkAnswer(q1, R.id.q1a); // 6
            score += checkAnswer(q2, R.id.q2a); // 3
            score += checkAnswer(q3, R.id.q3a); // 10
            score += checkAnswer(q4, R.id.q4a); // 9
            score += checkAnswer(q5, R.id.q5a); // 4

            // 🎵 Sound
            if(score >= 3){
                correctSound.start();
            } else {
                wrongSound.start();
            }

            // 🐼 Mascot result
            if(score == 5){
                tvResult.setText("🐼 Amazing! You got 5/5 🌟🌟🌟");
            } else if(score >= 3){
                tvResult.setText("🐼 Well Done! You got " + score + "/5 ⭐");
            } else {
                tvResult.setText("🐼 Try again! You got " + score + "/5 💡");
            }
        });

        startMascotWaveAnimation();
    }

    // ✅ Highlight answers
    private int checkAnswer(RadioGroup group, int correctId) {

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

    // ✅ Reset colors
    private void resetColors(RadioGroup group){
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