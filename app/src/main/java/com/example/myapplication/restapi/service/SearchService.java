package com.example.myapplication.restapi.service;


import com.example.myapplication.model.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchService {
    @GET("search?")
    Call<SearchResult> search(@Query("keyword") String input, @Header("Authorization") String token);
}
