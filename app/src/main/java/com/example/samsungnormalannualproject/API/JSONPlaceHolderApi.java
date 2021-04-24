package com.example.samsungnormalannualproject.API;


import android.content.SharedPreferences;

import com.example.samsungnormalannualproject.Models.RegisteredUser;
import com.example.samsungnormalannualproject.Models.UploadImage;
import com.example.samsungnormalannualproject.Models.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface JSONPlaceHolderApi {
    @POST("/auth/registration")
    Call<NetworkServiceResponse> registerUser(@Body User user);

    @POST("/auth/login")
    Call<NetworkServiceResponse> loginUser(@Body User user);

    @GET("/auth/user")
    Call<RegisteredUser> getUserData(@Header("Authorization") String token);

    @PUT("/auth/user")
    Call<RegisteredUser> updateUserData(@Header("Authorization") String token, @Body RegisteredUser registeredUser);

    @POST("/auth/upload")
    Call<UploadImage> postImage(@Header("Authorization") String token, @Body UploadImage uploadImage);
}
