package com.example.expensetracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etExpenseTitle, etExpenseAmount, etExpenseDate, etExpenseCategory, etExpenseNote;
    private Button btnSaveExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etExpenseTitle = findViewById(R.id.etExpenseTitle);
        etExpenseAmount = findViewById(R.id.etExpenseAmount);
        etExpenseDate = findViewById(R.id.etExpenseDate);
        etExpenseCategory = findViewById(R.id.etExpenseCategory);
        etExpenseNote = findViewById(R.id.etExpenseNote);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        btnSaveExpense.setOnClickListener(v -> {
            String title = etExpenseTitle.getText().toString().trim();
            String amountStr = etExpenseAmount.getText().toString().trim();
            String date = etExpenseDate.getText().toString().trim();
            String category = etExpenseCategory.getText().toString().trim();
            String note = etExpenseNote.getText().toString().trim();

            if (title.isEmpty() || amountStr.isEmpty() || date.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);

            ExpenseModel expense = new ExpenseModel(title, amount, date, category, note);
            ExpenseData.expenseList.add(expense);

            Toast.makeText(this, "Expense added successfully", Toast.LENGTH_SHORT).show();
            finish(); // close the activity
        });
    }
}
