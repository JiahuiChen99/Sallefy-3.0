package com.example.myapplication.controller.music;

import com.example.myapplication.model.Track;

import java.util.ArrayList;

public interface MusicCallback {

    void onMusicPlayerPrepared();

    void setTracks(ArrayList<Track> playlist, int currentTrack);

    void setTrack(Track song);

    void updatePlayPauseButton(boolean playing);
    void updateLikeButton(boolean liked);
    void updateSongTitle(String title);
    void updateArtist(String artistName);
    void updateSongImage(String imageURL);
    void updateSeekBar(int currentPosition);
    void updateMaxSeekBar(int currentPosition);
    void showShrinkedBar(boolean show);
}
