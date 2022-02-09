package com.example.foody.utils;

import android.content.SharedPreferences;

import com.example.foody.models.Customer;

public class Constant_Values {

    private static int ID_CUS = -1;// mặc định là -1

    private static boolean Doing_task = false;

    public static boolean isDoing_task() {
        return Doing_task;
    }

    public static void setDoing_task(boolean doing_task) {
        Doing_task = doing_task;
    }

    public static int getIdCus() {
        return ID_CUS;
    }

    public static void setIdCus(int idCus) {
        ID_CUS = idCus;
    }

    private static Customer customer = new Customer();

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        Constant_Values.customer = customer;
    }

    public static SharedPreferences save_ID_Cus;

    //private static String URL_SERVER = "http://192.168.1.6/foody/" ;
    private static String URL_SERVER = "https://tuyen2607.000webhostapp.com/";//link SERVER

    public static String URL_FOOD_API = URL_SERVER + "api_food.php";
    public static String URL_CUSTOMER_API = URL_SERVER + "api_customer.php";
    public static String URL_FAVORITE_API = URL_SERVER + "api_favorite.php";
    public static String URL_BILL_API = URL_SERVER + "api_bill.php";
    public static String URL_BILL_DETAIL_API = URL_SERVER + "api_bill_detail.php";
    public static String URL_RESTAURANT_API = URL_SERVER + "api_restaurant.php";

    public static String URL_TERM_OF_SERVICE = "";
    public static String URL_FORGET_PASS = "";

    public static String URL_IMG_SLIDE1 = "";
    public static String URL_IMG_SLIDE2 = "";

    public static Float Shipping_Fee_Per_1Km = 0.2f;
    public static String Addres_Res = "";

    public static String PHONENUMBER = "";
}
