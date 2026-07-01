package com.nibm.moneymate.budget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nibm.moneymate.R;

public class BudgetManagementActivity extends AppCompatActivity {

    RecyclerView recyclerBudget;
    FloatingActionButton fabAddBudget;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_management);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnBack = findViewById(R.id.btnBack);
        recyclerBudget = findViewById(R.id.recyclerBudget);
        fabAddBudget = findViewById(R.id.fabAddBudget);

        recyclerBudget.setLayoutManager(new LinearLayoutManager(this));
        
        btnBack.setOnClickListener(v -> finish());

        fabAddBudget.setOnClickListener(v -> {
            startActivity(new Intent(this, AddBudgetActivity.class));
        });
    }
}