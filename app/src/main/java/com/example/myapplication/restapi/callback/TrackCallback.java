package com.example.myapplication.restapi.callback;


import com.example.myapplication.model.Track;

import java.util.List;

public interface TrackCallback extends FailureCallback {
    void onTracksReceived(List<Track> tracks);
    void onNoTracks(Throwable throwable);

    void onRecommendedTracksReceived(List<Track> tracks);
    void onNoRecommendedTracks(Throwable throwable);
}