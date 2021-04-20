package com.example.samsungnormalannualproject.Utils;

public class FormValidator {

    private String string;

    public FormValidator(String string) {
        this.string = string.trim();
    }

    public FormValidator(int number) {
        this.string = String.valueOf(number).trim();
    }

    public FormValidator(float number) {
        this.string = String.valueOf(number).trim();
    }

    public boolean setMinLength(int minLength) {
        if (this.string.length() < minLength) {
            return false;
        } else {
            return true;
        }
    }
}
