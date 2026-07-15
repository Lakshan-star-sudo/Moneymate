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

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nibm.moneymate.R;
import com.nibm.moneymate.model.User;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etFullName,etEmail,  etPhone,etPassword,etConfirmPassword;
    Button btnRegister;
    private TextView txtLogin;
    ProgressBar progressRegister;

     FirebaseAuth firebaseAuth;
     FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();


        // Firebase initialization
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);
        progressRegister = findViewById(R.id.progressRegister);

        btnRegister.setOnClickListener(view -> registerUser());

        txtLogin.setOnClickListener(view -> openLoginPage());
    }

    private void registerUser() {

        String fullName = String.valueOf(etFullName.getText()).trim();
        String email = String.valueOf(etEmail.getText()).trim();
        String phone = String.valueOf(etPhone.getText()).trim();
        String password = String.valueOf(etPassword.getText()).trim();
        String confirmPassword = String.valueOf(etConfirmPassword.getText()).trim();

        // Clear previous errors
        etFullName.setError(null);
        etEmail.setError(null);
        etPhone.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);

        // Full name validation
        if (fullName.isEmpty()) {
            etFullName.setError("Enter full name");
            etFullName.requestFocus();
            return;
        }

        if (fullName.length() < 3) {
            etFullName.setError("Name must contain at least 3 characters");
            etFullName.requestFocus();
            return;
        }

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

        // Phone validation
        if (phone.isEmpty()) {
            etPhone.setError("Enter phone number");
            etPhone.requestFocus();
            return;
        }

        if (!phone.matches("0[0-9]{9}")) {
            etPhone.setError("Enter a valid 10 digit phone number");
            etPhone.requestFocus();
            return;
        }

        // Password validation
        if (password.isEmpty()) {
            etPassword.setError("Enter password");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must contain at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        // Confirm password validation
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Confirm your password");
            etConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        setLoading(true);

        // Create Firebase Authentication account
        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {

                    if (!task.isSuccessful()) {
                        setLoading(false);

                        String errorMessage = "Registration failed";

                        if (task.getException() != null
                                && task.getException().getMessage() != null) {
                            errorMessage = task.getException().getMessage();
                        }

                        Toast.makeText(
                                RegisterActivity.this,
                                errorMessage,
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    FirebaseUser firebaseUser =
                            firebaseAuth.getCurrentUser();

                    if (firebaseUser == null) {
                        setLoading(false);

                        Toast.makeText(
                                RegisterActivity.this,
                                "Unable to create user account",
                                Toast.LENGTH_SHORT
                        ).show();

                        return;
                    }

                    String userId = firebaseUser.getUid();

                    User user = new User(
                            userId,
                            fullName,
                            email,
                            phone,
                            System.currentTimeMillis()
                    );

                    // Save user profile in Firestore
                    firestore
                            .collection("users")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener(unused -> {

                                // Firebase automatically logs in after registration.
                                // Sign out because user must use the Login page.
                                firebaseAuth.signOut();

                                setLoading(false);

                                Toast.makeText(
                                        RegisterActivity.this,
                                        "Registration successful",
                                        Toast.LENGTH_SHORT
                                ).show();

                                openLoginPage();
                            })
                            .addOnFailureListener(exception -> {

                                // Remove Authentication account if
                                // Firestore profile creation fails.
                                firebaseUser.delete()
                                        .addOnCompleteListener(deleteTask -> {

                                            setLoading(false);

                                            Toast.makeText(
                                                    RegisterActivity.this,
                                                    "Unable to save user details: "
                                                            + exception.getMessage(),
                                                    Toast.LENGTH_LONG
                                            ).show();
                                        });
                            });
                });
    }

    private void setLoading(boolean isLoading) {

        if (isLoading) {
            progressRegister.setVisibility(View.VISIBLE);
            btnRegister.setEnabled(false);
            btnRegister.setText("Creating Account...");
        } else {
            progressRegister.setVisibility(View.GONE);
            btnRegister.setEnabled(true);
            btnRegister.setText("Register");
        }
    }

    private void openLoginPage() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}