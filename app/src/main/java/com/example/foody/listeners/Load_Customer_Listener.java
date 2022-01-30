package com.example.foody.listeners;

import com.example.foody.models.Customer;
import com.example.foody.models.Foods;

import java.util.ArrayList;

public interface Load_Customer_Listener {

    void onPre();
    void onEnd(boolean isSussed, Customer customer);
}
