package com.example.samsungnormalannualproject.Models;

public class User {
    public String login;
    public String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setEmail(String email) {
        this.login = email;
    }

    public String getEmail() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
