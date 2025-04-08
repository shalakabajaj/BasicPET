package com.example.expensetracker;
public class IncomeModel {
    private String title;
    private double amount;
    private String date;
    private String category;
    private String note;

    public IncomeModel(String title, double amount, String date, String category, String note) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }
}
