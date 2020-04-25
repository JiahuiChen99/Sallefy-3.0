package com.example.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class SearchGenre {
    private String genre;
    private Integer id;
    private List<Track> tracks;

    public SearchGenre() {
        genre = null;
        id = null;
        tracks = new ArrayList<>(0);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Track track) {
        tracks.add(track);
    }
}
