package com.example.eduability;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class BaseballActivity extends AppCompatActivity {

    ImageView imgBall, imgPlayer;
    TextView tvScore, tvLives;
    FrameLayout rootLayout;

    int score = 0;
    int lives = 3;

    float speedY = 18f;
    float speedX = 5f;

    float playerY;

    Handler handler = new Handler();
    Random random = new Random();

    boolean isHit = false;

    MediaPlayer hitSound, missSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_baseball);

        rootLayout = findViewById(R.id.rootLayout);
        imgBall = findViewById(R.id.imgBall);
        imgPlayer = findViewById(R.id.imgPlayer);
        tvScore = findViewById(R.id.tvScore);
        tvLives = findViewById(R.id.tvLives);

        hitSound = MediaPlayer.create(this, R.raw.hit);
        missSound = MediaPlayer.create(this, R.raw.miss);

        // Wait until layout is ready (IMPORTANT FIX)
        rootLayout.post(() -> {
            playerY = imgPlayer.getY();
            startGame(); // ✅ start AFTER layout ready
        });

        // Touch control
        rootLayout.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                float x = event.getX() - (imgPlayer.getWidth() / 2f);

                if (x < 0) x = 0;
                if (x > rootLayout.getWidth() - imgPlayer.getWidth())
                    x = rootLayout.getWidth() - imgPlayer.getWidth();

                imgPlayer.setX(x);
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.performClick(); // fix warning
                swingPlayer();
                checkHit();
            }

            return true;
        });
    }

    private void startGame() {
        spawnBall();
    }

    // ⚾ SAFE spawn
    private void spawnBall() {
        isHit = false;

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        float randomX = random.nextInt(screenWidth - 150); // ✅ FIXED

        imgBall.setX(randomX);
        imgBall.setY(0);

        speedX = random.nextBoolean() ? 5 : -5;

        moveBall();
    }

    private void moveBall() {
        handler.postDelayed(() -> {

            imgBall.setY(imgBall.getY() + speedY);
            imgBall.setX(imgBall.getX() + speedX);

            if (imgBall.getX() <= 0 ||
                    imgBall.getX() >= rootLayout.getWidth() - imgBall.getWidth()) {
                speedX *= -1;
            }

            if (!isHit && imgBall.getY() >= playerY) {
                missBall();
            } else if (!isHit) {
                moveBall();
            }

        }, 20);
    }

    private void checkHit() {

        float ballX = imgBall.getX();
        float playerX = imgPlayer.getX();

        if (Math.abs(ballX - playerX) < 200 &&
                imgBall.getY() >= playerY - 150 &&
                !isHit) {

            isHit = true;

            score++;
            speedY += 1.3f;
            speedX *= 1.1f;

            tvScore.setText("Score: " + score);

            if (hitSound != null) hitSound.start();

            imgBall.animate()
                    .translationY(-800)
                    .rotationBy(360)
                    .setDuration(400)
                    .withEndAction(this::spawnBall)
                    .start();
        }
    }

    private void missBall() {
        lives--;
        tvLives.setText("❤️ " + lives);

        if (missSound != null) missSound.start();

        if (lives <= 0) {
            endGame();
        } else {
            spawnBall();
        }
    }

    private void swingPlayer() {

        RotateAnimation rotate = new RotateAnimation(
                0, -25,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 1f
        );

        rotate.setDuration(120);
        rotate.setRepeatMode(RotateAnimation.REVERSE);
        rotate.setRepeatCount(1);

        imgPlayer.startAnimation(rotate);
    }

    private void endGame() {
        new AlertDialog.Builder(this)
                .setTitle("Game Over ⚾")
                .setMessage("🔥 Final Score: " + score)
                .setCancelable(false)
                .setPositiveButton("Play Again", (d, w) -> recreate())
                .setNegativeButton("Exit", (d, w) -> finish())
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (hitSound != null) hitSound.release();
        if (missSound != null) missSound.release();
    }
}