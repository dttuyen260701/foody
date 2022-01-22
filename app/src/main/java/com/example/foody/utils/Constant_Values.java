package com.example.foody.utils;

public class Constant_Values {

    private static int ID_CUS = 3;// mặc định là -1
    private static String URL_SERVER = "http://192.168.1.6/foody/" ;
    //private static String URL_SERVER = "https://tuyen2607.000webhostapp.com/";//link SERVER

    public static String URL_FOOD_API = URL_SERVER + "api_food.php";
    public static String URL_CUSTOMER_API = URL_SERVER + "api_customer.php";
    public static String URL_FAVORITE_API = URL_SERVER + "api_favorite.php";
    public static String URL_BILL_API = URL_SERVER + "api_bill.php";
    public static String URL_BILL_DETAIL_API = URL_SERVER + "api_bill_detail.php";

    public static String URL_IMG_SLIDE1 = "https://d1ralsognjng37.cloudfront.net/e61274a4-2f83-4e03-adb0-2253db148f5d.jpeg";
    public static String URL_IMG_SLIDE2 = "https://c.ndtvimg.com/2020-08/2dv9fku_kfc-covid_625x300_25_August_20.jpg";

    public static Float Shipping_Fee_Per_1Km = 0.2f;

    public static int getIdCus() {
        return ID_CUS;
    }

    public static void setIdCus(int idCus) {
        ID_CUS = idCus;
    }
}
