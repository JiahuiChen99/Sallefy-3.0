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
import com.example.myapplication.controller.callbacks.TrackListCallback;
import com.example.myapplication.model.Track;

import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.TrackManager;

import java.util.ArrayList;
import java.util.List;


public class LibraryFavouriteSongsFragment extends Fragment implements TrackCallback {
    private ArrayList<Track> favouriteSongsList;
    private RecyclerView msongListRecyclerView;
    private TrackListCallback callback;

    public static LibraryFavouriteSongsFragment getInstance(){
        return new LibraryFavouriteSongsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_library_favourite_songs, container, false);

        msongListRecyclerView = (RecyclerView) view.findViewById(R.id.library_favourite_songs);
        LinearLayoutManager songsListManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        TrackListAdapter likedPlaylistAdapter = new TrackListAdapter( callback, getContext(), null);
        msongListRecyclerView.setLayoutManager(songsListManager);
        msongListRecyclerView.setAdapter(likedPlaylistAdapter);

        getData();

        return view;
    }

    private void getData(){
        TrackManager.getInstance(this.getActivity()).getLikedTracks((TrackCallback) this);
        favouriteSongsList = new ArrayList<>();
    }


    @Override
    public void onTracksReceived(List<Track> tracks) {

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

    }

    @Override
    public void onTrackLiked(Track like) {

    }

    @Override
    public void onLikedTracksReceived(List<Track> likedTracks) {
        favouriteSongsList = (ArrayList) likedTracks;
        TrackListAdapter adapter = new TrackListAdapter(callback, getContext(), favouriteSongsList);
        msongListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoLikedTracks(Throwable noLikedTracks) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
