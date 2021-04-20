package com.example.samsungnormalannualproject.API;


import com.example.samsungnormalannualproject.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @POST("/auth/registration")
    Call<NetworkServiceResponse> registerUser();
}
