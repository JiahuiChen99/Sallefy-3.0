package com.example.myapplication.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.myapplication.R;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddSongActivity extends AppCompatActivity implements PlaylistCallback {
    public static final int SONG_SELECTION = 0;
    public static final int THUMBNAIL_SELECTION = 1;

    private TextView tvTitle;
    private ImageView ivThumbnail;
    private EditText etSongName;
    private EditText etSongDuration;
    private Button etUpload;
    private Button etSelectSong;
    private AnimatedCircleLoadingView loadingView;
    private ConstraintLayout layout;
    private List<String> selectedThumbnail;
    private Uri selectedSong;
    private ImageView songPreview;

    Cloudinary cloudinary = new Cloudinary();

    private String thumbnailURL;
    private String songURL;

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
                    ActivityCompat.requestPermissions(AddSongActivity.this, PERMISSIONS, THUMBNAIL_SELECTION);
                }
            }
        });

        songPreview = findViewById(R.id.add_song_preview);
        songPreview.setVisibility(View.GONE);
        etSongName = (EditText) findViewById(R.id.add_song_name);
        etSongDuration = (EditText) findViewById(R.id.add_song_duration);
        etUpload = (Button) findViewById(R.id.add_song_button);
        layout = findViewById(R.id.constraintLayout2);
        etUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    uploadCloudinary();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                showSelectSong();
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
        MediaManager.init(this);
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
                .forResult(THUMBNAIL_SELECTION);
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
            case THUMBNAIL_SELECTION: {
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
        if (requestCode == THUMBNAIL_SELECTION && resultCode == RESULT_OK) {
            selectedThumbnail = Matisse.obtainPathResult(data);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(20));

            Glide.with(this)
                    .load(new File(selectedThumbnail.get(0)))
                    .apply(requestOptions)
                    .into(ivThumbnail);
        }
        if(requestCode == SONG_SELECTION && resultCode == RESULT_OK){
            selectedSong = data.getData();
            etSelectSong.setVisibility(View.GONE);
            songPreview.setVisibility(View.VISIBLE);
        }
    }


    private void showSelectSong(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a song to Upload"), SONG_SELECTION);
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(this, "Plase install a File Manager", Toast.LENGTH_SHORT).show();
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

    private void uploadCloudinary() throws IOException {
        String requestId = MediaManager.get().upload(selectedThumbnail.get(0))
                .unsigned("preset")
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {

                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                        //TODO: Change hardcoded progress
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        thumbnailURL = resultData.get("secure_url").toString();
                        System.out.println(thumbnailURL);
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {

                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {

                    }
                })
                .dispatch();
        //cloudinary.uploader().upload(selectedSong, ObjectUtils.asMap("resource_type", "video"));
        String requestID2 = MediaManager.get().upload(selectedSong)
                .unsigned("preset")
                .option("resource_type", "video")
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {

                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {

                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        songURL = resultData.get("secure_url").toString();
                        System.out.println(songURL);
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {

                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {

                    }
                })
                .dispatch();
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
