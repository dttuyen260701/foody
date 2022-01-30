package com.example.foody.models;

import java.io.Serializable;

public class Favorite {
    private int iD_Food;
    private int iD_Cus;

    public Favorite(){}

    public Favorite(int iD_Food, int iD_Cus) {
        this.iD_Food = iD_Food;
        this.iD_Cus = iD_Cus;
    }

    public int getiD_Food() {
        return iD_Food;
    }

    public void setiD_Food(int iD_Food) {
        this.iD_Food = iD_Food;
    }

    public int getiD_Cus() {
        return iD_Cus;
    }

    public void setiD_Cus(int iD_Cus) {
        this.iD_Cus = iD_Cus;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "iD_Food=" + iD_Food +
                ", iD_Cus=" + iD_Cus +
                '}';
    }
}
