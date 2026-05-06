package com.example.eduability;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    TextView tvQuestion, tvScore, tvProgress;
    Button btnOption1, btnOption2, btnOption3, btnNext;

    int currentIndex = 0;
    int score = 0;
    boolean answered = false;

    ArrayList<Question> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        tvProgress = findViewById(R.id.tvProgress);

        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnNext = findViewById(R.id.btnNext);

        loadQuestions();
        Collections.shuffle(questionList);

        showQuestion();

        btnOption1.setOnClickListener(v -> checkAnswer(btnOption1.getText().toString()));
        btnOption2.setOnClickListener(v -> checkAnswer(btnOption2.getText().toString()));
        btnOption3.setOnClickListener(v -> checkAnswer(btnOption3.getText().toString()));

        btnNext.setOnClickListener(v -> {

            if (!answered) {
                Toast.makeText(this, "👉 Please select an answer first", Toast.LENGTH_SHORT).show();
                return;
            }

            currentIndex++;

            if (currentIndex < questionList.size()) {
                showQuestion();
            } else {
                finishQuiz();
            }
        });
    }

    private void loadQuestions() {

        questionList.add(new Question("A for ?", "Apple", "Ball", "Cat", "Apple"));
        questionList.add(new Question("B for ?", "Dog", "Ball", "Fish", "Ball"));

        questionList.add(new Question("How many fingers on one hand?", "3", "5", "10", "5"));
        questionList.add(new Question("Which comes after 2?", "1", "3", "5", "3"));

        questionList.add(new Question("Which shape has 3 sides?", "Circle", "Triangle", "Square", "Triangle"));

        questionList.add(new Question("CAT is a ?", "Animal", "Fruit", "Color", "Animal"));
    }

    private void showQuestion() {

        answered = false;

        Question q = questionList.get(currentIndex);

        tvQuestion.setText(q.question);
        btnOption1.setText(q.option1);
        btnOption2.setText(q.option2);
        btnOption3.setText(q.option3);

        tvScore.setText("Score: " + score);
        tvProgress.setText("Question " + (currentIndex + 1) + " / " + questionList.size());

        enableOptions(true);
    }

    private void checkAnswer(String selected) {

        if (answered) return;

        answered = true;
        enableOptions(false);

        Question q = questionList.get(currentIndex);

        if (selected.equals(q.correctAnswer)) {
            score++;
            tvScore.setText("Score: " + score);
            Toast.makeText(this, "✅ Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(
                    this,
                    "❌ Wrong!\nCorrect: " + q.correctAnswer,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // 🔥 NEW FUNCTION (IMPORTANT)
    private void finishQuiz() {

        int total = questionList.size();

        int progress = (score * 100) / total;

        // ✅ SAVE QUIZ PROGRESS
        ProgressHelper.saveProgress(this, ProgressHelper.KEY_QUIZ, progress);

        Toast.makeText(
                this,
                "🎉 Quiz Finished!\nScore: " + score + " / " + total,
                Toast.LENGTH_LONG
        ).show();

        finish();
    }

    private void enableOptions(boolean enable) {
        btnOption1.setEnabled(enable);
        btnOption2.setEnabled(enable);
        btnOption3.setEnabled(enable);
    }

    static class Question {
        String question, option1, option2, option3, correctAnswer;

        Question(String q, String o1, String o2, String o3, String ans) {
            question = q;
            option1 = o1;
            option2 = o2;
            option3 = o3;
            correctAnswer = ans;
        }
    }
}