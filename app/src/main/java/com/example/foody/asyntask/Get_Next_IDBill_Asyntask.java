package com.example.foody.asyntask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.foody.listeners.Get_Next_IDBill_Listener;
import com.example.foody.models.Reviews;
import com.example.foody.utils.Constant_Values;
import com.example.foody.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.RequestBody;

public class Get_Next_IDBill_Asyntask extends AsyncTask<Void, String, Boolean> {
    private int next_ID;
    private Get_Next_IDBill_Listener listener;
    private RequestBody requestBody;

    public Get_Next_IDBill_Asyntask(Get_Next_IDBill_Listener listener, RequestBody requestBody) {
        this.listener = listener;
        this.requestBody = requestBody;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String result = JsonUtils.okhttpPost(Constant_Values.URL_BILL_API, requestBody);

            JSONObject object = new JSONObject(result);
            next_ID = object.getInt("Next_ID");

            return true;
        } catch (Exception e) {
            Log.e("AAA", e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.onEnd(next_ID);
    }
}
