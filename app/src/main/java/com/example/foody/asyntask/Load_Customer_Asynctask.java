package com.example.foody.asyntask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.foody.listeners.Load_Customer_Listener;
import com.example.foody.models.Customer;
import com.example.foody.utils.JsonUtils;
import com.example.foody.utils.Constant_Values;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.RequestBody;

public class Load_Customer_Asynctask extends AsyncTask<Void, String, Boolean> {
    private Customer customer;
    private Load_Customer_Listener listener;
    private RequestBody requestBody;

    public Load_Customer_Asynctask(Load_Customer_Listener listener, RequestBody requestBody) {
        this.listener = listener;
        this.requestBody = requestBody;
        customer = new Customer();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPre();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String result = JsonUtils.okhttpPost(Constant_Values.URL_CUSTOMER_API, requestBody);

            JSONArray jsonArray = new JSONArray(result);
            if(jsonArray.length() == 0)
                customer.setiD_Cus(-1);
            else {
                JSONObject object = jsonArray.getJSONObject(0);
                customer = new Customer(object.getInt("ID_Cus"), object.getString("Gmail"),
                        object.getString("Password"), object.getString("Name_Cus"), object.getString("Phone"));
            }
            return true;
        } catch (Exception e){
            Log.e("AAA", e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.onEnd(aBoolean, customer);
    }
}
