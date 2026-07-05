package com.nibm.moneymate.model;

public class BankTransaction {
    private int id;
    private int accountId;
    private String type; // "DEPOSIT" or "WITHDRAW"
    private double amount;
    private String date;
    private String note;
    private String slipImagePath;

    public BankTransaction(int id, int accountId, String type, double amount,
                           String date, String note, String slipImagePath) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.slipImagePath = slipImagePath;
    }

    public int getId() { return id; }
    public int getAccountId() { return accountId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getNote() { return note; }
    public String getSlipImagePath() { return slipImagePath; }
}