package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private final boolean isAuth = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // пусть отправляет запросы
        if (!isAuth) {
            Intent intent = new Intent(this, SelectGenderActivity.class);
            startActivity(intent);
        } else {
        }
    }
}