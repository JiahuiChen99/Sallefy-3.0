package com.example.myapplication.controller.activities;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.TrackManager;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class TrackDetailsActivity extends AppCompatActivity implements TrackCallback {

    private static final String PLAY_VIEW = "PlayIcon";
    private static final String STOP_VIEW = "StopIcon";
    private static final String LIKED = "LikeIcon";
    private static final String NOT_LIKED = "NotLikedIcon";

    private List<Track> mTracks;
    private TextView tvTitle;
    private ImageView ivThumbnail;
    private TextView tvSongName;
    private TextView tvArtist;

    private SeekBar mSeekBar;
    private TextView tvStartTime;
    private TextView tvEndTime;

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

    private Integer songID;
    private String sectionID;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        this.songID = getIntent().getIntExtra("songId", 0);
         this.sectionID = getIntent().getStringExtra("sectionId");
        initViews();
        getData(sectionID);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initViews(){
        tvTitle = findViewById(R.id.track_header);
        ivThumbnail = findViewById(R.id.song_thumbnail);
        tvSongName = findViewById(R.id.song_title);
        tvArtist = findViewById(R.id.song_author);
        tvStartTime = findViewById(R.id.start_time);
        tvEndTime = findViewById(R.id.end_time);
        btnPreviousSong = (ImageButton) findViewById(R.id.previous_song);
        btnPreviousSong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                songID = ((songID-1)%(mTracks.size()));
                songID = songID < 0 ? (mTracks.size()-1):songID;
                updateTrack(mTracks.get(songID));
            }
        });
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
        btnNextSong = (ImageButton) findViewById(R.id.next_song);
        btnNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songID = ((songID+1)%(mTracks.size()));
                songID = songID >= mTracks.size() ? 0:songID;

                updateTrack(mTracks.get(songID));
            }
        });
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //Set Seekbar maximum duration
                mSeekBar.setMax(mPlayer.getDuration());

                mDuration =  mPlayer.getDuration();

                //Set end time of the song
                tvEndTime.setText(createTimeLabel(mPlayer.getDuration()));

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
                    TrackManager.getInstance(getApplicationContext()).likeTrack(mTracks.get(songID).getId(), TrackDetailsActivity.this);
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
    
    public void updateTrack(Track track){
        tvArtist.setText(track.getUserLogin());
        tvSongName.setText(track.getName());
        tvSongName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvSongName.setSelected(true);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(20));
        Glide.with(this)
                .asBitmap()
                .load(track.getThumbnail())
                .apply(requestOptions)
                .into(ivThumbnail);

        try {
            mPlayer.reset();
            mPlayer.setDataSource(track.getUrl());
            //mediaPlayer.pause();
            mPlayer.prepare();
        } catch(Exception e) {
        }
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
        tvStartTime.setText(createTimeLabel(mPlayer.getCurrentPosition()));
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

    private void getData(String sectionID){

        if(sectionID.equalsIgnoreCase("Recent Tracks")){
            TrackManager.getInstance(this).getRecentTracks(this);
        }else{
            TrackManager.getInstance(this).getRecommendedTracks(this);
        }
    }

    private String createTimeLabel(Integer duration){
        String timerLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timerLabel += min + ":";

        if(sec < 10) timerLabel += "0";

        timerLabel += sec;

        return timerLabel;
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
    public void onRecentTracksReceived(List<Track> tracks) {
        mTracks = tracks;
        tvTitle.setText("Playing from " + sectionID);
        tvArtist.setText(mTracks.get(songID).getUser().getLogin());
        tvSongName.setText(mTracks.get(songID).getName());
        tvSongName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvSongName.setSelected(true);


        Glide.with(this)
                .asBitmap()
                .load(mTracks.get(songID).getThumbnail())
                .into(ivThumbnail);

        prepareMediaPlayer(mTracks.get(songID).getUrl());
    }

    @Override
    public void onNoRecentTracksReceived(Throwable throwable) {

    }

    @Override
    public void onRecommendedTracksReceived(List<Track> tracks) {
        mTracks = tracks;
        tvTitle.setText("Playing from " + sectionID);

        tvArtist.setText(mTracks.get(songID).getUser().getLogin());
        tvSongName.setText(mTracks.get(songID).getName());
        tvSongName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvSongName.setSelected(true);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(this)
                .asBitmap()
                .load(mTracks.get(songID).getThumbnail())
                .apply(requestOptions)
                .into(ivThumbnail);

        prepareMediaPlayer(mTracks.get(songID).getUrl());
    }

    @Override
    public void onNoRecommendedTracks(Throwable throwable) {

    }

    @Override
    public void onTrackSelected(Integer id, String sectionID) {

    }

    @Override
    public void onTrackLiked(Track like) {
        if(like.isLiked()){
            btnLikeSong.setImageResource(R.drawable.ic_heart);
            btnLikeSong.setColorFilter(Color.RED);
            btnLikeSong.setTag(LIKED);
        }else{
            btnLikeSong.setImageResource(R.drawable.ic_heart_outline);
            btnLikeSong.setColorFilter(Color.WHITE);
            btnLikeSong.setTag(NOT_LIKED);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
