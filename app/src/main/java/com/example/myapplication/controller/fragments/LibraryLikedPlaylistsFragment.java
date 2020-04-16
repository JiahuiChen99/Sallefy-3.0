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
import com.example.myapplication.controller.adapters.LikedPlaylistAdapter;
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.controller.adapters.UserPlaylistAdapter;
import com.example.myapplication.controller.callbacks.TrackListCallback;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class LibraryLikedPlaylistsFragment extends Fragment implements PlaylistCallback, TrackListCallback {

    private RecyclerCoverFlow mPlaylistRecyclerView;
    private ArrayList<Playlist> mPlaylists;
    private RecyclerView msongList;
    private TrackListCallback callback;
    private Context context;

    public static LibraryLikedPlaylistsFragment getInstance(){
        return new LibraryLikedPlaylistsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_library_user_playlists, container, false);

        mPlaylistRecyclerView = (RecyclerCoverFlow) view.findViewById(R.id.library_playlist);
        CoverFlowLayoutManger userPlaylistManager = new CoverFlowLayoutManger(false, false, true, (float) 1);
        LikedPlaylistAdapter likedPlaylistAdapter = new LikedPlaylistAdapter( this, null);
        mPlaylistRecyclerView.setLayoutManager(userPlaylistManager);
        mPlaylistRecyclerView.setAdapter(likedPlaylistAdapter);
        mPlaylistRecyclerView.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected(){
            @Override
            public void onItemSelected(int position) {
                System.out.println("POSITION: " + position + " - " + mPlaylists.get(position).getThumbnail());

                TrackListAdapter songsListAdapter = new TrackListAdapter(callback, getContext(), (ArrayList<Track>) mPlaylists.get(position).getTracks());
                msongList.setAdapter(songsListAdapter);
            }});

        msongList = (RecyclerView) view.findViewById(R.id.library_playlist_songs);
        LinearLayoutManager songsListManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        msongList.setLayoutManager(songsListManager);;

        getData();

        return view;
    }

    private void getData(){
        PlaylistManager.getInstance(this.getActivity()).getLikedPlaylists(this);
        mPlaylists = new ArrayList<>();
    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onPlaylistReceived(Playlist playlists) {

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
        mPlaylists = (ArrayList) playlists;
        LikedPlaylistAdapter adapter = new LikedPlaylistAdapter(this, mPlaylists);
        mPlaylistRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoUserPlaylists(Throwable throwable) {

    }


    @Override
    public void onTrackSelected(Track track) {

    }

    @Override
    public void onTrackSelected(int index) {

    }


}