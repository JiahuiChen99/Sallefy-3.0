package com.example.myapplication.controller.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.TrackManager;

import java.util.List;

public class TrackDetailsActivity extends AppCompatActivity implements TrackCallback {

    private Track mTrack;
    private TextView tvTitle;
    private ImageView ivThumbnail;
    private TextView tvSongName;
    private TextView tvArtist;

    private ImageButton btnBackward;
    private ImageButton btnPlaySotp;
    private ImageButton btnForward;
    private SeekBar mSeekBar;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        Integer id = getIntent().getIntExtra("songId", 0);
        initViews();
        getData(id);
    }

    private void initViews(){
        tvTitle = findViewById(R.id.track_header);
        ivThumbnail = findViewById(R.id.song_thumbnail);
        tvSongName = findViewById(R.id.song_title);
        tvArtist = findViewById(R.id.song_author);
    }
    private void getData(Integer id){
        TrackManager.getInstance(this).getSpecificTrack(id, this);
    }

    @Override
    public void onTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onSpecificTrackReceived(Track track) {
        mTrack = track;
        tvTitle.setText("Now Playing");
        tvArtist.setText(mTrack.getUser().getLogin());
        tvSongName.setText(mTrack.getName());

        Glide.with(this)
                .asBitmap()
                .load(mTrack.getThumbnail())
                .into(ivThumbnail);
    }

    @Override
    public void onNoSpecificTrack(Track track) {

    }

    @Override
    public void onNoTracks(Throwable throwable) {

    }

    @Override
    public void onRecommendedTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onNoRecommendedTracks(Throwable throwable) {

    }

    @Override
    public void onTrackSelected(Integer id) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
