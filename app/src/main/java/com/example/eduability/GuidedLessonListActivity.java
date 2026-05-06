package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GuidedLessonListActivity extends AppCompatActivity {

    LinearLayout lessonAlphabet, lessonNumbers, lessonWords, lessonEmotions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_lesson_list);

        lessonAlphabet = findViewById(R.id.lessonAlphabet);
        lessonNumbers = findViewById(R.id.lessonNumbers);
        lessonWords = findViewById(R.id.lessonWords);
        lessonEmotions = findViewById(R.id.lessonEmotions);

        lessonAlphabet.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuidedLessonContentActivity.class);
            intent.putExtra("lesson", "ALPHABET");
            startActivity(intent);
        });

        lessonNumbers.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuidedLessonContentActivity.class);
            intent.putExtra("lesson", "NUMBERS");
            startActivity(intent);
        });

        lessonWords.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuidedLessonContentActivity.class);
            intent.putExtra("lesson", "WORDS");
            startActivity(intent);
        });

        lessonEmotions.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuidedLessonContentActivity.class);
            intent.putExtra("lesson", "EMOTIONS");
            startActivity(intent);
        });

    }
}
