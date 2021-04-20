package com.example.samsungnormalannualproject.API;


import com.example.samsungnormalannualproject.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @POST("/auth/registration")
    Call<NetworkServiceResponse> registerUser(@Body User user);

    @POST("/auth/login")
    Call<NetworkServiceResponse> loginUser(@Body User user);
}
