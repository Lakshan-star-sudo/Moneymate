package com.nibm.moneymate.budget;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.nibm.moneymate.R;

public class AddBudgetActivity extends AppCompatActivity {

    EditText etBudgetName, etBudgetAmount;
    Button btnSaveBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        etBudgetName = findViewById(R.id.etBudgetName);
        etBudgetAmount = findViewById(R.id.etBudgetAmount);
        btnSaveBudget = findViewById(R.id.btnSaveBudget);

        btnSaveBudget.setOnClickListener(v -> {
            String name = etBudgetName.getText().toString();
            String amount = etBudgetAmount.getText().toString();

            if (!name.isEmpty() && !amount.isEmpty()) {
                // මෙහිදී දත්ත සමුදායට (Database) දත්ත ඇතුළත් කිරීමේ කේතය පසුව එකතු කළ හැක.
                Toast.makeText(this, "Budget Saved Successfully!", Toast.LENGTH_SHORT).show();
                finish(); // පෙර තිරයට යාමට
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}