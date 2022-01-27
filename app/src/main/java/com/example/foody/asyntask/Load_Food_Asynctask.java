package com.example.foody.asyntask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.foody.listeners.Load_Data_Listener;
import com.example.foody.models.Foods;
import com.example.foody.utils.JsonUtils;
import com.example.foody.utils.Constant_Values;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Load_Food_Asynctask extends AsyncTask<Void, String, Boolean> {
    private ArrayList<Foods> list_Foods;
    private Load_Data_Listener listener;
    private RequestBody requestBody;

    public Load_Food_Asynctask(Load_Data_Listener listener, RequestBody requestBody) {
        this.list_Foods = new ArrayList<>();
        this.listener = listener;
        this.requestBody = requestBody;
    }

    @Override
    protected void onPreExecute() {
        listener.onPre();//Hàm chuẩn bị
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String result = JsonUtils.okhttpPost(Constant_Values.URL_FOOD_API, requestBody);

            JSONArray jsonArray = new JSONArray(result);

            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            Request.Builder builder = new Request.Builder();
            Response response;

            for(int i = 0; i < jsonArray.length(); ++i){
                JSONObject object = jsonArray.getJSONObject(i);

                float rate = (float) (Math.round(object.getDouble("Rate") * 10.0) / 10.0);

                Foods foods = new Foods(object.getInt("ID_Food"), object.getString("Name_Food"), object.getString("Description_Food"),
                       (float) object.getDouble("Frice_Food") , object.getString("Link_Img_Food"),
                        object.getInt("Time_Cooking"), rate, (object.getInt("Status") == 1) ? true : false,
                        (object.getInt("Is_Available") == 1) ? true : false);
                builder.url(foods.getLink_Image_Food());
                Request request = builder.build();
                response = okHttpClient.newCall(request).execute();
                byte[] temp = response.body().bytes();
                Bitmap bitmap_img = BitmapFactory.decodeByteArray(temp, 0, temp.length);
                foods.setImage_Food(bitmap_img);
                list_Foods.add(foods);
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
        listener.onEnd(aBoolean, list_Foods);
    }
}
