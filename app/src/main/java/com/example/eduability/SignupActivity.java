package com.example.eduability;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEmail, etPassword;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnSignup = findViewById(R.id.btnSignup);
        tvLogin = findViewById(R.id.tvLogin); // 🔹 THIS LINE IS IMPORTANT

        // Signup button
        btnSignup.setOnClickListener(v -> registerUser(v));

        // Login text click
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser(View view) {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showMessage(view, "Please fill all fields");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        // 🔴 Logout after signup (important for your flow)
                        FirebaseAuth.getInstance().signOut();

                        showMessage(view, "Account created! Please login.");

                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();

                    } else {
                        showMessage(view, task.getException().getMessage());
                    }
                });
    }

    private void showMessage(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.BLACK)
                .setTextColor(Color.WHITE)
                .show();
    }
}
