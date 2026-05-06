package com.example.eduability;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ParentGuidanceActivity extends AppCompatActivity {

    Button btnHowToUse, btnRoutine, btnTips, btnProgress, btnHelp;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_guidance);

        btnHowToUse = findViewById(R.id.btnHowToUse);
        btnRoutine = findViewById(R.id.btnRoutine);
        btnTips = findViewById(R.id.btnTips);
        btnProgress = findViewById(R.id.btnProgress);
        btnHelp = findViewById(R.id.btnHelp);
        tvContent = findViewById(R.id.tvContent);

        btnHowToUse.setOnClickListener(v ->
                tvContent.setText(
                        "EduAbility is designed to help children learn step by step.\n\n" +
                                "• Start with Learning modules\n" +
                                "• Use Play & Learn for focus\n" +
                                "• Repeat lessons daily\n" +
                                "• Always guide your child gently"
                ));

        btnRoutine.setOnClickListener(v ->
                tvContent.setText(
                        "Recommended Daily Routine:\n\n" +
                                "🕘 10–15 mins: Alphabet / Numbers\n" +
                                "🎮 5–10 mins: Play & Learn\n" +
                                "📖 10 mins: Read & Listen\n" +
                                "🧠 Short breaks are important!"
                ));

        btnTips.setOnClickListener(v ->
                tvContent.setText(
                        "Learning Tips for Parents:\n\n" +
                                "• Sit with your child initially\n" +
                                "• Encourage, don’t force\n" +
                                "• Repeat lessons calmly\n" +
                                "• Celebrate small progress"
                ));

        btnProgress.setOnClickListener(v ->
                tvContent.setText(
                        "Understanding Progress:\n\n" +
                                "✔ Quiz scores show learning\n" +
                                "✔ Games improve focus\n" +
                                "✔ Consistency matters more than speed"
                ));

        btnHelp.setOnClickListener(v ->
                tvContent.setText(
                        "When to Seek Help:\n\n" +
                                "• Child shows frustration\n" +
                                "• No improvement over time\n" +
                                "• Speech or attention issues\n\n" +
                                "Consult a therapist or educator."
                ));
    }
}
