package com.example.deliverytracker;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deliverytracker.Services.AuthManager;

public class RegisterActivity extends AppCompatActivity {

    private AuthManager authManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        authManager = new AuthManager(this);

        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button registerButton = findViewById(R.id.registerButton);
        Button goToLoginButton = findViewById(R.id.go_to_LoginButton);

        // Обработчик кнопки регистрации
        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            authManager.registerUser(email, password);
        });
        goToLoginButton.setOnClickListener(v->{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
