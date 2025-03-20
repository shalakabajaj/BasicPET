package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail, inputOTP, inputNewPassword;
    private Button btnSendOTP, btnVerifyOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputEmail = findViewById(R.id.inputEmail);
        inputOTP = findViewById(R.id.inputOTP);
        inputNewPassword = findViewById(R.id.inputNewPassword);
        btnSendOTP = findViewById(R.id.btnSendOTP);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else {
                    sendOTP(email);
                }
            }
        });

        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = inputOTP.getText().toString().trim();
                String newPassword = inputNewPassword.getText().toString().trim();

                if (otp.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Please enter OTP and new password", Toast.LENGTH_SHORT).show();
                } else {
                    verifyOTP(otp, newPassword);
                }
            }
        });
    }

    private void sendOTP(String email) {
        // TODO: Implement OTP sending logic (Firebase, backend API, etc.)
        Toast.makeText(this, "OTP sent to " + email, Toast.LENGTH_SHORT).show();
    }

    private void verifyOTP(String otp, String newPassword) {
        // TODO: Implement OTP verification logic
        Toast.makeText(this, "Password reset successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
        finish();
    }
}
