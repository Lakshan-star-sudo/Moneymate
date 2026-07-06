package com.example.financemanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTransactionActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    EditText etAmount, etCategory, etDate, etDescription;
    Spinner spType;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        databaseHelper = new DatabaseHelper(this);

        etAmount = findViewById(R.id.etAmount);
        etCategory = findViewById(R.id.etCategory);
        etDate = findViewById(R.id.etDate);
        etDescription = findViewById(R.id.etDescription);

        spType = findViewById(R.id.spType);
        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.transaction_type,
                        android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spType.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {

            String amount = etAmount.getText().toString().trim();
            String type = spType.getSelectedItem().toString();
            String category = etCategory.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (amount.isEmpty() || category.isEmpty() || date.isEmpty()) {

                Toast.makeText(this,
                        "Please fill all required fields",
                        Toast.LENGTH_SHORT).show();

            } else {

                boolean inserted = databaseHelper.insertTransaction(
                        amount,
                        type,
                        category,
                        date,
                        description
                );

                if (inserted) {

                    Toast.makeText(this,
                            "Transaction Saved Successfully",
                            Toast.LENGTH_SHORT).show();

                    etAmount.setText("");
                    etCategory.setText("");
                    etDate.setText("");
                    etDescription.setText("");

                } else {

                    Toast.makeText(this,
                            "Error Saving Transaction",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}