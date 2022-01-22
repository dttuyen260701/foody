package com.example.foody.models;

public class Bill_Details {
    private int iD_Bill;
    private int iD_Food;
    private int count;
    private float price_Total;
    private float rate;
    private String reviews;

    public Bill_Details(){
        this.iD_Bill = -1;
        this.iD_Food = -1;
        this.count = -1;
        this.price_Total = -1;
        this.rate = -1;
        this.reviews = "";
    }

    public Bill_Details(int iD_Bill, int iD_Food, int count, float price_Total, float rate, String reviews) {
        this.iD_Bill = iD_Bill;
        this.iD_Food = iD_Food;
        this.count = count;
        this.price_Total = price_Total;
        this.rate = rate;
        this.reviews = reviews;
    }

    public int getiD_Bill() {
        return iD_Bill;
    }

    public void setiD_Bill(int iD_Bill) {
        this.iD_Bill = iD_Bill;
    }

    public int getiD_Food() {
        return iD_Food;
    }

    public void setiD_Food(int iD_Food) {
        this.iD_Food = iD_Food;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice_Total() {
        return price_Total;
    }

    public void setPrice_Total(float price_Total) {
        this.price_Total = price_Total;
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
        return "Bill_Details{" +
                "iD_Bill=" + iD_Bill +
                ", iD_Food=" + iD_Food +
                ", count=" + count +
                ", price_Total=" + price_Total +
                ", rate=" + rate +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
