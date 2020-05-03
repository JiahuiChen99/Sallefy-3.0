package com.example.myapplication.restapi.callback;


import com.example.myapplication.model.SearchResult;

public interface SearchCallback {
    void onInfoReceived(SearchResult output);
    void onNoInfo(Throwable throwable);
}
