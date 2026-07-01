package com.nibm.moneymate.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nibm.moneymate.R;
import com.nibm.moneymate.budget.BudgetManagementActivity;
import com.nibm.moneymate.savings.ProgressDashboardActivity;
import com.nibm.moneymate.savings.SavingGoalActivity;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.nav_home);

        bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                return true;
            }

            if (item.getItemId() == R.id.nav_budget) {

                startActivity(new Intent(
                        DashboardActivity.this,
                        BudgetManagementActivity.class));

                return true;
            }

            if (item.getItemId() == R.id.nav_savings) {

                startActivity(new Intent(DashboardActivity.this, SavingGoalActivity.class));

                return true;
            }

            if (item.getItemId() == R.id.nav_profile) {

                startActivity(new Intent(
                        DashboardActivity.this,
                        ProgressDashboardActivity.class));

                return true;
            }

            return false;
        });
    }
}