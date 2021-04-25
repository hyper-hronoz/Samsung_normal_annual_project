package com.example.samsungnormalannualproject.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisteredUsers {
    public void setRegisteredUsers(List<RegisteredUser> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public List<RegisteredUser> getRegisteredUsers() {
        return this.registeredUsers;
    }

    public RegisteredUsers(List<RegisteredUser> musics) {
        this.registeredUsers = getRegisteredUsers();
    }

    @SerializedName("registeredUsers")
    private List<RegisteredUser> registeredUsers;
}
