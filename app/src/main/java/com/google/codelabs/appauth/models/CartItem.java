package com.google.codelabs.appauth.models;

public class CartItem {
    private String name;
    private String description;
    private String cost;
    private String amount;
    private int imageUrl;

    public CartItem() {
    }

    public CartItem(String name, String description, String cost, String amount, int imageUrl) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.amount = amount;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }


}
