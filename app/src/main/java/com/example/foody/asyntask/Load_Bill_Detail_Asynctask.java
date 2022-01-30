package com.example.foody.asyntask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.foody.listeners.Load_Bill_Detail_Listener;
import com.example.foody.models.Bill_Details;
import com.example.foody.utils.JsonUtils;
import com.example.foody.utils.Constant_Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;

public class Load_Bill_Detail_Asynctask extends AsyncTask<Void, String, Boolean> {
    private ArrayList<Bill_Details> arrayList_result;
    private Load_Bill_Detail_Listener listener;
    private RequestBody requestBody;

    public Load_Bill_Detail_Asynctask(Load_Bill_Detail_Listener listener, RequestBody requestBody) {
        this.listener = listener;
        this.requestBody = requestBody;
        arrayList_result = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPre();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String result = JsonUtils.okhttpPost(Constant_Values.URL_BILL_DETAIL_API, requestBody);

            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); ++i){
                JSONObject object = jsonArray.getJSONObject(i);
                boolean check_review = (object.getDouble("Rate") > 0) ? true : false;
                Bill_Details bill_details = new Bill_Details(object.getInt("ID_Bill"), object.getInt("ID_Food"),
                        object.getInt("Count"), (float) object.getDouble("Price_Total"),
                        (float) object.getDouble("Rate"), object.getString("Reviews"), check_review);
                arrayList_result.add(bill_details);
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
        listener.onEnd(aBoolean, arrayList_result);
    }
}
