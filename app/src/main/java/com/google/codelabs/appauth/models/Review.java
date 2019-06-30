package com.google.codelabs.appauth.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("id")
    @Expose String reviewId;

    @SerializedName("stars")
    @Expose String reviewStars;

    @SerializedName("date")
    @Expose String reviewDate;

    @SerializedName("message")
    @Expose String reviewMessage;

    public Review(String reviewId, String reviewStars, String reviewDate, String reviewMessage) {
        this.reviewId = reviewId;
        this.reviewStars = reviewStars;
        this.reviewDate = reviewDate;
        this.reviewMessage = reviewMessage;
    }

    public Review() {
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewStars() {
        return reviewStars;
    }

    public void setReviewStars(String reviewStars) {
        this.reviewStars = reviewStars;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }
}
