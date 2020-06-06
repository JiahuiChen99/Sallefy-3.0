package com.example.myapplication.controller.fragments;

import android.content.Context;
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
import com.example.myapplication.controller.adapters.LikedPlaylistAdapter;
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.controller.adapters.UserPlaylistAdapter;
import com.example.myapplication.controller.callbacks.TrackListCallback;
import com.example.myapplication.model.Followed;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class LibraryLikedPlaylistsFragment extends Fragment implements PlaylistCallback, TrackCallback {

    private RecyclerCoverFlow mPlaylistRecyclerView;
    private ArrayList<Playlist> mPlaylists;
    private RecyclerView msongList;
    private TrackCallback callback;
    private Context context;
    private Integer playlistID = 0;

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
                playlistID = position;
                TrackListAdapter songsListAdapter = new TrackListAdapter(LibraryLikedPlaylistsFragment.this, getContext(), (ArrayList<Track>) mPlaylists.get(position).getTracks());
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
        TrackListAdapter songsListAdapter = new TrackListAdapter(this, getContext(), (ArrayList<Track>) mPlaylists.get(0).getTracks());
        msongList.setAdapter(songsListAdapter);
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
    public void onErrorFollow(Throwable throwable) {

    }

    @Override
    public void onFollowReceived(Followed follow) {

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
        Intent intent = new Intent(getActivity(), TrackDetailsActivity.class);
        intent.putExtra("songId", id);
        intent.putExtra("sectionId", LikedPlaylistAdapter.TAG);
        intent.putExtra("playlistID", mPlaylists.get(playlistID).getId());
        startActivity(intent);
    }

    @Override
    public void onTrackLiked(Track like) {

    }

    @Override
    public void onLikedTracksReceived(List<Track> likedTracks) {

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
}
