package com.example.myapplication.restapi.callback;


import com.example.myapplication.model.Playlist;

public interface PlaylistCallback extends FailureCallback{
    void onPlaylistReceived(Playlist playlists);
    void onNoPlaylists(Throwable throwable);
    void onPlaylistCreated(Playlist playlist);
    void onPlaylistFailure(Throwable throwable);
}
