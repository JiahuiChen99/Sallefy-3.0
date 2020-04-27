package com.example.myapplication.controller.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.activities.TrackDetailsActivity;
import com.example.myapplication.controller.adapters.SearchGenreAdapter;
import com.example.myapplication.model.EssencialGenre;
import com.example.myapplication.model.SearchGenre;
import com.example.myapplication.model.SearchResult;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.GenreCallback;
import com.example.myapplication.restapi.callback.SearchCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.GenreManager;
import com.example.myapplication.restapi.manager.SearchManager;
import com.example.myapplication.restapi.manager.TrackManager;

import java.util.ArrayList;
import java.util.List;

public class SearchGenreFragment extends Fragment implements SearchCallback, TrackCallback, GenreCallback {

    private ArrayList<Track> tracks;
    private static ArrayList<SearchGenre> list;
    private RecyclerView msongListRecyclerView;
    private SearchGenreFragment instance;
    private String input;

    public SearchGenreFragment(String input) {
        this.input = input;
        list = new ArrayList<>(0);
    }

    public static SearchGenreFragment getInstance(String input){
        return new SearchGenreFragment(input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_songs, container, false);

        //**************************************************************
        msongListRecyclerView = (RecyclerView) v.findViewById(R.id.search_songs);
        LinearLayoutManager songsListManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        msongListRecyclerView.setLayoutManager(songsListManager);
        //***************************************************************

        if (input == null) {
            getData();
        }else if (input.equals("")) {
            getData();
        }else {
            updateSongs(input);
        }

        return v;
    }

    public void updateSongs(String input) {
        SearchManager.getInstance(this.getActivity()).searchSong(input,this);
    }

    private void getData(){
        GenreManager.getInstance(this.getActivity()).getGenre(this);

    }

    @Override
    public void onLikedTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
        this.tracks = (ArrayList)tracks;

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < this.tracks.size(); j++) {
                for (int k = 0; k < this.tracks.get(j).getGenres().size(); k++) {
                    if (this.tracks.get(j).getGenres().get(k).getId().compareTo(list.get(i).getId()) == 0) {
                        list.get(i).setTracks(tracks.get(j));
                    }
                }
            }
        }
        SearchGenreAdapter adapter = new SearchGenreAdapter(getContext(), list, getActivity(), this);
        msongListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onGenreReceived(List<EssencialGenre> essencial) {
        list.clear();
        for (int i = 0; i < essencial.size(); i++) {
            list.add(new SearchGenre());
            list.get(i).setGenre(essencial.get(i).getName());
            list.get(i).setId(essencial.get(i).getId());
        }
        TrackManager.getInstance(this.getActivity()).getAllTracks(this);
    }


    @Override
    public void onNoTracks(Throwable throwable) {

    }

    @Override
    public void onRecentTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onNoRecentTracksReceived(Throwable throwable) {

    }

    @Override
    public void onRecommendedTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onNoRecommendedTracks(Throwable throwable) {

    }

    @Override
    public void onSpecificTrackReceived(Track track) {

    }

    @Override
    public void onNoSpecificTrack(Track track) {

    }

    @Override
    public void onTrackSelected(Integer id, String sectionID) {
        Intent intent = new Intent(getActivity(), TrackDetailsActivity.class);
        intent.putExtra("songId", id);
        intent.putExtra("sectionId", "Favourite Songs");
        startActivity(intent);
    }

    @Override
    public void onTrackLiked(Track like) {

    }

    @Override
    public void onNoLikedTracks(Throwable noLikedTracks) {

    }

    @Override
    public void onArtistTracksReceived(List<Track> artistTracks) {

    }

    @Override
    public void onNoArtistTracks(Throwable noArtistTracks) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onInfoReceived(SearchResult output) {
        /*this.tracks = (ArrayList)output.getTracks();
        TrackListAdapter adapter = new TrackListAdapter(this, getContext(), this.tracks);
        msongListRecyclerView.setAdapter(adapter);*/
    }

    @Override
    public void onNoInfo(Throwable throwable) {
    }

    @Override
    public void onNoGenre(Throwable throwable) {

    }

    @Override
    public void onGenreSelected(Integer id, String sectionID) {

    }
}
