package com.example.samsungnormalannualproject.Models;

import com.example.samsungnormalannualproject.SignUpActivity;

import java.util.Map;

public class RegisteredUser extends User  {
    public Map<String, String> userInfo;
    public String userPhoto;

    public RegisteredUser(String username, Map<String, String> userInfo) {
        super(username);
        this.userInfo = userInfo;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public RegisteredUser(String username, Map<String, String> userInfo, String userPhoto) {
        super(username);
        this.userInfo = userInfo;
        this.userPhoto = userPhoto;
    }

    public RegisteredUser(String username) {
        super(username);
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
