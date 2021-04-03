package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.samsungnormalannualproject.interfaces.ActivitySettings;

public class SignUpFrom extends AppCompatActivity implements ActivitySettings {
    private boolean isMen = false;

    private View view;

    // устанавливает фон для элементов в зависимости от выбранного пола
    private void setBackGround() {

        System.out.println(this.view);

        if (this.isMen) {
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_boy);
        }

        if (!this.isMen) {
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_girl);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // сдесь у нас настройка активити берзрамочноая
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.acitibity_sign_up_fill_form);

        this.isMen = getIntent().getExtras().getBoolean(SelectGenderActivity.GENDER);
        this.view = findViewById(R.id.sign_up_fill_form_view);

        setBackGround();

        hideSystemUI();
    }

    @Override
    // скрывает топ меню и делаем приложения безрамочным
    public void hideSystemUI() {

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}