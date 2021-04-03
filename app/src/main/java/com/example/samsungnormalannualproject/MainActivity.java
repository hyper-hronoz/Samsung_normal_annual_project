package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.samsungnormalannualproject.interfaces.ActivitySettings;

public class MainActivity extends AppCompatActivity  {
    private final boolean isAuth = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, LoginOrSignUp.class);
        startActivity(intent);

        setContentView(R.layout.activity_main); // пусть отправляет запросы
    }
}