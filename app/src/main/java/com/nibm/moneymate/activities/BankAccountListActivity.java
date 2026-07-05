package com.nibm.moneymate.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.moneymate.R;
import com.nibm.moneymate.adapter.BankAccountAdapter;
import com.nibm.moneymate.database.BankDatabaseHelper;
import com.nibm.moneymate.model.BankAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BankAccountListActivity extends AppCompatActivity
        implements BankAccountAdapter.OnAccountDeleteListener {

    RecyclerView rvAccounts;
    EditText etSearchAccount;
    TextView tvEmptyState;
    BankDatabaseHelper dbHelper;
    BankAccountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account_list);

        rvAccounts = findViewById(R.id.rvAccounts);
        etSearchAccount = findViewById(R.id.etSearchAccount);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        FloatingActionButton fab = findViewById(R.id.fabAddAccount);
        dbHelper = new BankDatabaseHelper(this);

        rvAccounts.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(v ->
                startActivity(new Intent(this, AddBankAccountActivity.class)));

        // search box eke text wenas wena sema paraama filter karanawa
        etSearchAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAccounts();
    }

    private void loadAccounts() {
        List<BankAccount> accounts = dbHelper.getAllAccounts();

        adapter = new BankAccountAdapter(accounts, this, count -> {
            // filter result eka empty nam "No accounts found" pennanawa
            tvEmptyState.setVisibility(count == 0 ? android.view.View.VISIBLE : android.view.View.GONE);
            rvAccounts.setVisibility(count == 0 ? android.view.View.GONE : android.view.View.VISIBLE);
        });

        rvAccounts.setAdapter(adapter);

        // account thiyenne nathnam empty state eka mulinma pennanawa
        tvEmptyState.setVisibility(accounts.isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);

        // screen eka return unama, search text eka clear karala fresh list eka pennanawa
        etSearchAccount.setText("");
    }

    @Override
    public void onDeleteClicked(BankAccount account) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete this account?\nAll its transactions will also be deleted.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    dbHelper.deleteAccount(account.getId());
                    Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show();
                    loadAccounts();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}