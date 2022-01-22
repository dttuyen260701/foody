package com.example.foody.asyntask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.foody.listeners.Insert_Check_listener;
import com.example.foody.utils.JsonUtils;

import org.json.JSONObject;

import okhttp3.RequestBody;

public class Task_InsertOrDel_Asynctask extends AsyncTask<Void, String, Boolean> {
    private boolean insert_Sussec;
    private Insert_Check_listener listener;
    private RequestBody requestBody;
    private String URL_API = "";

    public Task_InsertOrDel_Asynctask(Insert_Check_listener listener, RequestBody requestBody, String uRL_API) {
        this.listener = listener;
        this.requestBody = requestBody;
        this.URL_API = uRL_API;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPre();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String result = JsonUtils.okhttpPost(URL_API, requestBody);

            JSONObject object = new JSONObject(result);
            insert_Sussec = object.getBoolean("value");

            return true;
        } catch (Exception e){
            Log.e("AAA", e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.onEnd(aBoolean, insert_Sussec);
    }
}
