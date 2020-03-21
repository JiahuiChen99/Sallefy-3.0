package com.example.myapplication.controller.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;

import java.util.ArrayList;
import java.util.List;

public class IdPlaylistActivity extends AppCompatActivity implements TrackCallback, PlaylistCallback {

    private RecyclerView mRecyclerView;
    private ArrayList<Track> mTracks;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_song);
        Intent i = new Intent();
        Integer id = getIntent().getIntExtra("playlistId", 0);
        initViews();
        getData(id);
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.playlist_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, null);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onPlaylistReceived(Playlist playlists) {
        mTracks = (ArrayList) playlists.getTracks();
        TrackListAdapter adapter = new TrackListAdapter(this, mTracks);
        mRecyclerView.setAdapter(adapter);
    }

    private void getData(Integer id) {
        PlaylistManager.getInstance(this).seePlaylists(id,this);
        mTracks = new ArrayList<>();
    }

    @Override
    public void onTracksReceived(List<Track> tracks) {


    }

    @Override
    public void onNoTracks(Throwable throwable) {

    }

    @Override
    public void onFailure(Throwable throwable) {

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

}
