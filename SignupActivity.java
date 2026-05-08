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
    private EditText etName, etEmail, etPassword;
    private Button btnSignup;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        // 🔗 Link UI
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);
        tvLogin = findViewById(R.id.tvLogin);

        // 🔘 Signup Click
        btnSignup.setOnClickListener(v -> registerUser(v));

        // 🔗 Go to Login
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser(View view) {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // ❗ Validation
        if (name.isEmpty()) {
            etName.setError("Enter name");
            etName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Enter email");
            etEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter valid email");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Enter password");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        // ⏳ Loading state
        btnSignup.setEnabled(false);
        btnSignup.setText("Creating...");

        // 🔐 Firebase Signup
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    // Reset button
                    btnSignup.setEnabled(true);
                    btnSignup.setText("Sign Up");

                    if (task.isSuccessful()) {

                        // 🔴 Logout after signup (your flow)
                        FirebaseAuth.getInstance().signOut();

                        showMessage(view, "Account created! Please login 😊");

                        // Clear fields
                        etName.setText("");
                        etEmail.setText("");
                        etPassword.setText("");

                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();

                    } else {

                        showMessage(view,
                                task.getException() != null ?
                                        task.getException().getMessage() :
                                        "Signup failed");

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