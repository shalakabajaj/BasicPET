package com.example.expensetracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditExpenseActivity extends AppCompatActivity {

    private EditText titleInput, amountInput, dateInput, categoryInput, noteInput;
    private Button saveButton;
    private ExpenseModel expenseToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        titleInput = findViewById(R.id.titleInput);
        amountInput = findViewById(R.id.amountInput);
        dateInput = findViewById(R.id.dateInput);
        categoryInput = findViewById(R.id.categoryInput);
        noteInput = findViewById(R.id.noteInput);
        saveButton = findViewById(R.id.saveButton);

        expenseToEdit = (ExpenseModel) getIntent().getSerializableExtra("expense");

        if (expenseToEdit != null) {
            titleInput.setText(expenseToEdit.getTitle());
            amountInput.setText(String.valueOf(expenseToEdit.getAmount()));
            dateInput.setText(expenseToEdit.getDate());
            categoryInput.setText(expenseToEdit.getCategory());
            noteInput.setText(expenseToEdit.getNote());
        } else {
            Toast.makeText(this, "Invalid expense data", Toast.LENGTH_SHORT).show();
            finish();
        }

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String amountStr = amountInput.getText().toString().trim();
            String date = dateInput.getText().toString().trim();
            String category = categoryInput.getText().toString().trim();
            String note = noteInput.getText().toString().trim();

            if (title.isEmpty() || amountStr.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Title, Amount, and Date are required", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update existing expense model
            expenseToEdit.setTitle(title);
            expenseToEdit.setAmount(amount);
            expenseToEdit.setDate(date);
            expenseToEdit.setCategory(category);
            expenseToEdit.setNote(note);

            Toast.makeText(this, "Expense updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
