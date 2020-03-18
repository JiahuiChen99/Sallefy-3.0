package com.example.myapplication.controller.resapi.callback;

import com.example.myapplication.model.User;
import com.example.myapplication.model.UserToken;

import java.util.List;

public interface UserCallback extends FailureCallback {

    void onLoginSuccess(UserToken userToken);
    void onLoginFailure(Throwable throwable);
    void onRegisterSuccess();
    void onRegisterFailure(Throwable throwable);
    void onUserInfoReceived(User userData);

}
