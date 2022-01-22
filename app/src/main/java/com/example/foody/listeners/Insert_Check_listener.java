package com.example.foody.listeners;

public interface Insert_Check_listener {
    void onPre();
    void onEnd(boolean isSucces, boolean insert_Success);
}
