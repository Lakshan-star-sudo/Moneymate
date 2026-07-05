package com.example.moneymate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymate.adapters.ExpenseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GroupDetailsActivity extends AppCompatActivity {

    Button btnBalance;
    Button btnActivity;
    Button btnMembers;

    RecyclerView recyclerExpenses;
    ExpenseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        FloatingActionButton fab = findViewById(R.id.fabAddExpense);

        btnBalance = findViewById(R.id.btnBalance);
        btnActivity = findViewById(R.id.btnActivity);
        btnMembers = findViewById(R.id.btnMembers);

        recyclerExpenses = findViewById(R.id.recyclerExpenses);
        recyclerExpenses.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ExpenseAdapter(ExpenseStorage.expenses);
        recyclerExpenses.setAdapter(adapter);

        btnMembers.setOnClickListener(v ->
                startActivity(new Intent(GroupDetailsActivity.this,
                        ManageMembersActivity.class)));

        fab.setOnClickListener(v ->
                startActivity(new Intent(GroupDetailsActivity.this,
                        AddExpenseActivity.class)));

        btnBalance.setOnClickListener(v ->
                startActivity(new Intent(GroupDetailsActivity.this,
                        BalanceActivitys.class)));

        btnActivity.setOnClickListener(v ->
                startActivity(new Intent(GroupDetailsActivity.this,
                        ActivityHistoryActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
    }
}