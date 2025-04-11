package com.example.expensetracker;

import java.io.Serializable;

public class ExpenseModel implements Serializable {
    private String title;
    private double amount;
    private String date;
    private String category;
    private String note;

    public ExpenseModel(String title, double amount, String date, String category, String note) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.note = note;
    }

    public String getTitle() { return title; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getCategory() { return category; }
    public String getNote() { return note; }

    public void setTitle(String title) { this.title = title; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDate(String date) { this.date = date; }
    public void setCategory(String category) { this.category = category; }
    public void setNote(String note) { this.note = note; }
}
