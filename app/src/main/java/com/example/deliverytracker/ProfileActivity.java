package com.example.deliverytracker;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deliverytracker.Services.AuthManager;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private AuthManager authManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        authManager = new AuthManager(this);

        TextView userEmail = findViewById(R.id.emailView);
        Button logoutButton = findViewById(R.id.logoutButton);
        Button goToAppButton = findViewById(R.id.Go_AppButton);

        // Отображаем email текущего пользователя
        String email = authManager.getCurrentUser().getEmail();
        if (email!=null) {
            userEmail.setText("Email: " + email);
        }

        // Обработчик кнопки выхода
        logoutButton.setOnClickListener(v -> {
            authManager.logoutUser();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish(); // Завершаем ProfileActivity
        });
        goToAppButton.setOnClickListener(v -> {

            Intent intent = new Intent(this, TrackInfoActivity.class);
            startActivity(intent);
            finish(); // Завершаем ProfileActivity
        });
    }
}
