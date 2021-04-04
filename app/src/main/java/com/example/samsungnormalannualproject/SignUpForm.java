package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.samsungnormalannualproject.interfaces.ActivitySettings;
public class SignUpForm extends BaseActivity  implements AdapterView.OnItemSelectedListener {
    private boolean isMen = false;

    private View view;
    private Spinner eyes_color_spinner;

    private Button button;

    private static final String[] paths = {"item 1", "item 2", "item 3"};

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

    private void spinner() {
        this.eyes_color_spinner = (Spinner)findViewById(R.id.eyes_color_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.eyes_color_spinner.setAdapter(adapter);
        this.eyes_color_spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acitibity_sign_up_fill_form);

        this.isMen = getIntent().getExtras().getBoolean(SelectGenderActivity.GENDER);
        this.view = findViewById(R.id.sign_up_fill_form_view);
        this.button = findViewById(R.id.confirm_button);

        setBackGround();

        spinner();

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WhatKindOfPeopleLookinFor.class);
                startActivity(intent);
            }
        });


//        RadioButton simpleRadioButton=(RadioButton) findViewById(R.id.hello_world);
//        simpleRadioButton.setText("I am a radiobutton"); // displayed text of radio button
    }
}