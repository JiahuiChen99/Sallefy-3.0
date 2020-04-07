package com.example.myapplication.controller.activities;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.TrackManager;

import java.io.IOException;
import java.util.List;

public class TrackDetailsActivity extends AppCompatActivity implements TrackCallback {

    private static final String PLAY_VIEW = "PlayIcon";
    private static final String STOP_VIEW = "StopIcon";
    private static final String LIKED = "LikeIcon";
    private static final String NOT_LIKED = "NotLikedIcon";

    private Track mTrack;
    private TextView tvTitle;
    private ImageView ivThumbnail;
    private TextView tvSongName;
    private TextView tvArtist;

    private SeekBar mSeekBar;

    private ImageButton btnPreviousSong;
    private ImageButton btnBackward;
    private ImageButton btnPlayStop;
    private ImageButton btnForward;
    private ImageButton btnNextSong;

    private ImageButton btnLikeSong;
    private ImageButton btnAddToPlaylist;
    private ImageButton btnDownload;
    private ImageButton btnShareSong;

    private MediaPlayer mPlayer;
    private int mDuration;
    private Handler mHandler;
    private Runnable mRunnable;


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
        btnBackward = (ImageButton) findViewById(R.id.backwards);
        btnBackward.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPlayer.seekTo(mPlayer.getCurrentPosition() - 5000);
            }
        });
        btnPlayStop = (ImageButton) findViewById(R.id.play_stop);
        btnPlayStop.setTag(PLAY_VIEW);
        btnPlayStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(btnPlayStop.getTag().equals(PLAY_VIEW)){
                    playAudio();
                }else{
                    pauseAudio();
                }
            }
        });
        btnForward = (ImageButton) findViewById(R.id.forward);
        btnForward.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPlayer.seekTo(mPlayer.getCurrentPosition() + 5000);
            }
        });
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBar.setMax(mPlayer.getDuration());
                mDuration =  mPlayer.getDuration();
                playAudio();
            }
        });
        mSeekBar = (SeekBar) findViewById(R.id.song_progress);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mPlayer.seekTo(progress);
                }
                if (mDuration > 0) {
                    System.out.println(mDuration + ": " + progress);
                    int newProgress = ((progress*100)/mDuration);
                    System.out.println("New progress: " + newProgress);
                    if(newProgress == 100){
                        btnPlayStop.setImageResource(R.drawable.ic_play_outline);
                        btnPlayStop.setTag(PLAY_VIEW);
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnLikeSong = (ImageButton) findViewById(R.id.like);
        btnLikeSong.setTag(NOT_LIKED);
        btnLikeSong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                System.out.println();
                if(btnLikeSong.getTag().equals(LIKED)){
                    btnLikeSong.setImageResource(R.drawable.ic_heart_outline);
                    btnLikeSong.setTag(NOT_LIKED);
                }else{
                    btnLikeSong.setImageResource(R.drawable.ic_heart);
                    btnLikeSong.setTag(LIKED);
                }
            }
        });
        btnAddToPlaylist = (ImageButton) findViewById(R.id.add_to_playlist);
        btnAddToPlaylist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        btnDownload = (ImageButton) findViewById(R.id.download);
        btnDownload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        btnShareSong = (ImageButton) findViewById(R.id.share);
        btnShareSong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        mHandler = new Handler();
    }

    private void prepareMediaPlayer(final String url) {
        Thread connection = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mPlayer.setDataSource(url);
                    mPlayer.prepare(); // might take long! (for buffering, etc)
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Error, couldn't play the music\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        connection.start();
    }

    private void playAudio() {
        mPlayer.start();
        updateSeekBar();
        btnPlayStop.setImageResource(R.drawable.ic_pause_outline);
        btnPlayStop.setTag(STOP_VIEW);
    }

    private void pauseAudio() {
        mPlayer.pause();
        btnPlayStop.setImageResource(R.drawable.ic_play_outline);
        btnPlayStop.setTag(PLAY_VIEW);
    }

    public void updateSeekBar() {
        mSeekBar.setProgress(mPlayer.getCurrentPosition());

        if(mPlayer.isPlaying()) {
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }
            };
            mHandler.postDelayed(mRunnable, 1000);
        }
    }

    private void getData(Integer id){
        TrackManager.getInstance(this).getSpecificTrack(id, this);
    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
    }

    @Override
    public void onSpecificTrackReceived(Track track) {

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
