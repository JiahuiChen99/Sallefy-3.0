package com.example.myapplication.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.UserCallback;
import com.example.myapplication.restapi.manager.UserManager;
import com.example.myapplication.utils.Sesion;

public class OpcionesActivity extends AppCompatActivity implements UserCallback {

    private Button bFollow;
    private Button bPlay;
    private Button bAddSong;
    private Button bLike;

    @Override
    public void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);

        UserManager.getInstance(getApplicationContext())
                .accountAttempt(OpcionesActivity.this);
    }

    private void initViews() {
        bFollow = (Button) findViewById(R.id.opciones_1);
        bPlay = (Button) findViewById(R.id.opciones_2);
        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BuscarPlaylistActivity.class);
                startActivity(intent);
            }
        });
        bAddSong = (Button) findViewById(R.id.opciones_3);
        bAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddSongActivity.class);
                startActivity(intent);
            }
        });
        bLike = (Button) findViewById(R.id.opciones_4);
    }

    @Override
    public void onLoginSuccess(UserToken userToken) {
    }

    @Override
    public void onLoginFailure(Throwable throwable) {
    }

    @Override
    public void onRegisterSuccess() {

    }

    @Override
    public void onRegisterFailure(Throwable throwable) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onAccountSucces(User user) {
        Sesion.getInstance(getApplicationContext()).setUser(user);
        setContentView(R.layout.activity_opciones);
        initViews();
    }

    @Override
    public void onAccountFailure(Throwable throwable) {

    }

}
