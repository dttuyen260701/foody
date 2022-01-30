package com.example.foody.asyntask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.foody.listeners.Load_Favorite_Listener;
import com.example.foody.models.Favorite;
import com.example.foody.utils.JsonUtils;
import com.example.foody.utils.Constant_Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;

public class Load_Favorite_Asynctask extends AsyncTask<Void, String, Boolean> {
    private ArrayList<Favorite> list_Favorite;
    private Load_Favorite_Listener listener;
    private RequestBody requestBody;

    public Load_Favorite_Asynctask(Load_Favorite_Listener listener, RequestBody requestBody) {
        this.listener = listener;
        this.requestBody = requestBody;
        list_Favorite = new ArrayList<>();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String result = JsonUtils.okhttpPost(Constant_Values.URL_FAVORITE_API, requestBody);

            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); ++i){
                JSONObject object = jsonArray.getJSONObject(i);
                Favorite favorite = new Favorite(object.getInt("ID_Food"), object.getInt("ID_Cus"));
                list_Favorite.add(favorite);
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
        listener.onEnd(aBoolean, list_Favorite);
    }
}
