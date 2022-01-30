package com.example.foody.listeners;

import com.example.foody.models.Reviews;

import java.util.ArrayList;

public interface Load_Reviews_Listener {
    void onPre();
    void onEnd(boolean isSussec, ArrayList<Reviews> list_result);
}
