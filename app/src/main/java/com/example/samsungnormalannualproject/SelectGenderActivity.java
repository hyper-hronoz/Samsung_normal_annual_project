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

public class SelectGenderActivity extends BaseActivity {
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
    private void changeGenderSelection() {
        System.out.println(this.isMen);
        if (isMen) {
            selectMenButton.setBackgroundResource(R.drawable.gender_selecion_button);
            selectWomenButton.setBackgroundColor(Color.TRANSPARENT);
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_boy);
        }

        if (!isMen){
            selectWomenButton.setBackgroundResource(R.drawable.gender_selecion_button);
            selectMenButton.setBackgroundColor(Color.TRANSPARENT);
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_girl);
        }
    }

    private void changeGender() {
        this.isMen = !isMen;
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(this.GENDER, this.isMen);
        intent.putExtras(bundle);
        System.out.println("Don't fuck " + this.isMen);
        startActivity(intent);
    }

    // создается активити не знаю зачем комментирую, возможно для порядка в беспорядке
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                changeGender();
                changeGenderSelection();
            }
        });

        // слушаем события нажатия на бабу
        this.selectWomenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGender();
                changeGenderSelection();
            }
        });

        changeGenderSelection();
    }
}