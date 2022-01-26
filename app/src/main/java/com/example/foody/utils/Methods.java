package com.example.foody.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.foody.models.Customer;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Methods {
    private Context context;

    public Methods(Context context) {
        this.context = context;
    }


    //Kiem Tra mang
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public RequestBody getRequestBody(String method_name, Bundle bundle){
        JsonObject postObj = new JsonObject();//tạo đối tượng json objet để gửi đi

        JsonObject tempObj = new JsonObject();//doi tuong JSon temp luu giu lieu
        String key_2 = "";
        switch (method_name){
            case "method_get_restaurant_data":
                postObj.addProperty("ID_Res", String.valueOf(bundle.getInt("ID_Res")));
                break;
            case "method_check_mail_exist":
            case "method_get_customer_data":
                postObj.addProperty("Gmail", bundle.getString("Gmail"));
                break;
            case "method_update_customer":
            case "method_insert_customer"://gửi thêm Obj Cus
                key_2 = "customer";// gửi Object tên customer
                tempObj.addProperty("ID_Cus", String.valueOf(bundle.get("ID_Cus")));
                tempObj.addProperty("Gmail", bundle.getString("Gmail"));
                tempObj.addProperty("Password", bundle.getString("Password"));
                tempObj.addProperty("Name_Cus", bundle.getString("Name_Cus"));
                tempObj.addProperty("Phone", bundle.getString("Phone"));
                break;
            case "method_get_customer_data_byID":
            case "method_get_favorite_data"://Gui them ID
            case "method_get_bill_data":
                postObj.addProperty("ID_Cus", String.valueOf(bundle.getInt("ID_Cus")));
                break;
            case "method_del_favorite":
            case "method_insert_favorite": //gui doi tuong favorite
                key_2 = "favorite";
                tempObj.addProperty("ID_Food", String.valueOf(bundle.getInt("ID_Food")));
                tempObj.addProperty("ID_Cus", String.valueOf(bundle.getInt("ID_Cus")));
                break;
            case "method_insert_bill"://Gửi đối tượng bill
                key_2 = "bill";
                tempObj.addProperty("ID_Bill", String.valueOf(bundle.getInt("ID_Bill")));
                tempObj.addProperty("ID_Cus", String.valueOf(bundle.getInt("ID_Cus")));
                tempObj.addProperty("Total", String.valueOf(bundle.getFloat("Total")));
                tempObj.addProperty("Time", bundle.getString("Time"));
                tempObj.addProperty("Address", bundle.getString("Address"));
                tempObj.addProperty("Shipping_fee", String.valueOf(bundle.getFloat("Shipping_fee")));
                tempObj.addProperty("done", bundle.getBoolean("done"));
                break;
            case "method_update_bill":
            case "method_get_bill_detail_data"://gửi ID bill
                postObj.addProperty("ID_Bill", String.valueOf(bundle.getInt("ID_Bill")));
                break;
            case "method_get_update_food_data":
            case "method_get_reviews_data":
                postObj.addProperty("ID_Food", String.valueOf(bundle.getInt("ID_Food")));
                break;
            case "method_update_review":
            case "method_insert_bill_detail":// gửi bill detail
                key_2 = "bill_detail";
                tempObj.addProperty("ID_Bill", String.valueOf(bundle.getInt("ID_Bill")));
                tempObj.addProperty("ID_Food", String.valueOf(bundle.getInt("ID_Food")));
                tempObj.addProperty("Count", String.valueOf(bundle.getInt("Count")));
                tempObj.addProperty("Price_Total", String.valueOf(bundle.getFloat("Price_Total")));
                tempObj.addProperty("Rate", String.valueOf(bundle.getFloat("Rate")));
                tempObj.addProperty("Reviews", bundle.getString("Reviews"));
                break;
            default:
                break;
        }

        postObj.addProperty("method_name", method_name);//gán chức năng

        String post_data = postObj.toString(); //convert từ Json sang String
        String temp_data = tempObj.toString();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM); // xác định kiểu chuyển trong body là form
        builder.addFormDataPart("data", post_data); // gửi json object đi
        if(key_2.length() > 0)
            builder.addFormDataPart(key_2, temp_data); // gửi json object t2 trong điều kiện cần

        return builder.build();
    }
}
