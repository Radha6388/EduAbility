package com.example.eduability;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CatchGameActivity extends AppCompatActivity {

    TextView txtScore, txtLives;
    CatchGameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_game);

        txtScore = findViewById(R.id.txtScore);
        txtLives = findViewById(R.id.txtLives);

        FrameLayout container = findViewById(R.id.gameContainer);

        gameView = new CatchGameView(this, txtScore, txtLives);
        container.addView(gameView);
    }

    // 💀 Game Over with animated mascot
    public void showGameOver(int score) {
        setContentView(R.layout.game_over_layout);

        TextView finalScore = findViewById(R.id.txtFinalScore);
        finalScore.setText("🏆 Score: " + score);

        ImageView mascot = findViewById(R.id.imgMascot);

        // 🟢 Jump animation
        ObjectAnimator jumpUp = ObjectAnimator.ofFloat(mascot, "translationY", 0f, -60f);
        jumpUp.setDuration(400);

        ObjectAnimator jumpDown = ObjectAnimator.ofFloat(mascot, "translationY", -60f, 0f);
        jumpDown.setDuration(400);

        AnimatorSet jumpSet = new AnimatorSet();
        jumpSet.playSequentially(jumpUp, jumpDown);
        jumpSet.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                jumpSet.start(); // restart animation
            }
        });

        // 🟡 Cute rotation (side to side)
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mascot, "rotation", 0f, 8f, -8f, 0f);
        rotate.setDuration(600);
        rotate.setRepeatCount(ObjectAnimator.INFINITE);

        // 🔵 Pulse effect (zoom in/out)
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mascot, "scaleX", 1f, 1.1f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mascot, "scaleY", 1f, 1.1f, 1f);
        scaleX.setDuration(800);
        scaleY.setDuration(800);
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);

        // 🔥 Start all animations
        jumpSet.start();
        rotate.start();
        scaleX.start();
        scaleY.start();
    }
}