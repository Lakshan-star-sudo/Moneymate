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

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputEditText etEmail;
    Button btnResetPassword;
    TextView txtBackToLogin;
    ProgressBar progressResetPassword;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // Connect XML views
        etEmail = findViewById(R.id.etEmail);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        txtBackToLogin = findViewById(R.id.txtBackToLogin);
        progressResetPassword = findViewById(R.id.progressResetPassword);

        // Send password reset email
        btnResetPassword.setOnClickListener(view -> resetPassword());

        // Back to Login page
        txtBackToLogin.setOnClickListener(view -> openLoginPage());
    }

    private void resetPassword() {

        String email = String.valueOf(etEmail.getText()).trim();

        etEmail.setError(null);

        if (email.isEmpty()) {
            etEmail.setError("Enter your email address");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }

        setLoading(true);

        firebaseAuth
                .sendPasswordResetEmail(email)
                .addOnCompleteListener(this, task -> {

                    setLoading(false);

                    if (task.isSuccessful()) {

                        Toast.makeText(
                                ForgotPasswordActivity.this,
                                "Password reset link sent. Check your email.",
                                Toast.LENGTH_LONG
                        ).show();

                        etEmail.setText("");

                        openLoginPage();

                    } else {

                        String errorMessage =
                                "Unable to send password reset link";

                        if (task.getException() != null
                                && task.getException().getMessage() != null) {

                            errorMessage =
                                    task.getException().getMessage();
                        }

                        Toast.makeText(
                                ForgotPasswordActivity.this,
                                errorMessage,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    private void setLoading(boolean isLoading) {

        if (isLoading) {
            progressResetPassword.setVisibility(View.VISIBLE);
            btnResetPassword.setEnabled(false);
            btnResetPassword.setText("Sending...");
        } else {
            progressResetPassword.setVisibility(View.GONE);
            btnResetPassword.setEnabled(true);
            btnResetPassword.setText("Send Reset Link");
        }
    }

    private void openLoginPage() {

        Intent intent = new Intent(
                ForgotPasswordActivity.this,
                LoginActivity.class
        );

        startActivity(intent);
        finish();
    }
}