package com.example.eduability;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MemoryGameActivity extends AppCompatActivity {

    Button[] buttons = new Button[6];

    // 🖼️ Using your images
    int[] images = {
            R.drawable.cow,
            R.drawable.fox,
            R.drawable.fish,
            R.drawable.cow,
            R.drawable.fox,
            R.drawable.fish
    };

    boolean[] matched = new boolean[6];

    int first = -1, second = -1;
    boolean isBusy = false;

    int moves = 0, score = 0, seconds = 0;

    TextView statusText, movesText, scoreText, timerText;

    Handler handler = new Handler();
    Handler timerHandler = new Handler();

    Runnable timerRunnable;

    // 🔊 Sound
    MediaPlayer flipSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);

        statusText = findViewById(R.id.statusText);
        movesText = findViewById(R.id.movesText);
        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);

        // 🔊 Load flip sound
        flipSound = MediaPlayer.create(this, R.raw.flipsound);

        shuffleCards();

        for(int i=0;i<6;i++){
            int id = getResources().getIdentifier("card"+i,"id",getPackageName());
            buttons[i] = findViewById(id);

            int finalI = i;
            buttons[i].setOnClickListener(v -> flipCard(finalI));

            // 🃏 Set default card back
            buttons[i].setBackgroundResource(R.drawable.dialog_emotion_bg);
        }

        updateScoreBoard();
        startTimer();
    }

    private void startTimer(){
        timerRunnable = () -> {
            seconds++;
            timerText.setText("Time: " + seconds + "s");
            timerHandler.postDelayed(timerRunnable,1000);
        };
        timerHandler.post(timerRunnable);
    }

    private void shuffleCards(){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i:images) list.add(i);
        Collections.shuffle(list);
        for(int i=0;i<6;i++) images[i]=list.get(i);
    }

    private void flipCard(int index){

        if(isBusy || matched[index]) return;

        // 🔊 Play flip sound
        if(flipSound != null) flipSound.start();

        // 🎬 Flip animation
        buttons[index].animate().rotationY(90).setDuration(150).withEndAction(() -> {
            buttons[index].setBackgroundResource(images[index]);
            buttons[index].animate().rotationY(0).setDuration(150);
        });

        if(first == -1){
            first = index;
        } else {
            second = index;
            isBusy = true;

            handler.postDelayed(this::checkMatch,700);
        }
    }

    private void checkMatch(){

        moves++;

        if(images[first] == images[second]){
            matched[first] = true;
            matched[second] = true;
            score += 10;
        } else {
            // Flip back
            buttons[first].setBackgroundResource(R.drawable.dialog_emotion_bg);
            buttons[second].setBackgroundResource(R.drawable.dialog_emotion_bg);
            score -= 2;
        }

        updateScoreBoard();

        first = -1;
        second = -1;
        isBusy = false;

        checkWin();
    }

    private void updateScoreBoard(){
        movesText.setText("Moves: " + moves);
        scoreText.setText("Score: " + score);
    }

    private void checkWin(){

        for(boolean b:matched){
            if(!b) return;
        }

        timerHandler.removeCallbacks(timerRunnable);

        String stars;
        if(moves <= 6) stars = "⭐⭐⭐";
        else if(moves <= 10) stars = "⭐⭐";
        else stars = "⭐";

        statusText.setText("🎉 You Win! " + stars);
    }

    public void restartGame(View view){

        moves = 0;
        score = 0;
        seconds = 0;

        for(int i=0;i<6;i++){
            matched[i] = false;
            buttons[i].setBackgroundResource(R.drawable.dialog_emotion_bg);
        }

        shuffleCards();

        updateScoreBoard();
        timerText.setText("Time: 0s");

        startTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(flipSound != null) flipSound.release();
    }
}