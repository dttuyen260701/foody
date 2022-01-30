package com.example.foody.models;

import java.util.Date;

public class Reviews {
    private String name_Cus;
    private Date time;
    private float rate;
    private String reviews;

    public Reviews(String name_Cus, Date time, float rate, String reviews) {
        this.name_Cus = name_Cus;
        this.time = time;
        this.rate = rate;
        this.reviews = reviews;
    }

    public String getName_Cus() {
        return name_Cus;
    }

    public void setName_Cus(String name_Cus) {
        this.name_Cus = name_Cus;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "name_Cus='" + name_Cus + '\'' +
                ", time=" + time +
                ", rate=" + rate +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
