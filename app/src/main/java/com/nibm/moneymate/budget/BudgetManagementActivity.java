package com.nibm.moneymate.budget;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nibm.moneymate.R;

public class BudgetManagementActivity extends AppCompatActivity {

    RecyclerView recyclerBudget;
    FloatingActionButton fabAddBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_management);

        recyclerBudget = findViewById(R.id.recyclerBudget);
        fabAddBudget = findViewById(R.id.fabAddBudget);

        fabAddBudget.setOnClickListener(v -> {

            Intent intent = new Intent(
                    BudgetManagementActivity.this,
                    AddBudgetActivity.class);

            startActivity(intent);

        });
    }
}