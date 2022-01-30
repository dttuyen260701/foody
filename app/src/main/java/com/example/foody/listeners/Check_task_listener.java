package com.example.foody.listeners;

public interface Check_task_listener {
    void onPre();
    void onEnd(boolean isSucces, boolean insert_Success);
}
