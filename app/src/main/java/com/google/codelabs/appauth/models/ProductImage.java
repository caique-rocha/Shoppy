package com.google.codelabs.appauth.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ProductImage {
    @SerializedName("url")
    @Expose
    private String ProductimageUrl;

    public ProductImage(String productimageUrl) {
        ProductimageUrl = productimageUrl;
    }

    public String getProductimageUrl() {
        return ProductimageUrl;
    }

    public void setProductimageUrl(String productimageUrl) {
        ProductimageUrl = productimageUrl;
    }
}
