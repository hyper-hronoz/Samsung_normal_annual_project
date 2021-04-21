package com.example.samsungnormalannualproject.API;

import com.google.gson.annotations.SerializedName;

public class NetworkServiceResponse {
    @SerializedName("token")
    public String response;

    public NetworkServiceResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
