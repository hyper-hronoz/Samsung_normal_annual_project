package com.example.samsungnormalannualproject.API;

import com.example.samsungnormalannualproject.Config;
import com.example.samsungnormalannualproject.Models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "http://" + Config.DOMAIN + ":" + Config.PORT;
    private Retrofit mRetrofit;
    private User user;

    public NetworkService(User user) {
        this.user = user;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        Call<NetworkServiceResponse> call = jsonPlaceHolderApi.registerUser(user);

        call.enqueue(new Callback<NetworkServiceResponse>() {
            @Override
            public void onResponse(Call<NetworkServiceResponse> call, Response<NetworkServiceResponse> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<NetworkServiceResponse> call, Throwable t) {
                System.out.println(t);
                System.out.println("failuuuuuuuuuuuuuuuuure");
            }
        });

    }
}