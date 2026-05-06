package com.example.eduability;

import android.content.Intent;

import android.os.Bundle;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;

public class EntryActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    Button btnNext;

    SlideAdapter adapter;

    FirebaseAuth mAuth;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        // 🔐 AUTO LOGIN CHECK (SESSION)

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {

            startActivity(new Intent(this, HomeActivity.class));

            finish();

            return; // VERY IMPORTANT

        }



        // 👇 Only runs if user is NOT logged in

        setContentView(R.layout.activity_entry);


        viewPager = findViewById(R.id.viewPager);

        btnNext = findViewById(R.id.btnNext);



        adapter = new SlideAdapter();

        viewPager.setAdapter(adapter);



        // ❌ Disable swipe (button-only navigation)

        viewPager.setUserInputEnabled(false);



        btnNext.setOnClickListener(v -> {

            int current = viewPager.getCurrentItem();



            if (current < adapter.getItemCount() - 1) {

                viewPager.setCurrentItem(current + 1, true);

            } else {

                startActivity(new Intent(this, LoginActivity.class));

                finish();

            }

        });

    }

}