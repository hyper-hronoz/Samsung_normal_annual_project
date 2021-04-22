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

    public static String gender;

    private static View view;

    // здесь меняется выбранный элемент
    private void changeGenderSelection() {
        System.out.println(this.gender);
        if (this.gender == getString(R.string.genderMan)) {
            selectMenButton.setBackgroundResource(R.drawable.gender_selecion_button);
            selectWomenButton.setBackgroundColor(Color.TRANSPARENT);
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_boy);
        }

        else if (this.gender == getString(R.string.genderWoman)){
            selectWomenButton.setBackgroundResource(R.drawable.gender_selecion_button);
            selectMenButton.setBackgroundColor(Color.TRANSPARENT);
            this.view.setBackgroundResource(R.drawable.select_gender_gradient_girl);
        }
    }

    private void changeGender(String g) {
        this.gender =  g;
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
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

        // устанавливаем дефолтный пол
        this.gender = getString(R.string.genderMan);

        // нажатие на кнопку подтвердить выбор
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
                changeGender(getString(R.string.genderMan));
                changeGenderSelection();
            }
        });

        // слушаем события нажатия на бабу
        this.selectWomenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGender(getString(R.string.genderWoman));
                changeGenderSelection();
            }
        });

        changeGenderSelection();
    }
}