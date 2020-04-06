package com.example.myapplication.restapi.service;

import com.example.myapplication.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserResourceService {

    @GET("users?popular=true")
    Call<List<User>> getPopularUsers(@Header("Authorization") String token);
}
