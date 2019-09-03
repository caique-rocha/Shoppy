package com.google.codelabs.appauth.models;

public class TopItemModel {

    String mTopName;
    Integer mTopPrice;
    String mImageUrl;
    String mImageId;
    String mImageCategory;

    public TopItemModel(String mTopName, Integer mTopPrice, String mImageUrl, String mImageId, String mImageCategory) {
        this.mTopName = mTopName;
        this.mTopPrice = mTopPrice;
        this.mImageUrl = mImageUrl;
        this.mImageId = mImageId;
        this.mImageCategory = mImageCategory;
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

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmImageId() {
        return mImageId;
    }

    public void setmImageId(String mImageId) {
        this.mImageId = mImageId;
    }

    public String getmImageCategory() {
        return mImageCategory;
    }

    public void setmImageCategory(String mImageCategory) {
        this.mImageCategory = mImageCategory;
    }
}
