package com.example.samsungnormalannualproject.Utils;

import com.example.samsungnormalannualproject.API.JSONPlaceHolderApi;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";

    public static JSONPlaceHolderApi getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
