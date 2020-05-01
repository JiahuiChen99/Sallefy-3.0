package com.example.myapplication.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;

import java.util.List;

public class BuscarPlaylistActivity extends AppCompatActivity implements PlaylistCallback {

    private EditText etIdPlaylist;
    private Button bSearch;
    private TextView tvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirar_playlist);
        initViews();
    }

    private void initViews() {
        etIdPlaylist = (EditText) findViewById(R.id.id_playlist);
        bSearch = (Button) findViewById(R.id.aceptar_btn);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Integer.parseInt(etIdPlaylist.getText().toString()));
            }
        });
    }

    private void getData(Integer num) {
        PlaylistManager.getInstance(this).seePlaylists(num,this);
    }
    private void updateList(String list) {
        tvList.setText(list);
    }

    @Override
    public void onPlaylistReceived(Playlist playlists) {

        if (playlists.getTracks().isEmpty()) {
            Toast.makeText(getApplicationContext(), "No hay canciones en la playlist", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(getApplicationContext(), IdPlaylistActivity.class);
            intent.putExtra("playlistId", playlists.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onNoPlaylists(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Call failed! " + throwable.getMessage(), Toast.LENGTH_LONG).show();

    }

    public void onFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Failure" + throwable.getMessage(), Toast.LENGTH_LONG).show();

    }

    public void onPlaylistCreated(Playlist playlist) {

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
