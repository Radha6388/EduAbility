package com.example.eduability;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class TicTacToeActivity extends AppCompatActivity {

    Button[] buttons = new Button[9];
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    boolean gameActive = true;

    int playerScore = 0;
    int aiScore = 0;

    int[][] winPositions = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
    };

    TextView statusText, scoreBoard;
    Random random = new Random();

    MediaPlayer tapSound, winSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        statusText = findViewById(R.id.statusText);
        scoreBoard = findViewById(R.id.scoreBoard);

        // 🔊 Load sounds (you already added in raw)
        tapSound = MediaPlayer.create(this, R.raw.tap);
        winSound = MediaPlayer.create(this, R.raw.win);

        updateScore();

        for (int i = 0; i < 9; i++) {
            String buttonID = "btn" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);

            int finalI = i;
            buttons[i].setOnClickListener(v -> playerMove(finalI));
        }

        statusText.setText("👉 Your Turn (X)");
    }

    // 👤 PLAYER MOVE
    private void playerMove(int index) {

        if (gameState[index] != 2 || !gameActive) return;

        gameState[index] = 0;
        buttons[index].setText("X");

        // 🔊 Tap sound
        if (tapSound != null) tapSound.start();

        animateButton(buttons[index]);

        if (checkWinner()) return;

        if (isDraw()) {
            statusText.setText("It's a Draw 🤝");
            gameActive = false;
            return;
        }

        statusText.setText("🤖 AI Thinking...");
        buttons[index].postDelayed(this::aiMove, 500);
    }

    // 🤖 AI MOVE
    private void aiMove() {

        if (!gameActive) return;

        int move = getBestMove();

        if (move != -1) {
            gameState[move] = 1;
            buttons[move].setText("O");

            animateButton(buttons[move]);

            if (checkWinner()) return;

            if (isDraw()) {
                statusText.setText("It's a Draw 🤝");
                gameActive = false;
                return;
            }
        }

        statusText.setText("👉 Your Turn (X)");
    }

    // 🤖 AI LOGIC
    private int getBestMove() {

        for (int i = 0; i < 9; i++) {
            if (gameState[i] == 2) {
                gameState[i] = 1;
                if (checkTemporaryWin()) {
                    gameState[i] = 2;
                    return i;
                }
                gameState[i] = 2;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (gameState[i] == 2) {
                gameState[i] = 0;
                if (checkTemporaryWin()) {
                    gameState[i] = 2;
                    return i;
                }
                gameState[i] = 2;
            }
        }

        int move;
        do {
            move = random.nextInt(9);
        } while (gameState[move] != 2);

        return move;
    }

    private boolean checkTemporaryWin() {
        for (int[] win : winPositions) {
            if (gameState[win[0]] == gameState[win[1]] &&
                    gameState[win[1]] == gameState[win[2]] &&
                    gameState[win[0]] != 2) {
                return true;
            }
        }
        return false;
    }

    private boolean checkWinner() {
        for (int[] win : winPositions) {

            if (gameState[win[0]] == gameState[win[1]] &&
                    gameState[win[1]] == gameState[win[2]] &&
                    gameState[win[0]] != 2) {

                gameActive = false;

                String winner = gameState[win[0]] == 0 ? "You" : "AI";

                // 🔊 Win sound
                if (winSound != null) winSound.start();

                if (winner.equals("You")) {
                    playerScore++;
                } else {
                    aiScore++;
                }

                updateScore();

                statusText.setText(winner + " Wins! 🎉");

                highlightWin(win);
                return true;
            }
        }
        return false;
    }

    private void updateScore() {
        scoreBoard.setText("You: " + playerScore + "  |  AI: " + aiScore);
    }

    private boolean isDraw() {
        for (int state : gameState) {
            if (state == 2) return false;
        }
        return true;
    }

    private void highlightWin(int[] winPositions) {
        for (int index : winPositions) {
            buttons[index].setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#A5D6A7"))
            );
        }
    }

    private void animateButton(Button btn) {
        btn.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100)
                .withEndAction(() ->
                        btn.animate().scaleX(1f).scaleY(1f).setDuration(100)
                );
    }

    public void resetGame(View view) {

        gameActive = true;

        for (int i = 0; i < 9; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
            buttons[i].setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
            );
        }

        statusText.setText("👉 Your Turn (X)");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (tapSound != null) tapSound.release();
        if (winSound != null) winSound.release();
    }
}