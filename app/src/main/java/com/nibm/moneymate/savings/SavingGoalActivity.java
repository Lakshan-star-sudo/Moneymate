package com.nibm.moneymate.savings;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nibm.moneymate.R;

public class SavingGoalActivity extends AppCompatActivity {

    RecyclerView recyclerSavings;
    FloatingActionButton fabAddGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_goal);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        recyclerSavings = findViewById(R.id.recyclerSavings);
        fabAddGoal = findViewById(R.id.fabAddGoal);

        recyclerSavings.setLayoutManager(new LinearLayoutManager(this));

        fabAddGoal.setOnClickListener(v -> {
            startActivity(new Intent(this, AddSavingGoalActivity.class));
        });
    }
}