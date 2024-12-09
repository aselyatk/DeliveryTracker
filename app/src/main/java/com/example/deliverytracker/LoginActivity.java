package com.example.deliverytracker;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deliverytracker.Services.AuthManager;

public class LoginActivity extends AppCompatActivity {

    private AuthManager authManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        authManager = new AuthManager(this);

        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);
        Button goToRegisterButton = findViewById(R.id.go_to_registerButton);

        // Обработчик кнопки входа
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            authManager.loginUser(email, password, ProfileActivity.class);
        });

        // Переход к экрану регистрации
        goToRegisterButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);

            startActivity(intent);
            finish();
        });
    }
}
