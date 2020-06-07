package com.example.myapplication.controller.activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;

import java.util.List;

public class AddPlaylistActivity extends AppCompatActivity implements PlaylistCallback {

    private EditText etPlaylistName;
    private CheckBox check;
    private Button bAccept;

    private PlaylistManager pm = new PlaylistManager(this);

    @Override
    public void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_add_playlist);
        initViews();
    }

    private void initViews () {

        etPlaylistName = (EditText) findViewById(R.id.add_song_playlist_name);
        check = (CheckBox)  findViewById(R.id.add_playlist_check);
        bAccept = (Button) findViewById(R.id.add_playlist_button);
        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPlaylist(etPlaylistName.getText(), check.isChecked());
            }
        });
    }

    private void createPlaylist(Editable text, Boolean bool) {
        Playlist p = new Playlist(text.toString(), bool);
        pm.createPlaylist(p,this);
    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onPlaylistReceived(List<Playlist> playlists) {

    }

    @Override
    public void onNoPlaylists(Throwable throwable) {

    }

    @Override
    public void onPlaylistCreated(Playlist playlist) {
        Toast.makeText(this, "Created", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPlaylistFailure(Throwable throwable) {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUserPlaylistsReceived(List<Playlist> playlists) {

    }

    @Override
    public void onNoUserPlaylists(Throwable throwable) {

    }

    @Override
    public void onUserSpecificLikedPlaylistReceived(Playlist specificPlaylist) {

    }

    @Override
    public void onNoUserSpecificLikedPlaylist(Throwable throwable) {

    }
}
