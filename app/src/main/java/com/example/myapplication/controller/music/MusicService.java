package com.example.myapplication.controller.music;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;
import com.example.myapplication.restapi.manager.TrackManager;
import com.example.myapplication.restapi.manager.UserResourcesManager;
import com.example.myapplication.utils.Variables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MusicService extends Service {

    private MediaPlayer mediaPlayer;
    private final IBinder mBinder = new MusicBinder();
    private AudioManager audioManager;
    private boolean playingBeforeInterruption = false;

    private ArrayList<Track> mTracks = new ArrayList<>();
    private int currentTrack = 0;


    private Integer songID;
    private String sectionID;
    private Integer playlistID;
    private String artistID;
    private String playlistName;

    private MusicCallback mCallback;

    public class MusicBinder extends Binder {
        public MusicService getService(){
            return MusicService.this;
        }
    }

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.songID = getIntent().getIntExtra("songId", 0);
        this.sectionID = getIntent().getStringExtra("sectionId");
        this.playlistID = getIntent().getIntExtra("playlistID", 0);
        this.artistID = getIntent().getStringExtra("artistID");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getStringExtra(Variables.URL) != null)
            playStream(intent.getStringExtra(Variables.URL));

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void stopService() {
        pausePlayer();
        stopSelf();
        onDestroy();
    }

    public void playStream(String url) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
            } catch(Exception e) {
            }
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                updateTrack(1);
            }
        });

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    System.out.println("Entra en el prepared");

                    if (mCallback != null) {
                        System.out.println("Entra en el callback");
                        mCallback.onMusicPlayerPrepared();
                    }
                }
            });
        } catch(Exception e) {

        }

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

    public void playStream(ArrayList<Track> tracks, int currentTrack) {

        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
            } catch(Exception e) {
            }
            mediaPlayer = null;
        }

        mTracks = tracks;
        this.currentTrack = currentTrack;
        String url = mTracks.get(currentTrack).getUrl();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                updateTrack(1);
            }
        });

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    mCallback.onMusicPlayerPrepared();

                }
            });
        } catch(Exception e) {
        }

    }

    public int getAudioSession() {
        return mediaPlayer.getAudioSessionId();
    }

    public Track getCurrentTrack() {
        return mTracks.size() > 0 ? mTracks.get(currentTrack):null;
    }

    public void updateTrack(int offset) {
        currentTrack = ((currentTrack+offset)%(mTracks.size()));
        currentTrack = currentTrack >= mTracks.size() ? 0:currentTrack;

        String newUrl = mTracks.get(currentTrack).getUrl();
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(newUrl);
            //mediaPlayer.pause();
            mediaPlayer.prepare();
        } catch(Exception e) {
        }
    }

    public void pausePlayer() {
        try {
            mediaPlayer.pause();
           // showNotification();
        } catch (Exception e) {
            Log.d(" EXCEPTION", "failed to ic_pause media player.");
        }
    }

    public void playPlayer() {
        try {
            getAudioFocusAndPlay();
            //showNotification();
        } catch (Exception e) {
            Log.d("EXCEPTION", "failed to start media player.");
        }
    }

    public void togglePlayer() {
        try {
            if (mediaPlayer.isPlaying()) {
                pausePlayer();
            } else {
                playPlayer();
            }
        }catch(Exception e) {
            Log.d("EXCEPTION", "failed to toggle media player.");
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    // audio focus section
    public void getAudioFocusAndPlay () {
        audioManager = (AudioManager) this.getBaseContext().getSystemService(Context.AUDIO_SERVICE);

        int result = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer.start();
        }
    }

    public void setCallback(MusicCallback callback) {
        mCallback = callback;
    }

    public void setCurrentDuration(int time) {
        try {
            mediaPlayer.seekTo(time);
        } catch (Exception e) {
            Log.d("EXCEPTION", "Failed to set the duration");
        }
    }

    public void updateTrack(){

    }


    public void playAudio() {
        mPlayer.start();
        updateSeekBar();
        btnPlayStop.setImageResource(R.drawable.ic_pause_outline);
        btnPlayStop.setTag(STOP_VIEW);
    }

    public void pauseAudio() {
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

    public int getCurrrentPosition() {
        try {
            if (mediaPlayer != null) {
                return mediaPlayer.getCurrentPosition();
            } else {
                return 0;
            }
        }catch(Exception e) {
            Log.d("EXCEPTION", "Failed to get the duration");
        }
        return 0;
    }

    public int getMaxDuration() {
        try {
            if (mediaPlayer != null) {
                return mediaPlayer.getDuration();
            } else {
                return 0;
            }
        }catch(Exception e) {
            Log.d("EXCEPTION", "Failed to get the duration");
        }
        return 0;
    }

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                if (mediaPlayer.isPlaying()) {
                    playingBeforeInterruption = true;
                } else {
                    playingBeforeInterruption = false;
                }
                pausePlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                if (playingBeforeInterruption) {
                    playPlayer();
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                pausePlayer();
                audioManager.abandonAudioFocus(afChangeListener);
            }
        }
    };

    private void updateData(List<Track> tracks){
        mTracks = tracks;
        if(sectionID.equalsIgnoreCase("Recent Tracks") ||sectionID.equalsIgnoreCase("Recommended Tracks")
                || sectionID.equalsIgnoreCase("Favourite Songs")){
            tvTitle.setText("Playing from " + sectionID);
        }
        if(sectionID.equalsIgnoreCase("User Liked Playlists") || sectionID.equalsIgnoreCase("User Playlists")){
            tvTitle.setText("Playing " + playlistName + " from " + sectionID );
        }
        if(sectionID.equalsIgnoreCase("Artists")){
            tvTitle.setText("Playing " + artistID + " popular songs");
        }

        tvArtist.setText(mTracks.get(songID).getUser().getLogin());
        tvSongName.setText(mTracks.get(songID).getName());
        tvSongName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvSongName.setSelected(true);

        if(mTracks.get(songID).isLiked()){
            btnLikeSong.setChecked(true);
            btnLikeSong.setImageResource(R.drawable.ic_heart);
            btnLikeSong.setColorFilter(Color.RED);
            btnLikeSong.setTag(LIKED);
        }else{
            btnLikeSong.setChecked(false);
            btnLikeSong.setImageResource(R.drawable.ic_heart_outline);
            btnLikeSong.setColorFilter(Color.WHITE);
            btnLikeSong.setTag(NOT_LIKED);
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(this)
                .asBitmap()
                .load(mTracks.get(songID).getThumbnail())
                .apply(requestOptions)
                .into(ivThumbnail);

        playStream(mTracks.get(songID).getUrl());
    }

    private void getData(String sectionID){

        switch (sectionID){
            case "Recent Tracks":
                TrackManager.getInstance(this).getRecentTracks(this);
                break;
            case "Recommended Tracks":
                TrackManager.getInstance(this).getRecommendedTracks(this);
                break;
            case "User Liked Playlists":
            case "User Playlists":
                PlaylistManager.getInstance(this).getSpecificPlaylist(playlistID, this);
                break;
            case "Favourite Songs":
                TrackManager.getInstance(this).getLikedTracks(this);
            case "Artists":
                UserResourcesManager.getInstance(this).getFollowingArtistsTopSongs(artistID, this);
                break;
        }
    }

    @Override
    public void onRecentTracksReceived(List<Track> tracks) {
        updateData(tracks);
    }

    @Override
    public void onRecommendedTracksReceived(List<Track> tracks) {
        updateData(tracks);
    }


    @Override
    public void onTrackLiked(Track like) {
        System.out.println(like.isLiked());

        if(like.isLiked()){
            btnLikeSong.setChecked(true);
            btnLikeSong.setShapeResource(R.drawable.ic_heart);
            btnLikeSong.setBtnColor(Color.RED);
            btnLikeSong.setBtnFillColor(Color.RED);
            btnLikeSong.setColorFilter(Color.RED);
            btnLikeSong.setTag(LIKED);
        }else{
            btnLikeSong.setChecked(false);
            btnLikeSong.setShapeResource(R.drawable.ic_heart_outline);
            btnLikeSong.setColorFilter(Color.WHITE);
            btnLikeSong.setBtnColor(Color.WHITE);
            btnLikeSong.setBtnFillColor(Color.WHITE);
            btnLikeSong.setTag(NOT_LIKED);
        }
    }

    @Override
    public void onLikedTracksReceived(List<Track> likedTracks) {
        updateData(likedTracks);
    }

    @Override
    public void onNoLikedTracks(Throwable noLikedTracks) {

    }

    @Override
    public void onArtistTracksReceived(List<Track> artistTracks) {

        updateData(artistTracks);
    }

    @Override
    public void onUserSpecificLikedPlaylistReceived(Playlist specificPlaylist) {
        this.playlistName = specificPlaylist.getName();
        updateData(specificPlaylist.getTracks());
    }

}
