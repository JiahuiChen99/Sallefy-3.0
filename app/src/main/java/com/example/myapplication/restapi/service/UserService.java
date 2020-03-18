package com.example.myapplication.restapi.service;


import com.example.myapplication.model.UserRegister;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("register")
    Call<ResponseBody> registerUser(@Body UserRegister user);

}
