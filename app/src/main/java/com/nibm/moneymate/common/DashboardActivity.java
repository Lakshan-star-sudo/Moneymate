package com.nibm.moneymate.common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.nibm.moneymate.R;
import com.nibm.moneymate.budget.BudgetActivity;
import com.nibm.moneymate.saving.SavingActivity;
import com.nibm.moneymate.warrantycard.WarrantyActivity;

public class DashboardActivity extends AppCompatActivity {

    CardView cardIncome, cardExpense,cardGroupExpense, cardBudget, cardSavings, cardReports, cardProfile ,cardWarranty;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();

        cardIncome = findViewById(R.id.cardIncome);
        cardExpense = findViewById(R.id.cardExpense);
        cardGroupExpense = findViewById(R.id.groupexpenses);
        cardBudget = findViewById(R.id.cardBudget);
        cardSavings = findViewById(R.id.cardSavings);
        cardReports = findViewById(R.id.cardReports);
        cardProfile = findViewById(R.id.cardProfile);
        cardWarranty = findViewById(R.id.cardWarranty);

        btnLogout = findViewById(R.id.btnLogout);

        // Income
        cardIncome.setOnClickListener(v -> {
            Toast.makeText(this, "Income Module", Toast.LENGTH_SHORT).show();

        });

        // Expense
        cardExpense.setOnClickListener(v -> {
            Toast.makeText(this, "Expense Module", Toast.LENGTH_SHORT).show();

        });

        // Group Expenses
        cardGroupExpense.setOnClickListener(v -> {
            Toast.makeText(this, "Group Expenses", Toast.LENGTH_SHORT).show();

        });

        //Budget
        cardBudget.setOnClickListener(
                v -> {
                    Toast.makeText(this, "Budget", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DashboardActivity.this, BudgetActivity.class);
                    startActivity(intent);
                });

        // Savings
        cardSavings.setOnClickListener(v -> {
            Toast.makeText(this, "Savings Module", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DashboardActivity.this, SavingActivity.class);
            startActivity(intent);
        });

        // Reports
        cardReports.setOnClickListener(v -> {
            Toast.makeText(this, "Reports Module", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DashboardActivity.this, ReportActivity.class);
            startActivity(intent);

        });
        // Warrant Card
        cardWarranty.setOnClickListener(v -> {
            Toast.makeText(this, "WarrantyCard Module", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DashboardActivity.this, WarrantyActivity.class);
            startActivity(intent);
        });

        // Profile
        cardProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Profile Module", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Logout
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        btnLogout.setOnClickListener(view -> {

            // Firebase user sign out
            FirebaseAuth.getInstance().signOut();

            // Open Login page
            Intent intent = new Intent(
                    DashboardActivity.this,
                    LoginActivity.class
            );

            // Remove Dashboard and previous pages
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();
        });


    }
}



