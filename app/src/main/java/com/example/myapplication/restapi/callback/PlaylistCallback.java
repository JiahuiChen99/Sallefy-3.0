package com.example.myapplication.restapi.callback;


import com.example.myapplication.model.Followed;
import com.example.myapplication.model.Playlist;

import java.util.List;

public interface PlaylistCallback extends FailureCallback{
    void onPlaylistReceived(List<Playlist> playlists);
    void onNoPlaylists(Throwable throwable);
    void onPlaylistCreated(Playlist playlist);
    void onPlaylistFailure(Throwable throwable);

    void onPlaylistSelected(Integer id, String sectionId);

    void onErrorFollow(Throwable throwable);
    void onFollowReceived(Followed follow);

    void onPlaylistModified(Playlist playlist);

    void onUserPlaylistsReceived(List<Playlist> playlists);
    void onNoUserPlaylists(Throwable throwable);

    void onUserSpecificLikedPlaylistReceived(Playlist specificPlaylist);
    void onNoUserSpecificLikedPlaylist(Throwable throwable);
}
