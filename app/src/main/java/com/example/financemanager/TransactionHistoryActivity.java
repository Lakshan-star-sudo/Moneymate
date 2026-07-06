package com.example.financemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TransactionHistoryActivity extends AppCompatActivity {

    ListView listTransactions;
    DatabaseHelper databaseHelper;

    ArrayList<String> transactionList;
    ArrayList<Integer> transactionIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        listTransactions = findViewById(R.id.listTransactions);

        databaseHelper = new DatabaseHelper(this);

        transactionList = new ArrayList<>();
        transactionIds = new ArrayList<>();

        loadTransactions();

        listTransactions.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent = new Intent(TransactionHistoryActivity.this,
                    EditTransactionActivity.class);

            intent.putExtra("transactionId", transactionIds.get(position));

            startActivity(intent);

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadTransactions();
    }

    private void loadTransactions() {

        transactionList.clear();
        transactionIds.clear();

        Cursor cursor = databaseHelper.getAllTransactions();

        while (cursor.moveToNext()) {

            transactionIds.add(cursor.getInt(0));

            String item =
                    "ID : " + cursor.getInt(0) + "\n" +
                            "Amount : Rs." + cursor.getString(1) + "\n" +
                            "Type : " + cursor.getString(2) + "\n" +
                            "Category : " + cursor.getString(3) + "\n" +
                            "Date : " + cursor.getString(4) + "\n" +
                            "Description : " + cursor.getString(5);

            transactionList.add(item);

        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                transactionList);

        listTransactions.setAdapter(adapter);

    }

}