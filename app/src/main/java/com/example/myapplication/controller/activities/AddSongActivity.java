package com.example.myapplication.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;


import java.io.File;
import java.util.List;

public class AddSongActivity extends AppCompatActivity implements PlaylistCallback {
    public static final int PICKER_REQUEST_CODE = 1;

    private TextView tvTitle;
    private ImageView ivThumbnail;
    private EditText etSongName;
    private EditText etSongDuration;
    private Button etUpload;
    private Button etSelectSong;
    private AnimatedCircleLoadingView loadingView;
    private ConstraintLayout layout;
    private List<String> selectedSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        initViews();
    }

    private void initViews() {
        tvTitle = findViewById(R.id.textView2);
        ivThumbnail = findViewById(R.id.add_choose_song_thumbnail);
        ivThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] PERMISSIONS = {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if(hasPermissions(AddSongActivity.this, PERMISSIONS)){
                    ShowPicker();
                }else{
                    ActivityCompat.requestPermissions(AddSongActivity.this, PERMISSIONS, PICKER_REQUEST_CODE);
                }
            }
        });
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
        etSelectSong = (Button) findViewById(R.id.add_choose_song);
        etSelectSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    /**
     * Method that displays the image/video chooser.
     */
    public void ShowPicker(){
        Matisse.from(AddSongActivity.this)
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .maxSelectable(1)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(new GlideEngine())
                .forResult(PICKER_REQUEST_CODE);
    }

    /**
     * Callback that handles the status of the permissions request.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PICKER_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                            AddSongActivity.this,
                            "Permission granted! Please click on pick a file once again.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            AddSongActivity.this,
                            "Permission denied to read your External storage :(",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                return;
            }
        }
    }

    /**
     * Helper method that verifies whether the permissions of a given array are granted or not.
     *
     * @param context
     * @param permissions
     * @return {Boolean}
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            selectedSong = Matisse.obtainPathResult(data);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(20));

            Glide.with(this)
                    .load(new File(selectedSong.get(0)))
                    .apply(requestOptions)
                    .into(ivThumbnail);
        }
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
