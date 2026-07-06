package com.example.financemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtBalance, txtIncome, txtExpense;

    Button btnAdd, btnHistory;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBalance = findViewById(R.id.txtBalance);
        txtIncome = findViewById(R.id.txtIncome);
        txtExpense = findViewById(R.id.txtExpense);

        btnAdd = findViewById(R.id.btnAdd);
        btnHistory = findViewById(R.id.btnHistory);

        databaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
            startActivity(intent);
        });

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TransactionHistoryActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Cursor cursor = databaseHelper.getSummary();

        if (cursor.moveToFirst()) {

            double income = cursor.isNull(0) ? 0 : cursor.getDouble(0);
            double expense = cursor.isNull(1) ? 0 : cursor.getDouble(1);

            double balance = income - expense;

            txtIncome.setText("Income : Rs. " + income);
            txtExpense.setText("Expense : Rs. " + expense);
            txtBalance.setText("Balance : Rs. " + balance);
        }

        cursor.close();
    }
}