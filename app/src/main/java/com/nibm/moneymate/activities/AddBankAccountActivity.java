package com.nibm.moneymate.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nibm.moneymate.R;
import com.nibm.moneymate.database.BankDatabaseHelper;
import com.nibm.moneymate.model.BankAccount;

public class AddBankAccountActivity extends AppCompatActivity {

    EditText etBankName, etAccountNumber, etHolderName, etBranch, etBalance;
    Button btnSave;
    BankDatabaseHelper dbHelper;

    boolean isEditMode = false;
    int editAccountId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_account);

        etBankName = findViewById(R.id.etBankName);
        etAccountNumber = findViewById(R.id.etAccountNumber);
        etHolderName = findViewById(R.id.etHolderName);
        etBranch = findViewById(R.id.etBranch);
        etBalance = findViewById(R.id.etBalance);
        btnSave = findViewById(R.id.btnSaveAccount);

        dbHelper = new BankDatabaseHelper(this);

        // check karanne - meka edit mode ekakda kiyala (list screen eken account_id ekak awoth)
        editAccountId = getIntent().getIntExtra("account_id", -1);
        isEditMode = editAccountId != -1;

        if (isEditMode) {
            loadExistingAccountData();
            btnSave.setText("Update Account");

            // edit karaddi account number wenas karanna denne na (bank rules walata anuwa)
            etAccountNumber.setEnabled(false);
        }

        btnSave.setOnClickListener(v -> saveAccount());
    }

    private void loadExistingAccountData() {
        BankAccount acc = dbHelper.getAccountById(editAccountId);
        if (acc != null) {
            etBankName.setText(acc.getBankName());
            etAccountNumber.setText(acc.getAccountNumber());
            etHolderName.setText(acc.getHolderName());
            etBranch.setText(acc.getBranch());
            etBalance.setText(String.valueOf(acc.getBalance()));

            // balance eka edit karanna denne na - transactions walin witharai balance eka wenas wenne
            etBalance.setEnabled(false);
        }
    }

    private void saveAccount() {
        String bank = etBankName.getText().toString().trim();
        String accNo = etAccountNumber.getText().toString().trim();
        String holder = etHolderName.getText().toString().trim();
        String branch = etBranch.getText().toString().trim();
        String balStr = etBalance.getText().toString().trim();

        if (bank.isEmpty() || accNo.isEmpty() || holder.isEmpty() || balStr.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // duplicate account number check
        // edit mode nam, own account eka ignore karala check karanawa
        int excludeId = isEditMode ? editAccountId : -1;
        if (dbHelper.isAccountNumberExists(accNo, excludeId)) {
            Toast.makeText(this, "This account number already exists!", Toast.LENGTH_LONG).show();
            return;
        }

        if (isEditMode) {
            dbHelper.updateAccount(editAccountId, bank, accNo, holder, branch);
            Toast.makeText(this, "Account updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            double balance = Double.parseDouble(balStr);
            long result = dbHelper.addAccount(bank, accNo, holder, branch, balance);

            if (result != -1) {
                Toast.makeText(this, "Account added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add account", Toast.LENGTH_SHORT).show();
            }
        }
    }
}