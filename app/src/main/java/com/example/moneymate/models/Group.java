package com.example.moneymate.models;

public class Group {

    private String name;
    private String members;
    private String balance;

    public Group(String name, String members, String balance) {
        this.name = name;
        this.members = members;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getMembers() {
        return members;
    }

    public String getBalance() {
        return balance;
    }
}