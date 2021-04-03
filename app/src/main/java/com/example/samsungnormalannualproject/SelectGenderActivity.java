package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.samsungnormalannualproject.interfaces.ActivitySettings;

public class SelectGenderActivity extends AppCompatActivity implements ActivitySettings {
    private ImageButton selectWomenButton;
    private ImageButton selectMenButton;
    private Button confirmGenderButton;

    // ключи
    private static final String PREFS = "PREFS";
    public static final String GENDER = "GENDER";

    private final String WOMEN_SELECTOR_KEY = "isClickedWomen";
    private final String MEN_SELECTOR_KEY = "isClickedMen";


    private boolean isMen = true;

    private static View view;

    // здесь меняется выбранный элемент
    private void changeGenderSelection(boolean isClickedMen) {
        if (isClickedMen) {
            selectMenButton.setBackgroundResource(R.drawable.gender_selecion_button);
            selectWomenButton.setBackgroundColor(Color.TRANSPARENT);
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_boy);
            this.isMen = true;
        }

        if (!isClickedMen){
            selectWomenButton.setBackgroundResource(R.drawable.gender_selecion_button);
            selectMenButton.setBackgroundColor(Color.TRANSPARENT);
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_girl);
            this.isMen = false;
        }
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(this.GENDER, this.isMen);
        intent.putExtras(bundle);
        System.out.println("Don't fuck " + this.isMen);
        startActivity(intent);
    }

    // скрывает боттом меню
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

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

    // создается активити не знаю зачем комментирую, возможно для порядка в беспорядке
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

        hideSystemUI();

        setContentView(R.layout.activity_select_gender);

        // объявления элементов в конструкторе
        this.selectMenButton = (ImageButton) findViewById(R.id.men_imageView);
        this.selectWomenButton = (ImageButton) findViewById(R.id.women_ImageButton);
        this.view = (View) findViewById(R.id.view);
        this.confirmGenderButton = (Button) findViewById(R.id.button);

        this.confirmGenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUpActivity();
            }
        });

        // слушаем нажатия на поцана
        this.selectMenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGenderSelection(true);
            }
        });

        // слушаем события нажатия на бабу
        this.selectWomenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGenderSelection(false);
            }
        });
    }
}