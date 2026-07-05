
package com.nibm.moneymate.common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nibm.moneymate.R;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etFullName, etEmail, etPhone, etPassword, etConfirmPassword;
    Button btnRegister;
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();


        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        // Register Button
        btnRegister.setOnClickListener(
                v -> {
                    String fullName = etFullName.getText().toString().trim();
                    String email = etEmail.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    String confirmPassword = etConfirmPassword.getText().toString().trim();

                    if (fullName.isEmpty()) {
                        etFullName.setError("Enter Full Name");
                        etFullName.requestFocus();
                        return;
                    }

                    if (email.isEmpty()) {
                        etEmail.setError("Enter Email");
                        etEmail.requestFocus();
                        return;
                    }

                    if (phone.isEmpty()) {
                        etPhone.setError("Enter Phone Number");
                        etPhone.requestFocus();
                        return;
                    }

                    if (password.isEmpty()) {
                        etPassword.setError("Enter Password");
                        etPassword.requestFocus();
                        return;
                    }

                    if (confirmPassword.isEmpty()) {
                        etConfirmPassword.setError("Confirm Password");
                        etConfirmPassword.requestFocus();
                        return;
                    }

                    if (!password.equals(confirmPassword)) {
                        etConfirmPassword.setError("Passwords do not match");
                        etConfirmPassword.requestFocus();
                        return;
                    }

                    Toast.makeText(RegisterActivity.this,
                            "Registration Successful",
                            Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                });

        // Already have account
        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
}