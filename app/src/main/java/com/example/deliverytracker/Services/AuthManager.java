package com.example.deliverytracker.Services;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthManager {

    private final FirebaseAuth mAuth;
    private final Context context;

    // Конструктор
    public AuthManager(Context context) {

        this.mAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    // Регистрация пользователя
    public void registerUser(String email, String password) {
        if (!validateInput(email, password)) return;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        sendEmailVerification();
                        Toast.makeText(context, "Регистрация успешна: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Ошибка регистрации: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Авторизация пользователя
    public void loginUser(String email, String password, Class<?> nextActivity) {
        if (!validateInput(email, password)) return;

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(context, "Вход успешен: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, nextActivity);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Ошибка входа: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Выход из аккаунта
    public void logoutUser() {
        mAuth.signOut();
        Toast.makeText(context, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
    }

    // Восстановление пароля
    public void resetPassword(String email) {
        if (email.isEmpty()) {
            Toast.makeText(context, "Введите email", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Ссылка для восстановления отправлена на email", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Ошибка: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Отправка подтверждения email
    public void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Подтверждение отправлено на email", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Ошибка отправки подтверждения: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Получение текущего пользователя
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    // Валидация email и пароля
    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Введите email и пароль", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(context, "Пароль должен быть не менее 6 символов", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
