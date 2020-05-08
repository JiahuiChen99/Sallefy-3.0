package com.example.myapplication.controller.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;


import java.util.List;

public class AddSongActivity extends AppCompatActivity implements PlaylistCallback {

    private TextView tvTitle;
    private EditText etSongName;
    private EditText etSongDuration;
    private Button etUpload;
    private AnimatedCircleLoadingView loadingView;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        initViews();
    }

    private void initViews() {
        tvTitle = findViewById(R.id.textView2);
        etSongName = (EditText) findViewById(R.id.add_song_name);
        etSongDuration = (EditText) findViewById(R.id.add_song_duration);
        etUpload = (Button) findViewById(R.id.add_song_button);
        layout = findViewById(R.id.constraintLayout2);
        etUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getData(Integer.parseInt(etIdPlaylist.getText().toString()));
                tvTitle.setText("Uploading ...");
                layout.setVisibility(View.INVISIBLE);
                startLoading();
                uploading();
            }
        });
        loadingView = findViewById(R.id.circle_loading_view);

        loadingView.setAnimationListener(new AnimatedCircleLoadingView.AnimationListener() {
            @Override
            public void onAnimationEnd(boolean success) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        });
    }

    private void startLoading(){
        loadingView.startDeterminate();
    }

    private void uploading(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    for (int i = 0; i <= 100 ; i++) {
                        Thread.sleep(100);
                        changePercent(i);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    private void changePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingView.setPercent(percent);
            }
        });
    }

    private void getData(Integer idPlaylist) {
        //PlaylistManager.getInstance(this).seePlaylists(idPlaylist,this);
    }

    @Override
    public void onPlaylistReceived(List<Playlist> playlist) {
        /*if (playlist.getUser().getLogin().equals(Sesion.getInstance(this).getUser().getLogin())) {
            List<Track> tracks = null;
            tracks = playlist.getTracks();
            tracks.add(new Track(Integer.parseInt(etIdSong.getText().toString())));
            PlaylistManager.getInstance(this).modifyPlaylist(new Playlist(Integer.parseInt(etIdPlaylist.getText().toString()),
                    etNamePlaylist.getText().toString(), tracks, true), this);
        }
        else{
            Toast.makeText(getApplicationContext(), "No eres dueño de la playlist o no tienes permisos", Toast.LENGTH_LONG).show();
        }*/
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
