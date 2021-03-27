package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.telephony.ims.ImsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class SelectGenderActivity extends AppCompatActivity {
    private ImageButton selectWomenButton;
    private ImageButton selectMenButton;

    public static View view;

    public static boolean isClickedWomen = false;
    public static boolean isClickedMen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_select_gender);
        this.selectMenButton = (ImageButton) findViewById(R.id.men_imageView);
        this.selectWomenButton = (ImageButton) findViewById(R.id.women_ImageButton);
        this.view = (View) findViewById(R.id.view);

        selectMenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SelectGenderActivity.isClickedMen) {
                    selectMenButton.setBackgroundResource(R.drawable.gender_selecion_button);
                    selectWomenButton.setBackgroundColor(Color.TRANSPARENT);
                    SelectGenderActivity.isClickedMen = true;
                    SelectGenderActivity.isClickedWomen = false;
                    SelectGenderActivity.view.setBackgroundResource(R.drawable.select_gender_gradient_boy);
                } else {
                    selectMenButton.setBackgroundColor(Color.TRANSPARENT);
                    SelectGenderActivity.isClickedMen = false;
                }
            }
        });

        selectWomenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SelectGenderActivity.isClickedWomen) {
                    selectWomenButton.setBackgroundResource(R.drawable.gender_selecion_button);
                    selectMenButton.setBackgroundColor(Color.TRANSPARENT);
                    SelectGenderActivity.isClickedWomen = true;
                    SelectGenderActivity.isClickedMen = false;
                    SelectGenderActivity.view.setBackgroundResource(R.drawable.select_gender_gradient_girl);
                } else {
                    selectWomenButton.setBackgroundColor(Color.TRANSPARENT);
                    SelectGenderActivity.isClickedWomen = false;

                }
            }
        });
    }
}