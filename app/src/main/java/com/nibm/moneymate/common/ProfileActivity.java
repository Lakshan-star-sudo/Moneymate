package com.nibm.moneymate.common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.nibm.moneymate.R;
import com.nibm.moneymate.auth.LoginActivity;

public class ProfileActivity extends AppCompatActivity {

    Button btnEditProfile, btnResetPassword, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        btnLogout = findViewById(R.id.btn_logout);

        btnEditProfile.setOnClickListener(v -> Toast.makeText(this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show());
        btnResetPassword.setOnClickListener(v -> Toast.makeText(this, "Reset Password Clicked", Toast.LENGTH_SHORT).show());

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}