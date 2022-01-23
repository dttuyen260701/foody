package com.example.foody.asyntask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.foody.listeners.Load_Bill_Listener;
import com.example.foody.models.Bill;
import com.example.foody.utils.JsonUtils;
import com.example.foody.utils.Constant_Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.RequestBody;

public class Load_Bill_Asynctask extends AsyncTask<Void, String, Boolean> {
    private ArrayList<Bill> billArrayList;
    private Load_Bill_Listener listener;
    private RequestBody requestBody;

    public Load_Bill_Asynctask(Load_Bill_Listener listener, RequestBody requestBody) {
        this.listener = listener;
        this.requestBody = requestBody;
        billArrayList = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPre();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String result = JsonUtils.okhttpPost(Constant_Values.URL_BILL_API, requestBody);

            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); ++i){
                JSONObject object = jsonArray.getJSONObject(i);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                Bill bill = new Bill(object.getInt("ID_Bill"), object.getInt("ID_Cus"),
                        (float) object.getDouble("Total"), dateFormat.parse(object.getString("Time")),
                        object.getString("Address"), (object.getInt("done") == 1) ? true : false);
                billArrayList.add(bill);
            }
            return true;
        } catch (JSONException | ParseException e) {
            Log.e("AAA", e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.onEnd(aBoolean, billArrayList);
    }
}
