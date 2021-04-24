package com.example.samsungnormalannualproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;
import com.example.samsungnormalannualproject.Models.RegisteredUser;
import com.example.samsungnormalannualproject.Utils.HashMapPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpForm extends BaseActivity {
    private boolean isMen = false;

    private View view;
    private Spinner eyes_color_spinner;

    private Map<String, String> userInfo;

    private Button button;

    private static final String[] paths = {"item 1", "item 2", "item 3"};

    private RadioButton manRadioButton;
    private RadioButton womanRadioButton;

    private String gender;
    private RadioGroup genderRadioGroup;

    private EditText eyesColorEditText;
    private EditText hairColorEditText;
    private EditText userHeightEditText;
    private EditText userAboutEditText;
    private EditText userAgeEditText;

    private Uri imageURi;

    private Bitmap bitmap;




    private RequestBody imageToUpload;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.eyes_color_spinner.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acitibity_sign_up_fill_form);

        this.view = findViewById(R.id.sign_up_fill_form_view);
        this.button = findViewById(R.id.confirm_button);

        setBackGround();

        this.eyesColorEditText = findViewById(R.id.eyes_color);
        this.hairColorEditText = findViewById(R.id.hairs_color);
        this.userHeightEditText = findViewById(R.id.height);
        this.userAboutEditText = findViewById(R.id.aboutUser);
        this.userAgeEditText = findViewById(R.id.age);



//        spinner();

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.userSharedPreferencesKey), Context.MODE_PRIVATE);
        String userData = sharedPref.getString(getString(R.string.userData), "");

        RegisteredUser registeredUser = new Gson().fromJson(userData, RegisteredUser.class);
        this.gender = registeredUser.userInfo.get("gender");

        Log.d("userInfo is", "onCreate: " + this.gender);

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData();
//                Intent intent = new Intent(getApplicationContext(), WhatKindOfPeopleLookinFor.class);
//                startActivity(intent);
                updateUserData();
            }
        });

        this.genderRadioGroup = findViewById(R.id.select_gender_radio_group);
        this.manRadioButton = findViewById(R.id.radio_man);
        this.womanRadioButton = findViewById(R.id.radio_woman);

//
        this.manRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGender("M");
                changeGenderRadioButton();
            }
        });



        this.womanRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGender("W");
                changeGenderRadioButton();
            }
        });

        changeGenderRadioButton();


//        RadioButton simpleRadioButton=(RadioButton) findViewById(R.id.hello_world);
//        simpleRadioButton.setText("I am a radiobutton"); // displayed text of radio button
    }


    private void hz() {
        File file = new File(imageURi.getPath());
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

// add another part within the multipart request
         this.imageToUpload =
                RequestBody.create(MediaType.parse("multipart/form-data"), UUID.randomUUID().toString().replace("-", ""));
    }


    private void updateUserData() {
        Map<String, String> registeredUserInfo = new HashMap<String, String>();

        registeredUserInfo.put("gender", this.gender);
        registeredUserInfo.put("eyesColor", this.userHeightEditText.getText().toString());
        registeredUserInfo.put("hairsColor", this.hairColorEditText.getText().toString());
        registeredUserInfo.put("height", this.userHeightEditText.getText().toString());
        registeredUserInfo.put("aboutUser", this.userAboutEditText.getText().toString());
        registeredUserInfo.put("age", this.userAgeEditText.getText().toString());

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.userSharedPreferencesKey), Context.MODE_PRIVATE);
        String user = sharedPref.getString(getString(R.string.userData), "");

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
        String JWTToken = sharedPreferences.getString(getString(R.string.JWTToken), "");

        System.out.println(JWTToken);

        Gson gson = new Gson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisteredUser registeredUser = gson.fromJson(user, RegisteredUser.class);
        registeredUser.setUserInfo(registeredUserInfo);

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(registeredUser));

        Call<RegisteredUser> call = jsonPlaceHolderApi.updateUserData("Bearer " + JWTToken, registeredUser);

        call.enqueue(new Callback<RegisteredUser>() {
            @Override
            public void onResponse(Call<RegisteredUser> call, Response<RegisteredUser> response) {
                Log.d("Update userD code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    new ToastError(getApplicationContext(), "Success");
                    Intent intent = new Intent(getApplicationContext(), BottomMenu.class);
                    startActivity(intent);
                    finish();
                } else {
                    new ToastError(getApplicationContext(), "Something is wrong check for data is properly set");
                }
            }

            @Override
            public void onFailure(Call<RegisteredUser> call, Throwable t) {
                Log.e("Update Data Failure", t.getMessage());
            }
        });
    }

    private void changeGenderRadioButton() {
        if (this.gender.equals("M")) {
            genderRadioGroup.check(R.id.radio_man);
            System.out.println("hello");
        }
        if (this.gender.equals("W")) {
            genderRadioGroup.check(R.id.radio_woman);
            System.out.println("pello");
        }
    }

    private void changeGender(String gender) {
        this.gender = gender;
    }
}