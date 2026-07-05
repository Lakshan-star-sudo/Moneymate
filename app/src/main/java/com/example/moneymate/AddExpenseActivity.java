package com.example.moneymate;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymate.models.Expense;

public class AddExpenseActivity extends AppCompatActivity {

    Spinner spinnerPaidBy;
    RadioGroup radioGroup;

    EditText edtExpenseName;
    EditText edtAmount;
    EditText edtDate;
    EditText edtNotes;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);

        spinnerPaidBy = findViewById(R.id.spinnerPaidBy);
        radioGroup = findViewById(R.id.radioExpenseType);

        edtExpenseName = findViewById(R.id.edtExpenseName);
        edtAmount = findViewById(R.id.edtAmount);
        edtDate = findViewById(R.id.edtDate);
        edtNotes = findViewById(R.id.edtNotes);

        btnSave = findViewById(R.id.btnSave);

        String[] members = {
                "You",
                "Kavi",
              "Nimal",
               "Kasun"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                members
        );

        spinnerPaidBy.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {

            String name = edtExpenseName.getText().toString().trim();
            String amount = edtAmount.getText().toString().trim();
            String date = edtDate.getText().toString().trim();
            String notes = edtNotes.getText().toString().trim();

            String paidBy = spinnerPaidBy.getSelectedItem().toString();

            String type;

            if (radioGroup.getCheckedRadioButtonId() == R.id.rbSplit) {
                type = "Split Equally";
            } else {
                type = "Paid Only By Payer";
            }

            if (name.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Expense expense = new Expense(
                    name,
                    amount,
                    paidBy,
                    type,
                    date,
                    notes
            );

            ExpenseStorage.expenses.add(expense);

            Toast.makeText(this,
                    "Expense Saved (" + ExpenseStorage.expenses.size() + ")",
                    Toast.LENGTH_LONG).show();

            finish();

        });

    }
}