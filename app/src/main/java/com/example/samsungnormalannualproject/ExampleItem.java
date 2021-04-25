package com.example.samsungnormalannualproject;

public class ExampleItem {
    private int mImageResource;
    private String mText1;
    private String mText2;
    public ExampleItem(int imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
    }
    public void changeText1(String text) {
        mText1 = text;
    }
    public int getImageResource() {
        return mImageResource;
    }
    public String getText1() {
        return mText1;
    }
}