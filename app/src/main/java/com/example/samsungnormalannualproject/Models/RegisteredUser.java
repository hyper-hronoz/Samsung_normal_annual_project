package com.example.samsungnormalannualproject.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class RegisteredUser  {
    public String username;

    public Map<String, String> userInfo;

    public RegisteredUser(String username, Map<String, String> userInfo) {
        this.username = username;
        this.userInfo = userInfo;
    }

    public Map<String, String> getUserInfo() {
        return userInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserInfo(Map<String, String> userInfo) {
        this.userInfo = userInfo;
    }
}
