package com.example.foody.models;

public class Customer {
    private int iD_Cus;
    private String gmail;
    private String password;
    private String name_Cus;
    private String phone;

    public Customer(int iD_Cus, String gmail, String password, String name_Cus, String phone) {
        this.iD_Cus = iD_Cus;
        this.gmail = gmail;
        this.password = password;
        this.name_Cus = name_Cus;
        this.phone = phone;
    }

    public Customer() {}

    public int getiD_Cus() {
        return iD_Cus;
    }

    public void setiD_Cus(int iD_Cus) {
        this.iD_Cus = iD_Cus;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName_Cus() {
        return name_Cus;
    }

    public void setName_Cus(String name_Cus) {
        this.name_Cus = name_Cus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "iD_Cus=" + iD_Cus +
                ", gmail='" + gmail + '\'' +
                ", password='" + password + '\'' +
                ", name_Cus='" + name_Cus + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
