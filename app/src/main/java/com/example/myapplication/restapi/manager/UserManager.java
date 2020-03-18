package com.example.myapplication.restapi.manager;

import android.content.Context;
import android.util.Log;


import com.example.myapplication.model.UserLogin;
import com.example.myapplication.model.UserRegister;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.UserCallback;
import com.example.myapplication.restapi.service.UserService;
import com.example.myapplication.restapi.service.UserTokenService;
import com.example.myapplication.utils.Variables;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserManager {

    private static final String TAG = "UserManager";

    private static UserManager UserManager;
    private Retrofit retrofit;
    private Context context;

    private UserService service;
    private UserTokenService tokenService;


    public static UserManager getInstance(Context context) {
        if (UserManager == null) {
            UserManager = new UserManager(context);
        }
        return UserManager;
    }

    private UserManager(Context cntxt) {
        context = cntxt;
        retrofit = new Retrofit.Builder()
                .baseUrl(Variables.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(UserService.class);
        tokenService = retrofit.create(UserTokenService.class);
    }


    /********************   LOGIN    ********************/
    public synchronized void loginAttempt (String username, String password, final UserCallback userCallback) {

        Call<UserToken> call = tokenService.loginUser(new UserLogin(username, password, true));

        call.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {

                int code = response.code();
                UserToken userToken = response.body();

                if (response.isSuccessful()) {
                    userCallback.onLoginSuccess(userToken);
                } else {
                    Log.d(TAG, "Error: " + code);
                    try {
                        userCallback.onLoginFailure(new Throwable("ERROR " + code + ", " + response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());
                userCallback.onFailure(t);
            }
        });
    }

    /********************   REGISTRATION    ********************/
    public synchronized void registerAttempt (String email, String username, String password, final UserCallback userCallback) {

        Call<ResponseBody> call = service.registerUser(new UserRegister(email, username, password));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    userCallback.onRegisterSuccess();
                } else {
                    try {
                        userCallback.onRegisterFailure(new Throwable("ERROR " + code + ", " + response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                userCallback.onFailure(t);
            }
        });
    }

}
