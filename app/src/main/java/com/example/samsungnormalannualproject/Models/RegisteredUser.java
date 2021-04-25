package com.example.samsungnormalannualproject.Models;

import android.content.Intent;

import com.example.samsungnormalannualproject.SignUpActivity;

import java.util.Map;

public class RegisteredUser extends User  {
    public int age;
    public int height;

    public String hairColor;
    public String eyesColor;
    public String aboutUser;
    public String userPhoto;
    public String instagramProfle;
    public String facebookProfile;
    public String vkProfile;

    public RegisteredUser(String aboutUser, int age, String eyesColor, String hairColor, int height, String userPhoto, String username, String vkProfile, String facebookProfile, String instagramProfle) {
        super(username);
        this.aboutUser = aboutUser;
        this.age = age;
        this.eyesColor = eyesColor;
        this.hairColor = hairColor;
        this.height = height;
        this.userPhoto = userPhoto;
        this.vkProfile = vkProfile;
        this.facebookProfile = facebookProfile;
        this.instagramProfle = instagramProfle;
    }


    public RegisteredUser(String username, String password, String gender) {
        super(username, password, gender);
    }

    public void setInstagramProfle(String instagramProfle) {
        this.instagramProfle = instagramProfle;
    }

    public void setFacebookProfile(String facebookProfile) {
        this.facebookProfile = facebookProfile;
    }

    public void setVkProfile(String vkProfile) {
        this.vkProfile = vkProfile;
    }

    public String getInstagramProfle() {
        return instagramProfle;
    }

    public String getFacebookProfile() {
        return facebookProfile;
    }

    public String getVkProfile() {
        return vkProfile;
    }

    public RegisteredUser(String username, int age, String hairColor, String eyesColor, String aboutUser, String userPhoto, String instagramProfle, String facebookProfile, String vkProfile) {
        super(username);
        this.aboutUser = aboutUser;
        this.hairColor = hairColor;
        this.eyesColor = eyesColor;
        this.userPhoto = userPhoto;
        this.instagramProfle = instagramProfle;
        this.facebookProfile = facebookProfile;
        this.vkProfile = vkProfile;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public void setEyesColor(String eyesColor) {
        this.eyesColor = eyesColor;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public String getHairColor() {
        return hairColor;
    }

    public String getEyesColor() {
        return eyesColor;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public String getUserPhoto() {
        return userPhoto;
    }
}
