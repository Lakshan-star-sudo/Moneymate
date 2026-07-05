package com.nibm.moneymate.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.moneymate.R;
import com.nibm.moneymate.adapter.BankTransactionAdapter;
import com.nibm.moneymate.database.BankDatabaseHelper;
import com.nibm.moneymate.model.BankAccount;
import com.nibm.moneymate.model.BankTransaction;
import com.nibm.moneymate.utils.PdfReportGenerator;

import java.util.List;

public class BankAccountDetailActivity extends AppCompatActivity
        implements BankTransactionAdapter.OnTransactionActionListener {

    TextView tvDetailBankName, tvDetailAccountNumber, tvDetailHolderName, tvDetailBalance;
    RecyclerView rvTransactions;
    Button btnAddTransaction, btnGenerateReport;

    BankDatabaseHelper dbHelper;
    int accountId;
    BankAccount currentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account_detail);

        tvDetailBankName = findViewById(R.id.tvDetailBankName);
        tvDetailAccountNumber = findViewById(R.id.tvDetailAccountNumber);
        tvDetailHolderName = findViewById(R.id.tvDetailHolderName);
        tvDetailBalance = findViewById(R.id.tvDetailBalance);
        rvTransactions = findViewById(R.id.rvTransactions);
        btnAddTransaction = findViewById(R.id.btnAddTransaction);
        btnGenerateReport = findViewById(R.id.btnGenerateReport);

        dbHelper = new BankDatabaseHelper(this);
        accountId = getIntent().getIntExtra("account_id", -1);

        rvTransactions.setLayoutManager(new LinearLayoutManager(this));

        btnAddTransaction.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddBankTransactionActivity.class);
            intent.putExtra("account_id", accountId);
            startActivity(intent);
        });

        btnGenerateReport.setOnClickListener(v -> generateReport());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAccountDetails();
    }

    private void loadAccountDetails() {
        List<BankAccount> accounts = dbHelper.getAllAccounts();
        for (BankAccount acc : accounts) {
            if (acc.getId() == accountId) {
                currentAccount = acc;
                break;
            }
        }

        if (currentAccount != null) {
            tvDetailBankName.setText(currentAccount.getBankName());
            tvDetailAccountNumber.setText("Acc No: " + currentAccount.getAccountNumber());
            tvDetailHolderName.setText("Holder: " + currentAccount.getHolderName());
            tvDetailBalance.setText("Balance: Rs. " + String.format("%.2f", currentAccount.getBalance()));
        }

        List<BankTransaction> transactions = dbHelper.getTransactionsForAccount(accountId);
        rvTransactions.setAdapter(new BankTransactionAdapter(transactions, this));
    }

    private void generateReport() {
        if (currentAccount == null) return;
        List<BankTransaction> transactions = dbHelper.getTransactionsForAccount(accountId);
        PdfReportGenerator.generateStatement(this, currentAccount, transactions);
    }

    // ---------- EDIT TRANSACTION ----------
    @Override
    public void onEditClicked(BankTransaction transaction) {
        // simple edit dialog - amount and note witharai edit karanna denne
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 10);

        EditText etAmount = new EditText(this);
        etAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etAmount.setHint("Amount");
        etAmount.setText(String.valueOf(transaction.getAmount()));
        layout.addView(etAmount);

        EditText etNote = new EditText(this);
        etNote.setHint("Note");
        etNote.setText(transaction.getNote());
        layout.addView(etNote);

        new AlertDialog.Builder(this)
                .setTitle("Edit Transaction")
                .setView(layout)
                .setPositiveButton("Save", (dialog, which) -> {
                    String amountStr = etAmount.getText().toString().trim();
                    if (amountStr.isEmpty()) {
                        Toast.makeText(this, "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double newAmount = Double.parseDouble(amountStr);
                    String newNote = etNote.getText().toString().trim();

                    dbHelper.updateTransaction(transaction.getId(), transaction.getType(), newAmount, newNote);
                    recalculateAndSaveBalance();
                    Toast.makeText(this, "Transaction updated", Toast.LENGTH_SHORT).show();
                    loadAccountDetails();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ---------- DELETE TRANSACTION ----------
    @Override
    public void onDeleteClicked(BankTransaction transaction) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Transaction")
                .setMessage("Are you sure you want to delete this transaction?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    dbHelper.deleteTransaction(transaction.getId());
                    recalculateAndSaveBalance();
                    Toast.makeText(this, "Transaction deleted", Toast.LENGTH_SHORT).show();
                    loadAccountDetails();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // transaction ekak edit/delete karapu sema paraama, balance eka recalculate karanna
    private void recalculateAndSaveBalance() {
        List<BankTransaction> transactions = dbHelper.getTransactionsForAccount(accountId);
        double balance = 0;
        for (BankTransaction t : transactions) {
            if (t.getType().equals("DEPOSIT")) balance += t.getAmount();
            else balance -= t.getAmount();
        }
        dbHelper.updateBalance(accountId, balance);
    }
}