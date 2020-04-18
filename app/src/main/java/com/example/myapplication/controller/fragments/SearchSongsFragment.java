package com.example.myapplication.controller.fragments;

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
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.TrackManager;

import java.util.ArrayList;

public class SearchSongsFragment extends Fragment{

    private ArrayList<Track> favouriteSongsList;
    private RecyclerView msongListRecyclerView;

    public static SearchSongsFragment getInstance(){
        return new SearchSongsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_songs, container, false);

        msongListRecyclerView = (RecyclerView) view.findViewById(R.id.);
        LinearLayoutManager songsListManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        TrackListAdapter likedPlaylistAdapter = new TrackListAdapter( this, getContext(), null);
        msongListRecyclerView.setLayoutManager(songsListManager);
        msongListRecyclerView.setAdapter(likedPlaylistAdapter);

        getData();

        return view;
    }

    private void getData(){
        TrackManager.getInstance(this.getActivity()).getLikedTracks((TrackCallback) this);
        favouriteSongsList = new ArrayList<>();
    }
}
