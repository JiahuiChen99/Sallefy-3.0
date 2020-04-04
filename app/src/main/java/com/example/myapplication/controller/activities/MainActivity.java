package com.example.myapplication.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.UserCallback;
import com.example.myapplication.restapi.manager.UserManager;
import com.example.myapplication.utils.Sesion;

public class MainActivity extends AppCompatActivity implements UserCallback {

    private EditText etUsername;
    private EditText etPassword;
    private Button bLogin;
    private Button bRegister;

    @Override
    public void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews () {

        etUsername = (EditText) findViewById(R.id.main_username);
        etPassword = (EditText) findViewById(R.id.main_password);
        bLogin = (Button) findViewById(R.id.main_logIn_button);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });

        bRegister = (Button) findViewById(R.id.main_register_button);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void doLogin(String username, String userpassword) {
        UserManager.getInstance(getApplicationContext())
                .loginAttempt(username, userpassword, MainActivity.this);
    }


    @Override
    public void onLoginSuccess(UserToken userToken) {
        Sesion.getInstance(getApplicationContext())
                .setUserToken(userToken);
        Intent intent = new Intent(getApplicationContext(), PaginaPrincipalActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onLoginFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Login failed " + throwable.getMessage(), Toast.LENGTH_LONG).show();
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

    }

    @Override
    public void onAccountFailure(Throwable throwable) {

    }

}









