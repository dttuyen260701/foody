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
    private float shipping_fee;
    //true la da hoan thanh, false là chưa nhận
    private boolean done;

    public Bill(){}

    public Bill(int iD_Bill, int iD_Cus, float total, Date time, String address, float shipping_fee, boolean done) {
        this.iD_Bill = iD_Bill;
        this.iD_Cus = iD_Cus;
        this.total = total;
        this.time = time;
        this.address = address;
        this.shipping_fee = shipping_fee;
        this.done = done;
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

    public float getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(float shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "iD_Bill=" + iD_Bill +
                ", iD_Cus=" + iD_Cus +
                ", total=" + total +
                ", time=" + time +
                ", address='" + address + '\'' +
                ", distance=" + shipping_fee +
                ", done=" + done +
                '}';
    }
}
