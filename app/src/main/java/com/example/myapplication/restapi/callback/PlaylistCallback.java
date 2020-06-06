package com.example.myapplication.restapi.callback;


import com.example.myapplication.model.Follow;
import com.example.myapplication.model.Playlist;

import java.util.List;

public interface PlaylistCallback extends FailureCallback{
    void onPlaylistReceived(List<Playlist> playlists);
    void onNoPlaylists(Throwable throwable);
    void onPlaylistCreated(Playlist playlist);
    void onPlaylistFailure(Throwable throwable);

    void onUserPlaylistsReceived(List<Playlist> playlists);
    void onNoUserPlaylists(Throwable throwable);

    void onUserSpecificLikedPlaylistReceived(Playlist specificPlaylist);
    void onNoUserSpecificLikedPlaylist(Throwable throwable);

    void onFollowReceived(Follow follow);
    void onFollowFaliure(Throwable throwable);
}
