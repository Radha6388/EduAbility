package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView tvScore, tvMessage;
    ImageView imgResult;
    Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvScore = findViewById(R.id.tvScore);
        tvMessage = findViewById(R.id.tvMessage);
        imgResult = findViewById(R.id.imgResult);
        btnBackHome = findViewById(R.id.btnBackHome);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 5);

        tvScore.setText("Score: " + score + " / " + total);

        if (score == total) {
            tvMessage.setText("🌟 Excellent! Perfect score!");
            imgResult.setImageResource(R.drawable.celebration);
        } else if (score >= total / 2) {
            tvMessage.setText("😊 Good job! Keep practicing!");
            imgResult.setImageResource(R.drawable.good_job);
        } else {
            tvMessage.setText("💪 Don’t worry! Try again!");
            imgResult.setImageResource(R.drawable.try_again);
        }

        btnBackHome.setOnClickListener(v -> {
            startActivity(new Intent(ResultActivity.this, HomeActivity.class));
            finish();
        });
    }
}
