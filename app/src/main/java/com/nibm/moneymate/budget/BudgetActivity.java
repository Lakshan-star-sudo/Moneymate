package com.nibm.moneymate.budget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nibm.moneymate.R;

public class BudgetActivity extends AppCompatActivity {

    FloatingActionButton fabAddBudget;

    Button btnEdit1, btnEdit2,btnDelete1 ,btnDelete2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        getSupportActionBar().hide();

        fabAddBudget = findViewById(R.id.fabAddBudget);

         btnEdit1 = findViewById(R.id.btnEdit1);
         btnEdit2 = findViewById(R.id.btnEdit2);

         btnDelete1 = findViewById(R.id.btnDelete1);
         btnDelete2 = findViewById(R.id.btnDelete2);

        fabAddBudget.setOnClickListener(
                v -> {
                    Intent intent = new Intent(BudgetActivity.this, AddBudgetActivity.class);
                    startActivity(intent);
                });

        btnEdit1.setOnClickListener(
                v -> {
                    Intent intent = new Intent(BudgetActivity.this, EditBudgetActivity.class);
                    startActivity(intent);
        });


        btnEdit2.setOnClickListener(
                v -> {
                    Intent intent = new Intent(BudgetActivity.this, EditBudgetActivity.class);
                    startActivity(intent);
        });

        btnDelete1.setOnClickListener(v ->
                Toast.makeText(this,"Budget Deleted",Toast.LENGTH_SHORT).show());

        btnDelete2.setOnClickListener(v ->
                Toast.makeText(this,"Budget Deleted",Toast.LENGTH_SHORT).show());
    }
}





