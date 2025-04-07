package com.example.expensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputOTP;
    private Button btnLogin, btnResendOTP;
    private CheckBox checkboxRememberMe;
    private TextView textForgotPassword, textSignUp;
    private SharedPreferences sharedPreferences;
    private TextView textDashboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputOTP = findViewById(R.id.inputOTP);
        btnLogin = findViewById(R.id.btnLogin);
        btnResendOTP = findViewById(R.id.btnResendOTP);
        checkboxRememberMe = findViewById(R.id.checkboxRememberMe);
        textForgotPassword = findViewById(R.id.textForgotPassword);
        textSignUp = findViewById(R.id.textSignUp);
        textDashboard = findViewById(R.id.textDashboard);
        ;

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isRemembered = sharedPreferences.getBoolean("rememberMe", false);

        if (isRemembered) {
            inputEmail.setText(sharedPreferences.getString("email", ""));
            inputPassword.setText(sharedPreferences.getString("password", ""));
            checkboxRememberMe.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btnResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "OTP Resent Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        textDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            }
        });
    }

    private void loginUser() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String otp = inputOTP.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(otp)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // You should implement actual authentication logic here
        if (email.equals("test@example.com") && password.equals("password") && otp.equals("1234")) {
            if (checkboxRememberMe.isChecked()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.putBoolean("rememberMe", true);
                editor.apply();
            } else {
                sharedPreferences.edit().clear().apply();
            }

            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class)); // Fixed redirection
            finish(); // Close LoginActivity to prevent going back
        } else {
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
