package com.example.foody.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bill {
    private int iD_Bill;
    private int iD_Cus;
    private float total;
    private Date time;
    private String address;

    public Bill(){}

    public Bill(int iD_Bill, int iD_Cus, float total, Date time, String address) {
        this.iD_Bill = iD_Bill;
        this.iD_Cus = iD_Cus;
        this.total = total;
        this.time = time;
        this.address = address;
    }

    public int getiD_Bill() {
        return iD_Bill;
    }

    public void setiD_Bill(int iD_Bill) {
        this.iD_Bill = iD_Bill;
    }

    public int getiD_Cus() {
        return iD_Cus;
    }

    public void setiD_Cus(int iD_Cus) {
        this.iD_Cus = iD_Cus;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return "Bill{" +
                "iD_Bill=" + iD_Bill +
                ", iD_Cus=" + iD_Cus +
                ", total=" + total +
                ", time=" + dateFormat.format(time) +
                ", address='" + address + '\'' +
                '}';
    }
}
