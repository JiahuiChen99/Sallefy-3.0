package com.example.myapplication.controller.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.controller.music.MusicService;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.manager.TrackManager;
import com.sackcentury.shinebuttonlib.ShineButton;
import java.util.List;

public class TrackDetailsActivity extends AppCompatActivity {

    private MusicService musicService;
    private ServiceConnection mConnection = new ServiceConnection() {

        /************************************************************************
         * This is called when the connection with the service has been stablished,
         * giving us the service object we can use to interact with the service
         ************************************************************************/
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MusicBinder)service).getService();
        }

        /************************************************************************
         * We shoulding see this happen, this is called when the connection with
         * the service has been unexpectedly disconnected, its process crashed
         ************************************************************************/
        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };
    private boolean mIsBound;

    private static final String PLAY_VIEW = "PlayIcon";
    private static final String STOP_VIEW = "StopIcon";
    private static final String LIKED = "LikeIcon";
    private static final String NOT_LIKED = "NotLikedIcon";

    private List<Track> mTracks;
    private TextView tvTitle;
    private ImageView ivThumbnail;
    private TextView tvSongName;
    private TextView tvArtist;

    private ImageButton btnBack;
    private ImageButton btnMore;
    private SeekBar mSeekBar;
    private TextView tvStartTime;
    private TextView tvEndTime;

    private ImageButton btnPreviousSong;
    private ImageButton btnBackward;
    private ImageButton btnPlayStop;
    private ImageButton btnForward;
    private ImageButton btnNextSong;

    private ShineButton btnLikeSong;
    private ImageButton btnAddToPlaylist;
    private ImageButton btnDownload;
    private ImageButton btnShareSong;


    private MediaPlayer mPlayer;
    private int mDuration;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        initViews();
        initService();

    }

    private void initService(){
        //Establish a connection with the service
        bindService(new Intent(this, MusicService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    private void doUnbindService(){
        if(mIsBound){
            //Detach our existing connection
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    private void initViews(){
        btnBack = findViewById(R.id.down_button);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
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
                musicService.updateTrack();
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
                    musicService.playAudio();
                }else{
                    musicService.pauseAudio();
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
                musicService.updateTrack();
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

                musicService.playAudio();
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

        btnLikeSong = (ShineButton) findViewById(R.id.like);
        btnLikeSong.init(this);
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
    }


    
    public void updateTrack(Track track){
        tvArtist.setText(track.getUserLogin());
        tvSongName.setText(track.getName());
        tvSongName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvSongName.setSelected(true);

        //TrackManager.getInstance(this).isLiked(mTracks.get(songID).getId(), TrackDetailsActivity.this);

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

    private String createTimeLabel(Integer duration){
        String timerLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timerLabel += min + ":";

        if(sec < 10) timerLabel += "0";

        timerLabel += sec;

        return timerLabel;
    }


}
