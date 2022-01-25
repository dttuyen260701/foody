package com.example.foody.asyntask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.foody.listeners.Check_task_listener;
import com.example.foody.models.Bill;
import com.example.foody.utils.Constant_Values;
import com.example.foody.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import okhttp3.RequestBody;

public class Get_Restaurant_Inf_Asynctask extends AsyncTask<Void, String, Boolean> {
    private RequestBody requestBody;
    private Check_task_listener listener;

    public Get_Restaurant_Inf_Asynctask(RequestBody requestBody, Check_task_listener listener) {
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPre();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String result = JsonUtils.okhttpPost(Constant_Values.URL_RESTAURANT_API, requestBody);

            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); ++i){
                JSONObject object = jsonArray.getJSONObject(i);
                Constant_Values.Shipping_Fee_Per_1Km = (float) object.getDouble("Shipping_Fee_1km");
                Constant_Values.URL_IMG_SLIDE1 = object.getString("Link_Slide_1");
                Constant_Values.URL_IMG_SLIDE2 = object.getString("Link_Slide_2");
                Constant_Values.PHONENUMBER = object.getString("Phone_number");
                Constant_Values.URL_TERM_OF_SERVICE = object.getString("Link_Term");
                Constant_Values.URL_FORGET_PASS = object.getString("Link_Forger_Pass");
            }
            return true;
        } catch (JSONException e) {
            Log.e("AAA", e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.onEnd(aBoolean, aBoolean);
    }
}
