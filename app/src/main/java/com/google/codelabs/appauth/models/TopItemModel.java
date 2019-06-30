package com.google.codelabs.appauth.models;

public class TopItemModel {

    String mTopName;
    Integer mTopPrice;
    int mTopImage;

    public TopItemModel(String mTopName, Integer mTopPrice, int mTopImage) {
        this.mTopName = mTopName;
        this.mTopPrice = mTopPrice;
        this.mTopImage = mTopImage;
    }

    public String getmTopName() {
        return mTopName;
    }

    public void setmTopName(String mTopName) {
        this.mTopName = mTopName;
    }

    public Integer getmTopPrice() {
        return mTopPrice;
    }

    public void setmTopPrice(Integer mTopPrice) {
        this.mTopPrice = mTopPrice;
    }

    public int getmTopImage() {
        return mTopImage;
    }

    public void setmTopImage(int mTopImage) {
        this.mTopImage = mTopImage;
    }
}
