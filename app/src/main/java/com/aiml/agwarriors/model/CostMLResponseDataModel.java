package com.aiml.agwarriors.model;

public class CostMLResponseDataModel {
    private String Date;
    private String price;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public float getPrice() {
        return Float.parseFloat(price);
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
