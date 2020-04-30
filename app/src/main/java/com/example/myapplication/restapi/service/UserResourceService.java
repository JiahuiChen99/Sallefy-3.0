package com.example.myapplication.restapi.service;

import com.example.myapplication.model.Track;
import com.example.myapplication.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserResourceService {

    @GET("users?popular=true")
    Call<List<User>> getPopularUsers(@Header("Authorization") String token);

    @GET("me/followings")
    Call<List<User>> getFollowingArtists(@Header("Authorization") String token);

    @GET("users/{login}/tracks?popular=true&size=5")
    Call<List<Track>> getFollowingArtistsTopTracks(@Path("login") String artistLogin, @Header("Authorization") String token);

    @GET("users/{login}/tracks")
    Call<List<Track>> getSpecificArtistSongs(@Path("login") String artistLogin, @Header("Authorization") String token);

    @GET("me/followers")
    Call<List<User>> getUserFollowers(@Header("Authorization") String token);

    @GET("me/followings")
    Call<List<User>> getUserFollowing(@Header("Authorization") String token);
}
