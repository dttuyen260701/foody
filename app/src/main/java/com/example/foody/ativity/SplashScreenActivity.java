package com.example.foody.ativity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.foody.R;
import com.example.foody.asyntask.Get_Restaurant_Inf_Asynctask;
import com.example.foody.listeners.Check_task_listener;
import com.example.foody.utils.Methods;

import okhttp3.RequestBody;

public class SplashScreenActivity extends AppCompatActivity {
    private Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        methods = new Methods(this);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);

                Bundle bundle = new Bundle();
                bundle.putInt("ID_Res", 1);
                RequestBody requestBody = methods.getRequestBody("method_get_restaurant_data", bundle);
                Get_Restaurant_Inf_Asynctask asynctask = new Get_Restaurant_Inf_Asynctask(requestBody, new Check_task_listener() {
                    @Override
                    public void onPre() {

                    }

                    @Override
                    public void onEnd(boolean isSucces, boolean insert_Success) {
                        // close this activity
                        finish();
                    }
                });
                asynctask.execute();
            }
        }, 1000);
    }
}