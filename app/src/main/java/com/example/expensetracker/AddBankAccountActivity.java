package com.example.expensetracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddBankAccountActivity extends AppCompatActivity {

    private EditText accountHolderName, accountNumber, bankName;
    private Button saveBankDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_account); // The layout for the bank account form

        // Initialize views
        accountHolderName = findViewById(R.id.accountHolderName);
        accountNumber = findViewById(R.id.accountNumber);
        bankName = findViewById(R.id.bankName);
        saveBankDetailsButton = findViewById(R.id.saveBankDetailsButton);

        saveBankDetailsButton.setOnClickListener(v -> saveBankDetails());
    }

    private void saveBankDetails() {
        String holderName = accountHolderName.getText().toString();
        String accountNum = accountNumber.getText().toString();
        String bank = bankName.getText().toString();

        if (holderName.isEmpty() || accountNum.isEmpty() || bank.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Save the bank account details (you can store them in SQLite or any other database)
            // For now, just showing a toast message
            Toast.makeText(this, "Bank Account Saved", Toast.LENGTH_SHORT).show();

            // Optionally, close the activity and go back to the dashboard
            finish();
        }
    }
}
