package com.example.deliverytracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.deliverytracker.Services.AuthManager;
import com.example.deliverytracker.Services.NotificationHelper;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AuthManager authManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация AuthManager
        authManager = new AuthManager(this);

        // Проверяем текущего пользователя
        checkCurrentUser();
    }

    private void checkCurrentUser() {
        FirebaseUser currentUser = authManager.getCurrentUser();

        if (currentUser != null) {
            // Если пользователь авторизован, переходим на экран профиля
            navigateToProfile(currentUser.getEmail());
        } else {
            // Если пользователь не авторизован, переходим на экран входа
            navigateToLogin();
        }
    }

    // Переход к экрану профиля
    private void navigateToProfile(String email) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish(); // Завершаем MainActivity, чтобы пользователь не мог вернуться
    }

    // Переход к экрану входа
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Завершаем MainActivity, чтобы пользователь не мог вернуться
    }
}
