package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GuidedLearningActivity extends AppCompatActivity {

    LinearLayout cardGuidedLessons, cardTherapist, cardParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_learning);

        cardGuidedLessons = findViewById(R.id.cardGuidedLessons);
        cardTherapist = findViewById(R.id.cardTherapist);
        cardParent = findViewById(R.id.cardParent);

        // 📘 Guided Lessons
        cardGuidedLessons.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuidedLessonListActivity.class);
            startActivity(intent);
        });

        // 🧠 Therapist Activities
        cardTherapist.setOnClickListener(v -> {
                    Intent intent = new Intent(this, TherapistActivity.class);
                    startActivity(intent);
                });

        // 👨‍👩‍👧 Parent Guidance
        cardParent.setOnClickListener(v -> {
            Intent intent = new Intent(this, ParentGuidanceActivity.class);
            startActivity(intent);
        } );
    }
}
