package com.nibm.moneymate.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.nibm.moneymate.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etResetEmail;
    Button btnSendReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        etResetEmail = findViewById(R.id.etResetEmail);
        btnSendReset = findViewById(R.id.btnSendReset);

        btnSendReset.setOnClickListener(v -> {
            String email = etResetEmail.getText().toString();
            if (!email.isEmpty()) {
                Toast.makeText(this, "Reset link sent to your email!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            }
        });
    }
}