package com.nibm.moneymate.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nibm.moneymate.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private Button btnResetPassword;
    private TextView txtBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();

        etEmail = findViewById(R.id.etEmail);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        txtBackToLogin = findViewById(R.id.txtBackToLogin);

        btnResetPassword.setOnClickListener(
                v -> {
                    String email = etEmail.getText().toString().trim();

                    if (email.isEmpty()) {
                        etEmail.setError("Please enter your email");
                        etEmail.requestFocus();
                        return;
                    }

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        etEmail.setError("Please enter a valid email");
                        etEmail.requestFocus();
                        return;
                    }

                    Toast.makeText(
                            ForgotPasswordActivity.this,
                            "Password reset link sent successfully!",
                            Toast.LENGTH_LONG
                    ).show();

                });

        // Back to Login
        txtBackToLogin.setOnClickListener(
                v -> {
                    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class
                    );
                    startActivity(intent);
                    finish();

                });

    }
}
