package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.API.NetworkServiceResponse;
import com.example.samsungnormalannualproject.Models.RegisteredUser;
import com.example.samsungnormalannualproject.Models.User;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
        System.out.println(getString(R.string.JWTToken));
        String JWTToken = sharedPref.getString(getString(R.string.JWTToken), "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        Call<RegisteredUser> call = jsonPlaceHolderApi.getUserData("Bearer " + JWTToken);


        call.enqueue(new Callback<RegisteredUser>() {
            @Override
            public void onResponse(Call<RegisteredUser> call, Response<RegisteredUser> response) {
                Log.d("Call", "onResponse: " + call);
                Log.d("GetUserDataResponse", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
            }

            @Override
            public void onFailure(Call<RegisteredUser> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}