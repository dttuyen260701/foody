package com.example.foody.asyntask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.foody.listeners.Load_Img_Listener;
import com.example.foody.models.Foods;
import com.example.foody.utils.Constant_Values;
import com.example.foody.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Load_Image_Asynctask extends AsyncTask<Void, String, Boolean> {
    private ArrayList<String> link_Img;
    private ArrayList<Bitmap> list_Bitmap_Img;
    private Load_Img_Listener listener;

    public Load_Image_Asynctask(ArrayList<String> link_Img, Load_Img_Listener listener) {
        this.link_Img = link_Img;
        this.listener = listener;
        list_Bitmap_Img = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPre();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            Request.Builder builder = new Request.Builder();
            Response response;
            for (String link : link_Img){
                URL url1 = new URL(link);
                builder.url(url1);
                Request request = builder.build();
                response = okHttpClient.newCall(request).execute();
                byte[] temp = response.body().bytes();
                Bitmap bitmap_img = BitmapFactory.decodeByteArray(temp, 0, temp.length);
                list_Bitmap_Img.add(bitmap_img);
            }
            return true;
        } catch (Exception e) {
            Log.e("AAA", e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.onEnd(aBoolean, list_Bitmap_Img);
    }
}
