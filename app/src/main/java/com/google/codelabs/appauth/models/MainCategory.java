package com.google.codelabs.appauth.models;

public class MainCategory {
    private int color=-1;
    private int icon=1;
    private String label;

    public MainCategory(int color, int icon, String label) {
        this.color = color;
        this.icon = icon;
        this.label = label;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
