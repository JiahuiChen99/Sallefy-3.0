package com.example.myapplication.controller.resapi.service;


import com.example.myapplication.model.UserLogin;
import com.example.myapplication.model.UserToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserTokenService {

    @POST("authenticate")
    Call<UserToken> loginUser(@Body UserLogin login);

}
