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
import com.example.myapplication.controller.adapters.SearchPlaylistAdapter;
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.controller.adapters.UserPlaylistAdapter;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.SearchResult;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.SearchCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;
import com.example.myapplication.restapi.manager.SearchManager;
import com.example.myapplication.restapi.manager.TrackManager;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class SearchPlaylistsFragment extends Fragment implements SearchCallback, PlaylistCallback {

    private ArrayList<Track> tracks;
    private ArrayList<Playlist> playlists;
    private RecyclerView msongListRecyclerView;
    private SearchPlaylistsFragment instance;
    private String input;

    public SearchPlaylistsFragment(String input) {
        this.input = input;
    }

    public static SearchPlaylistsFragment getInstance(String input){
        return new SearchPlaylistsFragment(input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_songs, container, false);

        msongListRecyclerView = (RecyclerView) v.findViewById(R.id.search_songs);
        LinearLayoutManager songsListManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        SearchPlaylistAdapter userPlaylistAdapter = new SearchPlaylistAdapter( getContext(), null);
        msongListRecyclerView.setLayoutManager(songsListManager);
        msongListRecyclerView.setAdapter(userPlaylistAdapter);

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
        System.out.println(input);
        SearchManager.getInstance(this.getActivity()).searchSong(input,this);
    }

    public void getData(){
        PlaylistManager.getInstance(this.getActivity()).getAllPlaylists("0", this);
    }

    @Override
    public void onInfoReceived(SearchResult output) {
        this.playlists = (ArrayList)output.getPlaylists();
        SearchPlaylistAdapter adapter = new SearchPlaylistAdapter(getContext(), playlists);
        msongListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoInfo(Throwable throwable) {

    }

    @Override
    public void onPlaylistReceived(List<Playlist> playlists) {
        this.playlists = (ArrayList) playlists;
        SearchPlaylistAdapter adapter = new SearchPlaylistAdapter(getContext(), this.playlists);
        msongListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoPlaylists(Throwable throwable) {

    }

    @Override
    public void onPlaylistCreated(Playlist playlist) {

    }

    @Override
    public void onPlaylistFailure(Throwable throwable) {

    }

    @Override
    public void onUserPlaylistsReceived(List<Playlist> playlists) {

    }

    @Override
    public void onNoUserPlaylists(Throwable throwable) {

    }

    @Override
    public void onUserSpecificLikedPlaylistReceived(Playlist specificPlaylist) {

    }

    @Override
    public void onNoUserSpecificLikedPlaylist(Throwable throwable) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
