package com.example.myapplication.restapi.service;


import com.example.myapplication.model.Playlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlaylistService {
    @POST("playlists")
    Call<Playlist> createPlaylist(@Body Playlist playlist, @Header("Authorization") String token);

    @GET("playlists/{id}")
    Call<Playlist> getAllPlaylist(@Path("id") int id, @Header("Authorization") String token);

    @PUT("playlists")
    Call<Playlist> modifyPlaylist(@Body Playlist playlist, @Header("Authorization") String token);

    @GET("me/playlists")
    Call<List<Playlist>> getUserPlaylists(@Header("Authorization") String token);
}
