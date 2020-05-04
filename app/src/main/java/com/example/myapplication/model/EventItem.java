package com.example.myapplication.model;


public class EventItem extends ListItem {

    private Track track;

    public void setTrack(Track track) {
        this.track = track;
    }

    public Track getTrack() {
        return track;
    }

    @Override
    public int getType() {
        return TYPE_EVENT;
    }
}
