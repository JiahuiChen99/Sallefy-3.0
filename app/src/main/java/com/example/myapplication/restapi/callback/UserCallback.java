package com.example.myapplication.restapi.callback;

import com.example.myapplication.model.User;
import com.example.myapplication.model.UserToken;

public interface UserCallback extends FailureCallback {

    void onLoginSuccess(UserToken userToken);
    void onLoginFailure(Throwable throwable);
    void onRegisterSuccess();
    void onRegisterFailure(Throwable throwable);
    void onAccountSucces(User user);
    void onAccountFailure(Throwable throwable);

}
