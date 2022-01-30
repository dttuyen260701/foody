package com.example.foody.listeners;

import com.example.foody.models.Favorite;

import java.util.ArrayList;

public interface Load_Favorite_Listener {
    void onEnd(boolean isDone, ArrayList<Favorite> list_Favorites);
}
