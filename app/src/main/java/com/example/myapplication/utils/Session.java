package com.example.myapplication.utils;

import android.content.Context;

import com.example.myapplication.model.User;
import com.example.myapplication.model.UserRegister;
import com.example.myapplication.model.UserToken;

public class Session {

    public static Session sesionActual;
    private static Object mutex = new Object();

    private Context mContext;

    private UserRegister usuarioRegistrado;
    private User user;
    private UserToken token;

    public static Session getInstance(Context context) {
        Session result = sesionActual;
        if (result == null) {
            synchronized (mutex) {
                result = sesionActual;
                if (result == null)
                    sesionActual = result = new Session();
            }
        }
        return result;
    }

    private Session() {}

    public Session(Context context) {
        this.mContext = context;
        this.usuarioRegistrado = null;
        this.token = null;
    }

    public void resetValues() {
        usuarioRegistrado = null;
        token = null;
    }

    public UserRegister getUserRegister() {
        return usuarioRegistrado;
    }

    public void setUserRegister(UserRegister userRegister) {
        usuarioRegistrado = userRegister;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserToken getUserToken() {
        return token;
    }

    public void setUserToken(UserToken userToken) {
        this.token = userToken;
    }
}
