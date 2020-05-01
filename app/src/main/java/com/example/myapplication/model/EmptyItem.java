package com.example.myapplication.model;

public class EmptyItem extends ListItem {

    private String Text;

    public void setGenre (String text) {
        Text = text;
    }


    public String getGenre() {
        return Text;
    }

    @Override
    public int getType() {
        return TYPE_EMPTY;
    }
}
