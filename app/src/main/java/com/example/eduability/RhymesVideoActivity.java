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
import android.widget.RadioGroup; // ✅ ADDED

import androidx.appcompat.app.AppCompatActivity;

public class RhymesVideoActivity extends AppCompatActivity {

    private VideoView videoViewRhymes;
    private Button btnPlayRhymesVideo, btnBackRhymes;
    private ImageView imgRhymesMascot;
    private TextView tvRhymesVideoTitle;

    // ✅ ADDED (Quiz variables)
    private RadioGroup q1, q2, q3;
    private Button btnSubmitQuiz;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhymes_video);

        videoViewRhymes = findViewById(R.id.videoViewRhymes);
        btnPlayRhymesVideo = findViewById(R.id.btnPlayRhymesVideo);
        btnBackRhymes = findViewById(R.id.btnBackRhymes);
        imgRhymesMascot = findViewById(R.id.imgRhymesMascot);
        tvRhymesVideoTitle = findViewById(R.id.tvRhymesVideoTitle);

        // ✅ ADDED (connect quiz views)
        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);
        btnSubmitQuiz = findViewById(R.id.btnSubmitQuiz);
        tvResult = findViewById(R.id.tvResult);

        String videoTitle = getIntent().getStringExtra("videoTitle");
        int videoResId = getIntent().getIntExtra("videoResId", -1);

        if (videoTitle != null) {
            tvRhymesVideoTitle.setText(videoTitle);
        }

        if (videoResId != -1) {
            String videoPath = "android.resource://" + getPackageName() + "/" + videoResId;
            Uri uri = Uri.parse(videoPath);

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoViewRhymes);
            videoViewRhymes.setMediaController(mediaController);
            videoViewRhymes.setVideoURI(uri);

            videoViewRhymes.setOnPreparedListener(mp -> videoViewRhymes.seekTo(100));
        }

        btnPlayRhymesVideo.setOnClickListener(v -> {
            if (!videoViewRhymes.isPlaying()) {
                videoViewRhymes.start();
            }
        });

        btnBackRhymes.setOnClickListener(v -> finish());

        // ✅ ADDED (Quiz evaluation logic)
        btnSubmitQuiz.setOnClickListener(v -> {

            int score = 0;

            if(q1.getCheckedRadioButtonId() == R.id.q1a) score++;
            if(q2.getCheckedRadioButtonId() == R.id.q2a) score++;
            if(q3.getCheckedRadioButtonId() == R.id.q3a) score++;

            if(score == 3){
                tvResult.setText("🌟 Amazing! You know Baby Shark!");
            } else if(score == 2){
                tvResult.setText("😊 Good job! Try again!");
            } else {
                tvResult.setText("💡 Watch again and learn!");
            }
        });

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
        imgRhymesMascot.startAnimation(rotateAnimation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoViewRhymes != null && videoViewRhymes.isPlaying()) {
            videoViewRhymes.pause();
        }
    }
}