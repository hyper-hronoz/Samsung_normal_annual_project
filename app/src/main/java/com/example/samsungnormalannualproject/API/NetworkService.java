package com.example.samsungnormalannualproject.API;

import com.example.samsungnormalannualproject.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://" + Config.DOMAIN;
    private Retrofit mRetrofit;

    private NetworkService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);
    }
}