package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;

public class EntryActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    Button btnNext;
    SlideAdapter adapter;
    FirebaseAuth mAuth;

    // ✅ Dots
    LinearLayout dotsLayout;
    View[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔐 AUTO LOGIN CHECK
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_entry);

        // 🔗 Link UI
        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        dotsLayout = findViewById(R.id.dotsLayout);

        // 🔧 Setup adapter
        adapter = new SlideAdapter();
        viewPager.setAdapter(adapter);

        // ❌ Disable swipe (optional)
        viewPager.setUserInputEnabled(false);

        // ✅ Initialize dots
        addDots(0);

        // 👉 Button click
        btnNext.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem();

            if (current < adapter.getItemCount() - 1) {
                viewPager.setCurrentItem(current + 1, true);
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });

        // ✅ Page change listener
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                addDots(position);

                // 🔄 Change button text
                if (position == adapter.getItemCount() - 1) {
                    btnNext.setText("Get Started");
                } else {
                    btnNext.setText("Next");
                }
            }
        });
    }

    // ✅ DOT FUNCTION
    private void addDots(int position) {
        dots = new View[adapter.getItemCount()];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new View(this);

            if (i == position) {
                dots[i].setBackgroundResource(R.drawable.dot_indicator_active);
            } else {
                dots[i].setBackgroundResource(R.drawable.dot_indicator_inactive);
            }

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(20, 20);
            params.setMargins(8, 0, 8, 0);

            dotsLayout.addView(dots[i], params);
        }
    }
}