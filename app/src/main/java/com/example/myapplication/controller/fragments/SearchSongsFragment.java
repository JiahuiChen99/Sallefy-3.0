package com.example.myapplication.controller.fragments;

import android.content.Context;
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
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.controller.music.MusicCallback;
import com.example.myapplication.model.SearchResult;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.SearchCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.SearchManager;
import com.example.myapplication.restapi.manager.TrackManager;

import java.util.ArrayList;
import java.util.List;

public class SearchSongsFragment extends Fragment implements SearchCallback, TrackCallback {

    private ArrayList<Track> tracks;
    private RecyclerView msongListRecyclerView;
    private TrackListAdapter adapter;
    private Boolean mode;

    private MusicCallback sendTracksCallback;

    public Boolean getMode() {
        return mode;
    }

    private String input;

    public String getInput() {
        return input;
    }

    public SearchSongsFragment(String input) {
        this.input = input;
    }

    public static SearchSongsFragment getInstance(String input){
        return new SearchSongsFragment(input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_songs, container, false);

        msongListRecyclerView = (RecyclerView) v.findViewById(R.id.search_songs);
        LinearLayoutManager songsListManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        msongListRecyclerView.setLayoutManager(songsListManager);

        if (input == null) {
            getData();
        }else if (input.equals("")) {
            mode = false;
            getData();
        }else {
            mode = true;
            updateSongs(input);
        }

        return v;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            sendTracksCallback = (MusicCallback) context;
        }catch (ClassCastException e){
            System.out.println("Error, class doesn't implement the interface");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        sendTracksCallback = null;
    }

    public void updateSongs(String input) {
        System.out.println(input);
        SearchManager.getInstance(this.getActivity()).searchSong(input,this);
    }

    public void updateSongs() {
        SearchManager.getInstance(this.getActivity()).searchSong(this.input,this);
    }

    public void getData(){
        TrackManager.getInstance(this.getActivity()).getAllTracks(this);
    }

    @Override
    public void onLikedTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
        this.tracks = (ArrayList)tracks;
        adapter = new TrackListAdapter(this, getContext(), this.tracks);
        msongListRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onInfoReceived(SearchResult output) {
        this.tracks = (ArrayList)output.getTracks();
        adapter = new TrackListAdapter(this, getContext(), this.tracks);
        msongListRecyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
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
        sendTracksCallback.setTracks(tracks, id);
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
    public void onTrackUploaded(Track uploadedTrack) {

    }

    @Override
    public void onNoTrackUploaded(Throwable notUploaded) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onNoInfo(Throwable throwable) {
        System.out.println("no");
    }
}
