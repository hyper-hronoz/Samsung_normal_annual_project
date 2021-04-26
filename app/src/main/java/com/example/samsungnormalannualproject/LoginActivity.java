package com.example.samsungnormalannualproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import retrofit2.Call;

import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.API.NetworkServiceResponse;
import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;
import com.example.samsungnormalannualproject.Models.User;
import com.example.samsungnormalannualproject.Utils.InputDataValidator;
import com.google.gson.GsonBuilder;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {

    private String login;
    private String password;

    private EditText loginEditText;
    private EditText passwordEditText;
    private Button button;

    private String gender;
    private View view;

    private TextView haveAccount;

    private void setBackGround() {

        System.out.println(this.gender);

        try {
            if (this.gender == getString(R.string.genderMan)) {
                this.view.setBackgroundResource(R.drawable.select_gender_gradient_boy);
            }
            if (this.gender == getString(R.string.genderWoman)) {
                this.view.setBackgroundResource(R.drawable.select_gender_gradient_girl);
            }
        } catch (Exception e) {
            return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        System.out.println(this.loginEditText);

        this.view = (View) findViewById(R.id.login_view);
        this.loginEditText = findViewById(R.id.login_login);
        this.passwordEditText = findViewById(R.id.login_password);
        this.button = findViewById(R.id.login_confirm);
        this.haveAccount = findViewById(R.id.haveAccount);

        this.gender = SelectGenderActivity.gender;

        setBackGround();

        BaseActivity.activityHeading = findViewById(R.id.activity_heading);

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        this.haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectGenderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void login() {
        this.login = this.loginEditText.getText().toString();
        this.password = this.passwordEditText.getText().toString();


        InputDataValidator inputDataValidator = new InputDataValidator(this.login, this.password);

        String response = inputDataValidator.validate();
        if (response == "OK") {
            User user = new User(this.login, this.password);

            new Login(user, getApplicationContext());


        } else {
            new ToastError(getApplicationContext(), response);
        }
    }

    public class Login {
        User user;
        public Login(User user, Context context) {
            this.user = user;
            System.out.println("Что отправляем: " + new GsonBuilder().setPrettyPrinting().create().toJson(user));

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

            Call<NetworkServiceResponse> call = jsonPlaceHolderApi.loginUser(this.user);

            call.enqueue(new Callback<NetworkServiceResponse>() {
                @Override
                public void onResponse(Call<NetworkServiceResponse> call, Response<NetworkServiceResponse> response) {
                    Log.d("Login status code is", String.valueOf(response.code()));
                    Log.d("Login response body is", String.valueOf(response.body()));
                    if (response.code() == 400) {
                        new ToastError(getApplicationContext(), "incorrect login or password");
                    } else {
                        String jwt = new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getResponse()).replaceAll("^.|.$", "");
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        Log.d("JWT toke from login", jwt);
                        editor.putString(getString(R.string.JWTToken), jwt);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), UserDataActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<NetworkServiceResponse> call, Throwable t) {
                    System.out.println("Failure");
                }
            });
        }
    }
}