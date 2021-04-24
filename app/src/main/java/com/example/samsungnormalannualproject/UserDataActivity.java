package com.example.samsungnormalannualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;
import com.example.samsungnormalannualproject.API.NetworkServiceResponse;
import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;
import com.example.samsungnormalannualproject.Models.RegisteredUser;
import com.example.samsungnormalannualproject.Models.UploadImage;
import com.example.samsungnormalannualproject.Models.User;
import com.example.samsungnormalannualproject.Utils.HashMapPreferences;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.JWTTokenSharedPreferencesKey), Context.MODE_PRIVATE);
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
                System.out.println("Status code is: " + response.code());
                if (response.code() == 401) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.userSharedPreferencesKey), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.userData), new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    HashMapPreferences.saveMap(getApplicationContext(), getString(R.string.userInfo), response.body().getUserInfo());
                    Map<String, String> userInfo = response.body().getUserInfo();
                    Log.d("userInfo", String.valueOf(response.body().getUserInfo().size()));
                    if (response.body().getUserPhoto() == "") {
                        Intent intent = new Intent(getApplicationContext(), UploadImageActivity.class);
                        startActivity(intent);
                    }
                    else if (userInfo.size() == 1) {
                        Intent intent = new Intent(getApplicationContext(), SignUpForm.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), BottomMenu.class);
                        startActivity(intent);
                    }
                    editor.commit();
                    Log.d("Call", "onResponse: " + call);
                    Log.d("GetUserDataResponse", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                }
                finish();
            }

            @Override
            public void onFailure(Call<RegisteredUser> call, Throwable t) {
                Log.e("UserGetData", "сервер кажись лежит");
            }
        });

    }
}