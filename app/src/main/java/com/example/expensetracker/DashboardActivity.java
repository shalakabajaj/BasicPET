package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

public class DashboardActivity extends AppCompatActivity {

    private TextView txtBalance, txtIncome, txtExpenses;
    private PieChart pieChart;
    private BarChart barChart;
    private RecyclerView recyclerTransactions;
    private Button btnLogout, btnCheckBalance, btnAddExpense, btnAddBankAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize UI components
        txtBalance = findViewById(R.id.txtBalance);
        txtIncome = findViewById(R.id.txtIncome);
        txtExpenses = findViewById(R.id.txtExpenses);
        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);
        recyclerTransactions = findViewById(R.id.recyclerTransactions);
        btnLogout = findViewById(R.id.btnLogout);
        btnCheckBalance = findViewById(R.id.btnCheckBalance);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnAddBankAccount = findViewById(R.id.btnAddBankAccount);

        // Set up RecyclerView
        recyclerTransactions.setLayoutManager(new LinearLayoutManager(this));
        // recyclerTransactions.setAdapter(new TransactionsAdapter(transactionList)); // Set adapter later

        // Logout button functionality
        btnLogout.setOnClickListener(view -> {
            // Implement logout logic (clear session, go to login page, etc.)
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

//        // Add Expense button functionality
//        btnAddExpense.setOnClickListener(view -> {
//            Intent intent = new Intent(DashboardActivity.this, AddExpenseActivity.class);
//            startActivity(intent);
//        });
//
//        // Add Bank Account button functionality
//        btnAddBankAccount.setOnClickListener(view -> {
//            Intent intent = new Intent(DashboardActivity.this, AddBankAccountActivity.class);
//            startActivity(intent);
//        });
    }
}
