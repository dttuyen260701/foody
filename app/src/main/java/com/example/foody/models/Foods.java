package com.example.foody.models;

import android.graphics.Bitmap;

public class Foods {
    private int iD_Food;
    private String name_Food;
    private String description_Food;
    private float price_Food;
    private String link_Image_Food;
    private Bitmap image_Food;
    private int time_Cooking;
    private float rate;
    private boolean status;
    private boolean Available;
    private boolean Favorite;

    public Foods(){}

    public Foods(int iD_Food, String name_Food, String description_Food, float price_Food, String link_Image_Food, int time_Cooking, float rate, boolean status, boolean is_Available) {
        this.iD_Food = iD_Food;
        this.name_Food = name_Food;
        this.description_Food = description_Food;
        this.price_Food = price_Food;
        this.link_Image_Food = link_Image_Food;
        this.time_Cooking = time_Cooking;
        this.rate = rate;
        this.status = status;
        this.Available = is_Available;
        this.Favorite = false;
    }

    public int getiD_Food() {
        return iD_Food;
    }

    public void setiD_Food(int iD_Food) {
        this.iD_Food = iD_Food;
    }

    public String getName_Food() {
        return name_Food;
    }

    public void setName_Food(String name_Food) {
        this.name_Food = name_Food;
    }

    public String getDescription_Food() {
        return description_Food;
    }

    public void setDescription_Food(String description_Food) {
        this.description_Food = description_Food;
    }

    public float getPrice_Food() {
        return price_Food;
    }

    public void setPrice_Food(float price_Food) {
        this.price_Food = price_Food;
    }

    public String getLink_Image_Food() {
        return link_Image_Food;
    }

    public void setLink_Image_Food(String link_Image_Food) {
        this.link_Image_Food = link_Image_Food;
    }

    public Bitmap getImage_Food() {
        return image_Food;
    }

    public void setImage_Food(Bitmap image_Food) {
        this.image_Food = image_Food;
    }

    public int getTime_Cooking() {
        return time_Cooking;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setTime_Cooking(int time_Cooking) {
        this.time_Cooking = time_Cooking;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean is_Available() {
        return Available;
    }

    public void set_Available(boolean is_Available) {
        this.Available = is_Available;
    }

    public boolean is_Favorite() {
        return Favorite;
    }

    public void set_Favorite(boolean is_Favorite) {
        this.Favorite = is_Favorite;
    }

    @Override
    public String toString() {
        return "Foods{" +
                "iD_Food=" + iD_Food +
                ", name_Food='" + name_Food + '\'' +
                ", description_Food='" + description_Food + '\'' +
                ", price_Food=" + price_Food +
                ", link_Image_Food='" + link_Image_Food + '\'' +
                ", time_Cooking=" + time_Cooking +
                ", status=" + status +
                ", Available=" + Available +
                ", Favorite=" + Favorite +
                '}';
    }
}
