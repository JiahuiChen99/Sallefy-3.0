package com.example.myapplication.restapi.service;

import com.example.myapplication.model.EssencialGenre;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GenreService {
    @GET("genres")
    Call<List<EssencialGenre>> getGenres(@Header("Authorization") String token);
}
