package com.example.myapplication.controller.music;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import android.os.PowerManager;
import com.example.myapplication.model.Track;

import java.io.IOException;
import java.util.ArrayList;


public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{

    private MediaPlayer mediaPlayer;
    private final IBinder mBinder = new MusicBinder();

    private ArrayList<Track> mTracks;
    private Track song;
    private int currentTrack;

    private MusicCallback callback;

    public void setCallback(MusicCallback callback){
        this.callback = callback;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    public class MusicBinder extends Binder {
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        mediaPlayer.release();
        stopSelf();
        onDestroy();
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        currentTrack = 0;
        mediaPlayer = new MediaPlayer();

        this.callback = callback;

        initMediaPlayer();
    }

    private void initMediaPlayer(){
        // Let playback continue when the device continue idle
        //mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        //Interface Implemented in the Class
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    public void setTracks(ArrayList<Track> tracks, int currentTrack){
        mTracks = tracks;
        this.currentTrack = currentTrack;
        updateTrackUI();
        playSong();
    }


    public ArrayList<Track> getTracks(){
        return mTracks;
    }

    public void setTrack(Track song){
        this.song = song;
    }

    public Track getTrack(){
        return song;
    }

    public void playSong() {
        mediaPlayer.reset();
        //Fetch the song buffer it and play it
        Track track = mTracks.get(currentTrack);
        try {
            mediaPlayer.setDataSource(track.getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                callback.updateMaxSeekBar(mediaPlayer.getDuration());
                playAudio();
            }
        });
    }

    public void updateTrack(int offset) {
        currentTrack = ((currentTrack+offset)%(mTracks.size()));
        currentTrack = currentTrack >= mTracks.size() ? 0:currentTrack;
        currentTrack = currentTrack < 0 ? (mTracks.size()-1):currentTrack;


        String newUrl = mTracks.get(currentTrack).getUrl();
        try {
            playSong();
        } catch(Exception e) {
        }

        //Update view
        updateTrackUI();
    }

    public void updateTrackUI(){
        setTrack(mTracks.get(currentTrack));
        callback.setSongID(currentTrack);
        callback.showShrinkedBar(true);
        callback.updateLikeButton(mTracks.get(currentTrack).isLiked());
        callback.updateSongTitle(mTracks.get(currentTrack).getName());
        callback.updateSongImage(mTracks.get(currentTrack).getThumbnail());
        callback.updateSeekBar(mediaPlayer.getCurrentPosition());
        callback.updateArtist(mTracks.get(currentTrack).getUserLogin());
    }


    public void seekTo(int newPosition){
        mediaPlayer.seekTo(newPosition);
    }

    public void previousSong(){
        //TODO: Update View
        currentTrack -= 1;
    }

    public void nextSong(){
        currentTrack += 1;
    }

    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }


    public Track getCurrentTrack() {
        return mTracks.size() > 0 ? mTracks.get(currentTrack):null;
    }



    public void playAudio() {
        mediaPlayer.start();
        callback.updateSeekBar(mediaPlayer.getCurrentPosition());
        callback.updatePlayPauseButton(true);
    }

    public void pauseAudio() {
        mediaPlayer.pause();
        callback.updateSeekBar(mediaPlayer.getCurrentPosition());
        callback.updatePlayPauseButton(false);
    }

}
