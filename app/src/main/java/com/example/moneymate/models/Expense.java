package com.example.moneymate.models;

public class Expense {

    private String name;
    private String amount;
    private String paidBy;
    private String type;
    private String date;
    private String notes;

    public Expense(String name, String amount, String paidBy,
                   String type, String date, String notes) {

        this.name = name;
        this.amount = amount;
        this.paidBy = paidBy;
        this.type = type;
        this.date = date;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }
}