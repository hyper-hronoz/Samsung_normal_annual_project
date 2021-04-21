package com.example.samsungnormalannualproject.Models;

public class User {
    public String username;
    public String password;
    public String gender;

    public User(String login, String password, String gender) {
        this.username = login;
        this.password = password;
        this.gender = gender;
    }

    public User(String login, String password) {
        this.username = login;
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setEmail(String email) {
        this.username = email;
    }

    public String getEmail() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
