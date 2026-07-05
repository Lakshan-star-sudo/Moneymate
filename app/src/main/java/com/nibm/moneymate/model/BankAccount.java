package com.nibm.moneymate.model;

public class BankAccount {
    private int id;
    private String bankName;
    private String accountNumber;
    private String holderName;
    private String branch;
    private double balance;

    public BankAccount(int id, String bankName, String accountNumber,
                       String holderName, String branch, double balance) {
        this.id = id;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.branch = branch;
        this.balance = balance;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getBankName() { return bankName; }
    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public String getBranch() { return branch; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}