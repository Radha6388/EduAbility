package com.example.eduability;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressActivity extends AppCompatActivity {

    ProgressBar progressAlphabet, progressNumbers, progressWords,
            progressShapes, progressQuiz, progressEmotion, progressFindLetter;

    TextView tvOverall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        progressAlphabet = findViewById(R.id.progressAlphabet);
        progressNumbers = findViewById(R.id.progressNumbers);
        progressWords = findViewById(R.id.progressWords);
        progressShapes = findViewById(R.id.progressShapes);
        progressQuiz = findViewById(R.id.progressQuiz);
        progressEmotion = findViewById(R.id.progressEmotion);
        progressFindLetter = findViewById(R.id.progressFindLetter);

        tvOverall = findViewById(R.id.tvOverallProgress);

        loadProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProgress();
    }

    private void loadProgress() {
        int alphabet = ProgressHelper.getProgress(this, ProgressHelper.KEY_ALPHABET);
        int numbers = ProgressHelper.getProgress(this, ProgressHelper.KEY_NUMBERS);
        int words = ProgressHelper.getProgress(this, ProgressHelper.KEY_WORDS);
        int shapes = ProgressHelper.getProgress(this, ProgressHelper.KEY_SHAPES);
        int quiz = ProgressHelper.getProgress(this, ProgressHelper.KEY_QUIZ);
        int emotion = ProgressHelper.getProgress(this, ProgressHelper.KEY_EMOTION);
        int findLetter = ProgressHelper.getProgress(this, ProgressHelper.KEY_FIND_LETTER);

        progressAlphabet.setProgress(alphabet);
        progressNumbers.setProgress(numbers);
        progressWords.setProgress(words);
        progressShapes.setProgress(shapes);
        progressQuiz.setProgress(quiz);
        progressEmotion.setProgress(emotion);
        progressFindLetter.setProgress(findLetter);

        int overall = (alphabet + numbers + words + shapes + quiz + emotion + findLetter) / 7;
        tvOverall.setText("Overall Progress : " + overall + "%");
    }
}