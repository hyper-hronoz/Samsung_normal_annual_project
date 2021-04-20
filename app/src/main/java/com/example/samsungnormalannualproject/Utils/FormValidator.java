package com.example.samsungnormalannualproject.Utils;

public class FormValidator {

    private String string;

    public FormValidator(String string) {
        this.string = string;
    }

    public FormValidator(int number) {
        this.string = String.valueOf(number);
    }

    public FormValidator(float number) {
        this.string = String.valueOf(number);
    }

    public boolean setMinLength(int minLength) {
        if (this.string.length() < minLength) {
            return false;
        } else {
            return true;
        }
    }
}
