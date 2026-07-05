package com.nibm.moneymate.saving;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nibm.moneymate.R;



public class SavingActivity extends AppCompatActivity {

    FloatingActionButton fabAddSavings;

    Button btnEditGoal1, btnEditGoal2, btnDeleteGoal1, btnDeleteGoal2, btnProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);
        getSupportActionBar().hide();

        fabAddSavings = findViewById(R.id.fabAddSavings);

        btnEditGoal1 = findViewById(R.id.btnEditGoal1);
        btnEditGoal2 = findViewById(R.id.btnEditGoal2);

        btnDeleteGoal1 = findViewById(R.id.btnDeleteGoal1);
        btnDeleteGoal2 = findViewById(R.id.btnDeleteGoal2);

        btnProgress = findViewById(R.id.btnProgress);

        fabAddSavings.setOnClickListener(
                v -> {
                    Intent intent = new Intent(SavingActivity.this, AddSavingActivity.class);
                    startActivity(intent);
                });

        btnEditGoal1.setOnClickListener(v -> {
            Intent intent = new Intent(SavingActivity.this, EditSavingActivity.class);
            startActivity(intent);
        });

        btnEditGoal2.setOnClickListener(v -> {
            Intent intent = new Intent(SavingActivity.this, EditSavingActivity.class);
            startActivity(intent);
        });

        btnDeleteGoal1.setOnClickListener(v ->
                Toast.makeText(this, "Savings Goal Deleted", Toast.LENGTH_SHORT).show());

        btnDeleteGoal2.setOnClickListener(v ->
                Toast.makeText(this, "Savings Goal Deleted", Toast.LENGTH_SHORT).show());


        btnProgress.setOnClickListener(
                v -> {
                    Intent intent = new Intent(SavingActivity.this, ProgressActivity.class);
                    startActivity(intent);
                });
    }
}


