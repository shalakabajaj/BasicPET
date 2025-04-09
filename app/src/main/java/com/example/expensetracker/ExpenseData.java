package com.example.expensetracker;

import java.util.ArrayList;
import java.util.List;

public class ExpenseData {
    public static List<ExpenseModel> expenseList = new ArrayList<>();

    static {
        // Dummy data for testing
        expenseList.add(new ExpenseModel("Groceries", 1500, "2025-04-02", "Food", "Supermarket shopping"));
        expenseList.add(new ExpenseModel("Rent", 8000, "2025-04-01", "Housing", "Monthly rent"));
        expenseList.add(new ExpenseModel("Internet", 1000, "2025-03-30", "Utilities", "WiFi bill"));
        expenseList.add(new ExpenseModel("Movie", 400, "2025-04-03", "Entertainment", "Cinema with friends"));
        expenseList.add(new ExpenseModel("Transport", 600, "2025-04-06", "Travel", "Bus and auto fare"));
    }
}
