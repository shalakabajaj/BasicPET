package com.example.expensetracker;

public class ExpenseModel {

    private int id;
    private String title;
    private double amount;
    private String date;
    private String category;
    private String note;

    private String reason;
    private String buy;

    // Constructor for detailed expenses
    public ExpenseModel(String title, double amount, String date, String category, String note) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.note = note;
    }

    // Constructor for RecyclerViewActivity (with id, buy, reason)
    public ExpenseModel(int id, String buy, String reason) {
        this.id = id;
        this.buy = buy;
        this.reason = reason;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getNote() {
        return note;
    }

    public String getReason() {
        return reason;
    }

    public String getBuy() {
        return buy;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }
}
