package com.example.eduability;

import android.graphics.Bitmap;

public class FallingObject {
    Bitmap image;
    int x, y;
    int speed;
    boolean isBomb;

    public FallingObject(Bitmap image, int x, int y, int speed, boolean isBomb) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.isBomb = isBomb;
    }
}