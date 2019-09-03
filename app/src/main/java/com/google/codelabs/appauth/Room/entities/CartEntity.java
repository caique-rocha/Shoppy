package com.google.codelabs.appauth.Room.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class CartEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id")
   public String mCartId;

    @ColumnInfo(name = "name")
    public String mName;
    @ColumnInfo(name = "category")
    public String mCategory;
    @ColumnInfo(name="price")
    public String mPrice;
    @ColumnInfo(name="image")
    public String mImage;
//    public CartEntity(@NonNull String mCartId) {
//        this.mCartId = mCartId;
//    }

    public CartEntity(@NonNull String mCartId, String mName, String mCategory, String mPrice, String mImage) {
        this.mCartId = mCartId;
        this.mName = mName;
        this.mCategory = mCategory;
        this.mPrice = mPrice;
        this.mImage = mImage;
    }

    @NonNull
    public String getmCartId() {
        return mCartId;
    }

    public void setmCartId(@NonNull String mCartId) {
        this.mCartId = mCartId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }
}
