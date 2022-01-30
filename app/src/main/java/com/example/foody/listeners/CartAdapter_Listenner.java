package com.example.foody.listeners;

import com.example.foody.models.Bill_Details;

import java.util.ArrayList;

public interface CartAdapter_Listenner {
    //checkEMty_cart khi trừ xuống 0;
    //hàm có thể dùng tính tổng hoặc back về
    void TinhTong(float Tong, boolean check_EmptyCart);
}
