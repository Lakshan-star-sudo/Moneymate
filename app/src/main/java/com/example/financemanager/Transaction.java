package com.example.financemanager;

public class Transaction {

    private int id;
    private String amount;
    private String type;
    private String category;
    private String date;
    private String description;

    public Transaction(int id, String amount, String type,
                       String category, String date, String description) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}