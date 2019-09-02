package com.google.codelabs.appauth.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {


    @SerializedName("stars")
    @Expose
    Integer reviewStars;


    @SerializedName("message")
    @Expose
    String reviewMessage;


    public Review() {
    }

    public Review(Integer reviewStars, String reviewMessage) {
        this.reviewStars = reviewStars;
        this.reviewMessage = reviewMessage;
    }

    public Integer getReviewStars() {
        return reviewStars;
    }

    public void setReviewStars(Integer reviewStars) {
        this.reviewStars = reviewStars;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }
}
