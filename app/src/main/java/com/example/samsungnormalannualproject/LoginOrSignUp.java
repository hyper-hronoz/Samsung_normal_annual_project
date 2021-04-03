package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.samsungnormalannualproject.interfaces.ActivitySettings;

public class LoginOrSignUp extends BaseActivity {

    private Button loginButton;
    private Button signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_or_sign_up);

        this.loginButton = (Button) findViewById(R.id.login_or_sign_up_login_button);
        this.signUpButton = (Button) findViewById(R.id.login_or_sign_up_sign_up_button);

        System.out.println(this.loginButton);

        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        this.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectGenderActivity.class);
                startActivity(intent);
            }
        });
    }
}