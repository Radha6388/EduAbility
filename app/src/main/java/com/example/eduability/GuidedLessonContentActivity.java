package com.example.eduability;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuidedLessonContentActivity extends AppCompatActivity {

    TextView tvLessonTitle, tvLessonContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_lesson_content);

        tvLessonTitle = findViewById(R.id.tvLessonTitle);
        tvLessonContent = findViewById(R.id.tvLessonContent);

        String lesson = getIntent().getStringExtra("lesson");

        if ("ALPHABET".equals(lesson)) {
            tvLessonTitle.setText("Alphabet Guided Lesson");
            tvLessonContent.setText(
                    "• Recognize capital letters\n" +
                            "• Match letter with sound\n" +
                            "• Practice A, B, C with teacher help"
            );

        } else if ("NUMBERS".equals(lesson)) {
            tvLessonTitle.setText("Numbers Guided Lesson");
            tvLessonContent.setText(
                    "• Count objects\n" +
                            "• Identify numbers\n" +
                            "• Practice 1 to 5 slowly"
            );

        } else if ("WORDS".equals(lesson)) {
            tvLessonTitle.setText("Words Guided Lesson");
            tvLessonContent.setText(
                    "• Read simple words\n" +
                            "• Understand meaning\n" +
                            "• Use pictures and sound"
            );

        } else if ("EMOTIONS".equals(lesson)) {
            tvLessonTitle.setText("Emotion Guided Lesson");
            tvLessonContent.setText(
                    "• Identify emotions\n" +
                            "• Understand facial expressions\n" +
                            "• Practice with examples"
            );
        }
    }
}
