package com.nibm.moneymate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nibm.moneymate.model.BankAccount;
import com.nibm.moneymate.model.BankTransaction;

import java.util.ArrayList;
import java.util.List;

public class BankDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "moneymate.db";
    private static final int DB_VERSION = 1;

    public BankDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bank_accounts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "bank_name TEXT," +
                "account_number TEXT," +
                "holder_name TEXT," +
                "branch TEXT," +
                "balance REAL)");

        db.execSQL("CREATE TABLE bank_transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account_id INTEGER," +
                "type TEXT," +
                "amount REAL," +
                "date TEXT," +
                "note TEXT," +
                "slip_image_path TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS bank_accounts");
        db.execSQL("DROP TABLE IF EXISTS bank_transactions");
        onCreate(db);
    }

    // ---------- BANK ACCOUNT CRUD ----------

    public long addAccount(String bankName, String accNo, String holder, String branch, double balance) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("bank_name", bankName);
        cv.put("account_number", accNo);
        cv.put("holder_name", holder);
        cv.put("branch", branch);
        cv.put("balance", balance);
        return db.insert("bank_accounts", null, cv);
    }

    public List<BankAccount> getAllAccounts() {
        List<BankAccount> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM bank_accounts", null);
        while (c.moveToNext()) {
            list.add(new BankAccount(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getString(c.getColumnIndexOrThrow("bank_name")),
                    c.getString(c.getColumnIndexOrThrow("account_number")),
                    c.getString(c.getColumnIndexOrThrow("holder_name")),
                    c.getString(c.getColumnIndexOrThrow("branch")),
                    c.getDouble(c.getColumnIndexOrThrow("balance"))
            ));
        }
        c.close();
        return list;
    }

    // ---- NEW: get a single account by id ----
    public BankAccount getAccountById(int accountId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM bank_accounts WHERE id=?",
                new String[]{String.valueOf(accountId)});
        BankAccount account = null;
        if (c.moveToFirst()) {
            account = new BankAccount(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getString(c.getColumnIndexOrThrow("bank_name")),
                    c.getString(c.getColumnIndexOrThrow("account_number")),
                    c.getString(c.getColumnIndexOrThrow("holder_name")),
                    c.getString(c.getColumnIndexOrThrow("branch")),
                    c.getDouble(c.getColumnIndexOrThrow("balance"))
            );
        }
        c.close();
        return account;
    }

    // ---- NEW: check if account number already exists (duplicate check) ----
    // excludeId eka -1 dunnoth "add new account" check ekak
    // excludeId eka real id ekak dunnoth "edit account" check ekak (own number ignore karanawa)
    public boolean isAccountNumberExists(String accountNumber, int excludeId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT id FROM bank_accounts WHERE account_number=? AND id != ?",
                new String[]{accountNumber, String.valueOf(excludeId)});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    // ---- NEW: update full account details (edit screen eken call karanawa) ----
    public void updateAccount(int accountId, String bankName, String accNo,
                              String holder, String branch) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("bank_name", bankName);
        cv.put("account_number", accNo);
        cv.put("holder_name", holder);
        cv.put("branch", branch);
        db.update("bank_accounts", cv, "id=?", new String[]{String.valueOf(accountId)});
    }

    public void updateBalance(int accountId, double newBalance) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("balance", newBalance);
        db.update("bank_accounts", cv, "id=?", new String[]{String.valueOf(accountId)});
    }

    public void deleteAccount(int accountId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("bank_transactions", "account_id=?", new String[]{String.valueOf(accountId)});
        db.delete("bank_accounts", "id=?", new String[]{String.valueOf(accountId)});
    }

    // ---------- TRANSACTION CRUD ----------

    public long addTransaction(int accountId, String type, double amount,
                               String date, String note, String slipPath) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("account_id", accountId);
        cv.put("type", type);
        cv.put("amount", amount);
        cv.put("date", date);
        cv.put("note", note);
        cv.put("slip_image_path", slipPath);
        return db.insert("bank_transactions", null, cv);
    }

    public List<BankTransaction> getTransactionsForAccount(int accountId) {
        List<BankTransaction> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM bank_transactions WHERE account_id=? ORDER BY id DESC",
                new String[]{String.valueOf(accountId)});
        while (c.moveToNext()) {
            list.add(new BankTransaction(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getInt(c.getColumnIndexOrThrow("account_id")),
                    c.getString(c.getColumnIndexOrThrow("type")),
                    c.getDouble(c.getColumnIndexOrThrow("amount")),
                    c.getString(c.getColumnIndexOrThrow("date")),
                    c.getString(c.getColumnIndexOrThrow("note")),
                    c.getString(c.getColumnIndexOrThrow("slip_image_path"))
            ));
        }
        c.close();
        return list;
    }

    public void updateTransaction(int transactionId, String type, double amount, String note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("type", type);
        cv.put("amount", amount);
        cv.put("note", note);
        db.update("bank_transactions", cv, "id=?", new String[]{String.valueOf(transactionId)});
    }

    public void deleteTransaction(int transactionId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("bank_transactions", "id=?", new String[]{String.valueOf(transactionId)});
    }
}