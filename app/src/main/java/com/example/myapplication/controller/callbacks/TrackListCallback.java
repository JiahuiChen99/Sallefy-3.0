package com.example.myapplication.controller.callbacks;


import com.example.myapplication.model.Track;

public interface TrackListCallback {
    void onTrackSelected(Track track);
    void onTrackSelected(int index);
}
