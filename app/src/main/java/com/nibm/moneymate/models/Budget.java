package com.nibm.moneymate.models;

public class Budget {

    private String name;
    private String amount;
    private String spent;
    private int percentage;

    public Budget(String name, String amount,
                  String spent, int percentage) {

        this.name = name;
        this.amount = amount;
        this.spent = spent;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getSpent() {
        return spent;
    }

    public int getPercentage() {
        return percentage;
    }
}