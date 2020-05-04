package com.example.myapplication.utils;


import android.content.Context;

import com.example.myapplication.model.User;
import com.example.myapplication.model.UserRegister;
import com.example.myapplication.model.UserToken;

public class Sesion {

    public static Sesion sesionActual;
    private static Object mutex = new Object();

    private Context mContext;

    private UserRegister usuarioRegistrado;
    private User user;
    private UserToken token;

    public static Sesion getInstance(Context context) {
        Sesion result = sesionActual;
        if (result == null) {
            synchronized (mutex) {
                result = sesionActual;
                if (result == null)
                    sesionActual = result = new Sesion();
            }
        }
        return result;
    }

    private Sesion() {}

    public Sesion(Context context) {
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
