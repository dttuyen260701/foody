package com.example.foody.listeners;

import android.graphics.Bitmap;

import java.util.ArrayList;

import javax.xml.transform.Result;

public interface Load_Img_Listener {
    void onPre();
    void onEnd(boolean isSuccess, ArrayList<Bitmap> list_result);
}
