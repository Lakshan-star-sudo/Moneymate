package com.example.financemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditTransactionActivity extends AppCompatActivity {

    EditText etAmount, etCategory, etDate, etDescription;
    Spinner spType;
    Button btnUpdate, btnDelete;

    DatabaseHelper databaseHelper;

    int transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        databaseHelper = new DatabaseHelper(this);

        etAmount = findViewById(R.id.etAmount);
        etCategory = findViewById(R.id.etCategory);
        etDate = findViewById(R.id.etDate);
        etDescription = findViewById(R.id.etDescription);

        spType = findViewById(R.id.spType);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.transaction_type,
                        android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spType.setAdapter(adapter);

        Intent intent = getIntent();
        transactionId = intent.getIntExtra("transactionId", -1);

        if (transactionId == -1) {
            Toast.makeText(this, "Invalid Transaction", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Cursor cursor = databaseHelper.getTransactionById(transactionId);

        if (cursor.moveToFirst()) {

            etAmount.setText(cursor.getString(1));

            String type = cursor.getString(2);

            if(type.equals("Income")){
                spType.setSelection(0);
            }else{
                spType.setSelection(1);
            }

            etCategory.setText(cursor.getString(3));
            etDate.setText(cursor.getString(4));
            etDescription.setText(cursor.getString(5));
        }

        cursor.close();

        btnUpdate.setOnClickListener(v -> {

            String amount = etAmount.getText().toString().trim();
            String type = spType.getSelectedItem().toString();
            String category = etCategory.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            boolean result = databaseHelper.updateTransaction(
                    transactionId,
                    amount,
                    type,
                    category,
                    date,
                    description);

            if(result){

                Toast.makeText(this,
                        "Transaction Updated",
                        Toast.LENGTH_SHORT).show();

                finish();

            }else{

                Toast.makeText(this,
                        "Update Failed",
                        Toast.LENGTH_SHORT).show();
            }

        });

        btnDelete.setOnClickListener(v -> {

            boolean result =
                    databaseHelper.deleteTransaction(transactionId);

            if(result){

                Toast.makeText(this,
                        "Transaction Deleted",
                        Toast.LENGTH_SHORT).show();

                finish();

            }else{

                Toast.makeText(this,
                        "Delete Failed",
                        Toast.LENGTH_SHORT).show();
            }

        });

    }
}