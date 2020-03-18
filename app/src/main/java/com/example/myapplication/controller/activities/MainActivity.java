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
import com.example.myapplication.controller.resapi.callback.UserCallback;
import com.example.myapplication.controller.resapi.manager.UserManager;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.utils.Session;

public class MainActivity extends AppCompatActivity implements UserCallback {

    private EditText etUsername;
    private EditText etPassword;
    private Button bLogin;
    private Button bRegister;
    private TextView tvRecordarContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
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
        tvRecordarContra = (TextView) findViewById(R.id.main_recordar_contra);
    }

    private void doLogin(String username, String userpassword) {
        UserManager.getInstance(getApplicationContext())
                .loginAttempt(username, userpassword, MainActivity.this);
    }


    @Override
    public void onLoginSuccess(UserToken userToken) {
        Session.getInstance(getApplicationContext())
                .setUserToken(userToken);
        Intent intent = new Intent(getApplicationContext(), PaginaPrincipalActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
    public void onUserInfoReceived(User userData) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

}
