package com.example.samsungnormalannualproject.API;


import android.content.SharedPreferences;

import com.example.samsungnormalannualproject.Models.RegisteredUser;
import com.example.samsungnormalannualproject.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface JSONPlaceHolderApi {
    @POST("/auth/registration")
    Call<NetworkServiceResponse> registerUser(@Body User user);

    @POST("/auth/login")
    Call<NetworkServiceResponse> loginUser(@Body User user);

    @GET("/auth/user")
    Call<RegisteredUser> getUserData(@Header("Authorization") String token);

    @PUT("/auth/user")
    Call<RegisteredUser> updateUserData(@Header("Authorization") String token, @Body RegisteredUser registeredUser);
}
