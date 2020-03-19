package com.example.myapplication.controller.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class OpcionesActivity extends AppCompatActivity {

    private Button bFollow;
    private Button bPlay;
    private Button bAddSong;
    private Button bLike;

    @Override
    public void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_opciones);
        initViews();
    }

    private void initViews() {
        bFollow = (Button) findViewById(R.id.opciones_1);
        bPlay = (Button) findViewById(R.id.opciones_2);
        bAddSong = (Button) findViewById(R.id.opciones_3);
        bLike = (Button) findViewById(R.id.opciones_4);
    }

}
