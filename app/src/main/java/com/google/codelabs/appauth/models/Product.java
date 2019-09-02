package com.google.codelabs.appauth.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("_id")
    @Expose
    private
   String mProductId;
    @SerializedName("name")
    @Expose
    private
    String productName;

    @SerializedName("price")
    @Expose
    private
    String productPrice;

    @SerializedName("image")
    @Expose
    private
    String productImage;

    @SerializedName("color")
    @Expose
    private
    String productColor;

    @SerializedName("category")
    @Expose
    private
    String productCategory;

    @SerializedName("size")
    @Expose
    private
    String productSize;


    public Product() {
    }

    public Product(String mProductId, String productName, String productPrice, String productImage, String productColor, String productCategory, String productSize) {
        this.mProductId = mProductId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productColor = productColor;
        this.productCategory = productCategory;
        this.productSize = productSize;
    }

    public String getmProductId() {
        return mProductId;
    }

    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }
}
