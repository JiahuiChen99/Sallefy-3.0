package com.example.myapplication.restapi.service;


import com.example.myapplication.model.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TrackService {

    @GET("tracks")
    Call<List<Track>> getAllTracks(@Header("Authorization") String token);

    @GET("tracks/{id}")
    Call<Track> getSpecificTrack(@Path("id") int id, @Header("Authorization") String token);

    @GET("tracks?recent=true&size=10")
    Call<List<Track>> getRecentTracks(@Header("Authorization") String token);

    @GET("tracks?played=true&size=20")
    Call<List<Track>> getRecommendedTracks(@Header("Authorization") String token);
}
