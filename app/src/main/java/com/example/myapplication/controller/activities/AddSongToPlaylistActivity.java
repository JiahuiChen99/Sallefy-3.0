package com.example.myapplication.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.SearchPlaylistAdapter;
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.model.Followed;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;

import java.util.ArrayList;
import java.util.List;

public class AddSongToPlaylistActivity extends AppCompatActivity implements PlaylistCallback {

    private RecyclerView mRecyclerView;
    private TrackListAdapter adapter;
    private PlaylistManager playlistManager;

    private Track track;
    private ArrayList<Playlist> playlists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        track = (Track) getIntent().getSerializableExtra("track");
        setContentView(R.layout.activity_add_song_playlist);
        initViews();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.playlists);
        playlistManager = new PlaylistManager(this);
        playlistManager.getUserPlaylists(this);
    }

    @Override
    public void onPlaylistReceived(List<Playlist> playlists) {

    }

    private void initReciclerView(List<Playlist> playlists) {
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        SearchPlaylistAdapter adapter = new SearchPlaylistAdapter(this, (ArrayList<Playlist>) playlists, this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        this.playlists = (ArrayList<Playlist>) playlists;
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
    public void onPlaylistSelected(Integer id, String sectionId) {
        Playlist playlist = new Playlist();
        for (Playlist p : playlists) {
            if (p.getId().equals(id)) {
                playlist = p;
                break;
            }
        }
        playlist.getTracks().add(track);
        playlistManager.modifyPlaylist(playlist, this);
    }

    @Override
    public void onErrorFollow(Throwable throwable) {

    }

    @Override
    public void onFollowReceived(Followed follow) {

    }

    @Override
    public void onUserPlaylistsReceived(List<Playlist> playlists) {
        initReciclerView(playlists);
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

    @Override
    public void onPlaylistModified(Playlist playlist) {
        Toast.makeText(this, "Song added to Playlist \n Go back", Toast.LENGTH_SHORT).show();
    }
}
