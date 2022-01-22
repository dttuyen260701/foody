package com.example.foody.models;

public class Reviews {
    private int iD_Food;
    private float rate;
    private String reviews;

    public Reviews(int iD_Food, float rate, String reviews) {
        this.iD_Food = iD_Food;
        this.rate = rate;
        this.reviews = reviews;
    }

    public int getiD_Food() {
        return iD_Food;
    }

    public void setiD_Food(int iD_Food) {
        this.iD_Food = iD_Food;
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
                "iD_Food=" + iD_Food +
                ", rate=" + rate +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
