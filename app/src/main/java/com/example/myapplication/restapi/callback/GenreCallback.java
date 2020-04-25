package com.example.myapplication.restapi.callback;

import com.example.myapplication.model.EssencialGenre;

import java.util.List;

public interface GenreCallback extends FailureCallback {
    void onGenreReceived(List<EssencialGenre> essencial);
    void onNoGenre(Throwable throwable);
}
