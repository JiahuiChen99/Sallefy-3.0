package com.example.myapplication.controller.activities;


import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.Playlist;
import com.example.myapplication.restapi.callback.PlaylistCallback;

import java.util.List;

public class AddPlaylistActivity extends AppCompatActivity implements PlaylistCallback {




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

    }

    @Override
    public void onPlaylistFailure(Throwable throwable) {

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
