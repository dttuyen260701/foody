package com.example.foody.listeners;

import com.example.foody.models.Foods;

import java.util.ArrayList;

public interface Load_Data_Listener {
    void onPre();
    void onEnd(boolean isSussed, ArrayList<Foods> arrayList);
}
