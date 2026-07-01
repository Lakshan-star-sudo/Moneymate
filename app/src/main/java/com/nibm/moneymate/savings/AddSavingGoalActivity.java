package com.nibm.moneymate.savings;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.nibm.moneymate.R;

public class AddSavingGoalActivity extends AppCompatActivity {

    EditText etGoalName, etTargetAmount, etCurrentAmount;
    Button btnSaveGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saving_goal);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        etGoalName = findViewById(R.id.etGoalName);
        etTargetAmount = findViewById(R.id.etTargetAmount);
        etCurrentAmount = findViewById(R.id.etCurrentAmount);
        btnSaveGoal = findViewById(R.id.btnSaveGoal);

        btnSaveGoal.setOnClickListener(v -> {
            String name = etGoalName.getText().toString();
            if (!name.isEmpty()) {
                Toast.makeText(this, "Savings Goal Saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please enter a goal name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}