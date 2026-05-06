package com.example.eduability;

import android.content.Context;
import android.graphics.*;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Random;

public class CatchGameView extends View {

    Paint paint;

    Bitmap basket, fruit, bomb;

    int basketX = 300;
    int score = 0;
    int lives = 3;

    TextView txtScore, txtLives;

    ArrayList<FallingObject> objects = new ArrayList<>();
    Random random = new Random();

    // 🎵 SOUND VARIABLES (only effects)
    MediaPlayer catchSound, bombSound;

    public CatchGameView(Context context, TextView txtScore, TextView txtLives) {
        super(context);

        this.txtScore = txtScore;
        this.txtLives = txtLives;

        paint = new Paint();

        basket = BitmapFactory.decodeResource(getResources(), R.drawable.basket);
        fruit = BitmapFactory.decodeResource(getResources(), R.drawable.apple_bg);
        bomb = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);

        basket = Bitmap.createScaledBitmap(basket, 200, 120, false);
        fruit = Bitmap.createScaledBitmap(fruit, 100, 100, false);
        bomb = Bitmap.createScaledBitmap(bomb, 100, 100, false);

        // 🎵 Initialize sound effects only
        catchSound = MediaPlayer.create(context, R.raw.catch_fun);
        bombSound = MediaPlayer.create(context, R.raw.boom);

        // Create objects
        for (int i = 0; i < 2; i++) {
            spawnObject();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        // Draw basket
        int basketY = getHeight() - basket.getHeight() - 20;
        canvas.drawBitmap(basket, basketX, basketY, paint);

        for (FallingObject obj : objects) {

            canvas.drawBitmap(obj.image, obj.x, obj.y, paint);

            obj.y += obj.speed;

            // 🎯 Collision
            if (obj.y >= getHeight() - basket.getHeight() - 20 &&
                    obj.x > basketX &&
                    obj.x < basketX + basket.getWidth()) {

                if (obj.isBomb) {

                    if (bombSound != null) bombSound.start();

                    new Handler().postDelayed(() -> {
                        gameOver();
                    }, 300); // 300ms delay

                    return;

                } else {
                    score++;
                    updateUI();

                    if (catchSound != null) catchSound.start(); // 🍎 sound
                }

                resetObject(obj);
            }

            // ❌ Missed
            if (obj.y > getHeight()) {
                if (!obj.isBomb) {
                    lives--;
                    updateUI();

                    if (lives <= 0) {
                        gameOver();
                        return;
                    }
                }
                resetObject(obj);
            }
        }

        postInvalidateDelayed(30); // smooth & slow
    }

    private void spawnObject() {
        boolean isBomb = random.nextInt(7) == 0;

        Bitmap img = isBomb ? bomb : fruit;

        objects.add(new FallingObject(
                img,
                random.nextInt(300),
                -300,
                5 + random.nextInt(6),
                isBomb
        ));
    }

    private void resetObject(FallingObject obj) {
        obj.y = 0;
        obj.x = random.nextInt(getWidth() - obj.image.getWidth());
        obj.speed = 5 + random.nextInt(6);
        obj.isBomb = random.nextInt(6) == 0;
        obj.image = obj.isBomb ? bomb : fruit;
    }

    private void updateUI() {
        txtScore.setText("🏆 " + score);

        String hearts = "";
        for (int i = 0; i < lives; i++) hearts += "❤️";

        txtLives.setText(hearts);
    }

    private void gameOver() {
        ((CatchGameActivity) getContext()).showGameOver(score);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        basketX = (int) event.getX() - basket.getWidth() / 2;
        return true;
    }

    // 🔥 Release memory
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (catchSound != null) catchSound.release();
        if (bombSound != null) bombSound.release();
    }
}