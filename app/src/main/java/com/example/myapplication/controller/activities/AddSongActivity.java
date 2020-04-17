package com.example.myapplication.controller.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.model.User;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;
import com.example.myapplication.utils.Sesion;

import java.util.List;

public class AddSongActivity extends AppCompatActivity implements PlaylistCallback {

    private EditText etIdSong;
    private EditText etIdPlaylist;
    private EditText etNamePlaylist;
    private Button etUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        initViews();
    }

    private void initViews() {
        etIdSong = (EditText) findViewById(R.id.add_song_id);
        etIdPlaylist = (EditText) findViewById(R.id.add_song_playlist_id);
        etNamePlaylist = (EditText) findViewById(R.id.add_song_playlist_name);
        etUpload = (Button) findViewById(R.id.add_song_button);
        etUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Integer.parseInt(etIdPlaylist.getText().toString()));
            }
        });
    }

    private void getData(Integer idPlaylist) {
        PlaylistManager.getInstance(this).seePlaylists(idPlaylist,this);
    }

    @Override
    public void onPlaylistReceived(Playlist playlist) {
        if (playlist.getUser().getLogin().equals(Sesion.getInstance(this).getUser().getLogin())) {
            List<Track> tracks = null;
            tracks = playlist.getTracks();
            tracks.add(new Track(Integer.parseInt(etIdSong.getText().toString())));
            PlaylistManager.getInstance(this).modifyPlaylist(new Playlist(Integer.parseInt(etIdPlaylist.getText().toString()),
                    etNamePlaylist.getText().toString(), tracks, true), this);
        }
        else{
            Toast.makeText(getApplicationContext(), "No eres dueño de la playlist o no tienes permisos", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNoPlaylists(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Call failed! " + throwable.getMessage(), Toast.LENGTH_LONG).show();

    }

    public void onFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Failure" + throwable.getMessage(), Toast.LENGTH_LONG).show();

    }

    public void onPlaylistCreated(Playlist playlists) {
        Toast.makeText(getApplicationContext(), "Se ha añadido con éxito la canción", Toast.LENGTH_LONG).show();
    }
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
}
