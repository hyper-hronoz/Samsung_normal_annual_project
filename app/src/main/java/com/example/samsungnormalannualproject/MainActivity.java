package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
        System.out.println(getString(R.string.JWTToken));
        String JWTToken = sharedPref.getString(getString(R.string.JWTToken), "");

        System.out.println("JWT is found " + JWTToken);

        if (JWTToken != "") {
            Intent intent = new Intent(this, ViewingNominationsActvity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginOrSignUp.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main); // пусть отправляет запросы
    }
}