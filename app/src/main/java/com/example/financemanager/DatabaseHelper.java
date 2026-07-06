package com.example.financemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FinanceDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "transactions";

    public static final String COL_ID = "id";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_TYPE = "type";
    public static final String COL_CATEGORY = "category";
    public static final String COL_DATE = "date";
    public static final String COL_DESCRIPTION = "description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getAllTransactions() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC", null);
    }
    

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_AMOUNT + " TEXT," +
                COL_TYPE + " TEXT," +
                COL_CATEGORY + " TEXT," +
                COL_DATE + " TEXT," +
                COL_DESCRIPTION + " TEXT)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertTransaction(String amount,
                                     String type,
                                     String category,
                                     String date,
                                     String description) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_AMOUNT, amount);
        values.put(COL_TYPE, type);
        values.put(COL_CATEGORY, category);
        values.put(COL_DATE, date);
        values.put(COL_DESCRIPTION, description);

        long result = db.insert(TABLE_NAME, null, values);

        return result != -1;
    }
    public boolean updateTransaction(int id,
                                     String amount,
                                     String type,
                                     String category,
                                     String date,
                                     String description) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_AMOUNT, amount);
        values.put(COL_TYPE, type);
        values.put(COL_CATEGORY, category);
        values.put(COL_DATE, date);
        values.put(COL_DESCRIPTION, description);

        int result = db.update(TABLE_NAME,
                values,
                COL_ID + "=?",
                new String[]{String.valueOf(id)});

        return result > 0;
    }
    public boolean deleteTransaction(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_NAME,
                COL_ID + "=?",
                new String[]{String.valueOf(id)});

        return result > 0;
    }
    public Cursor getTransactionById(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE id=?",
                new String[]{String.valueOf(id)});
    }

    public Cursor getSummary(){

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT " +
                        "SUM(CASE WHEN type='Income' THEN amount ELSE 0 END)," +
                        "SUM(CASE WHEN type='Expense' THEN amount ELSE 0 END)" +
                        " FROM transactions",
                null);

    }
}