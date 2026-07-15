package com.nibm.moneymate.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.nibm.moneymate.R;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etEmail,etPassword;
    Button btnLogin;
    TextView txtRegister, txtForgotPassword;
    private ProgressBar progressLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // Connect XML views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        progressLogin = findViewById(R.id.progressLogin);

        // Login button
        btnLogin.setOnClickListener(view -> loginUser());

        // Register page
        txtRegister.setOnClickListener(view -> {
            Intent intent = new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            );
            startActivity(intent);
        });

        // Forgot password page
        txtForgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(
                    LoginActivity.this,
                    ForgotPasswordActivity.class
            );
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // User is already logged in
        if (firebaseAuth.getCurrentUser() != null) {
            openDashboard();
        }
    }

    private void loginUser() {

        String email = String.valueOf(etEmail.getText()).trim();
        String password = String.valueOf(etPassword.getText()).trim();

        // Clear previous errors
        etEmail.setError(null);
        etPassword.setError(null);

        // Email validation
        if (email.isEmpty()) {
            etEmail.setError("Enter email address");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }

        // Password validation
        if (password.isEmpty()) {
            etPassword.setError("Enter password");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError(
                    "Password must contain at least 6 characters"
            );
            etPassword.requestFocus();
            return;
        }

        setLoading(true);

        firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {

                    setLoading(false);

                    if (task.isSuccessful()) {
                        Toast.makeText(
                                LoginActivity.this,
                                "Login successful",
                                Toast.LENGTH_SHORT).show();
                              openDashboard();

                    } else {
                        Toast.makeText(
                                LoginActivity.this,
                                "Invalid email or password",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setLoading(boolean isLoading) {

        if (isLoading) {
            progressLogin.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);
            btnLogin.setText("Logging in...");
        } else {
            progressLogin.setVisibility(View.GONE);
            btnLogin.setEnabled(true);
            btnLogin.setText("Login");
        }
    }

    private void openDashboard() {

        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);

        // User cannot return to Login page using Back button
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}