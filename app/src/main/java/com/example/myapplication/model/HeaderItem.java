package com.example.myapplication.model;

public class HeaderItem extends ListItem {

    private String Genre;
    private Boolean empty;

    public void setGenre (String genre) {
        Genre = genre;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public String getGenre() {
        return Genre;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}
