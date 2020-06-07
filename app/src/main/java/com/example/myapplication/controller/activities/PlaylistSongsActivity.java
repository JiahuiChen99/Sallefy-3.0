package com.example.myapplication.controller.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.controller.music.MusicCallback;
import com.example.myapplication.controller.music.MusicService;
import com.example.myapplication.model.Followed;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;

import java.util.ArrayList;
import java.util.List;

public class PlaylistSongsActivity extends AppCompatActivity implements PlaylistCallback, TrackCallback, MusicCallback {

    private RecyclerView mRecyclerView;
    private TextView tvName;
    private Button bFollow;
    private ArrayList<Track> tracks;
    private Playlist playlist;
    private boolean mIsBound;

    private PlaylistManager mPlaylistManager;
    private MusicCallback sendTracksCallback;

    private MusicService musicService;
    private ServiceConnection mConnection = new ServiceConnection() {

        /************************************************************************
         * This is called when the connection with the service has been stablished,
         * giving us the service object we can use to interact with the service
         ************************************************************************/
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MusicBinder)service).getService();
            musicService.setCallback(PlaylistSongsActivity.this);
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

    @Override
    public void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_see_songs);
        initViews();
    }

    private void initViews () {

        Intent i = getIntent();
        playlist = (Playlist) i.getSerializableExtra("Playlist");
        tracks = (ArrayList<Track>) playlist.getTracks();
        tvName = (TextView) findViewById(R.id.playlist_name);
        tvName.setText(playlist.getName());

        mRecyclerView = (RecyclerView) findViewById(R.id.playlist_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, this, tracks);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        bFollow = (Button) findViewById(R.id.follow_playlist);
        checkFollow();
        bFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFollow();
            }
        });
        sendTracksCallback = this;

        initService();
    }

    private void initService() {
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

    private void updateFollow() {
        mPlaylistManager.followPlaylist(playlist.getId(), this);
    }

    private void checkFollow() {
        mPlaylistManager = new PlaylistManager(this);
        mPlaylistManager.checkIfFollow(this, playlist.getId());
    }

    @Override
    public void onErrorFollow(Throwable throwable) {

    }

    @Override
    public void onFollowReceived(Followed follow) {
        if(follow.getFollowed().equalsIgnoreCase("true")){
            bFollow.setText("UNFOLLOW");
        } else bFollow.setText("FOLLOW");
    }

    @Override
    public void onPlaylistReceived(List<Playlist> playlists) {

    }

    @Override
    public void onNoPlaylists(Throwable throwable) {

    }

    @Override
    public void onPlaylistCreated(Playlist playlist) {

    }

    @Override
    public void onPlaylistFailure(Throwable throwable) {

    }

    @Override
    public void onPlaylistSelected(Integer id, String sectionId) {

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

    @Override
    public void onTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onNoTracks(Throwable throwable) {

    }

    @Override
    public void onRecentTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onNoRecentTracksReceived(Throwable throwable) {

    }

    @Override
    public void onRecommendedTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onNoRecommendedTracks(Throwable throwable) {

    }

    @Override
    public void onSpecificTrackReceived(Track track) {

    }

    @Override
    public void onNoSpecificTrack(Track track) {

    }

    @Override
    public void onTrackSelected(Integer id, String sectionID) {
        sendTracksCallback.setTracks(tracks, id);
    }

    @Override
    public void onTrackLiked(Track like) {

    }

    @Override
    public void onLikedTracksReceived(List<Track> likedTracks) {

    }

    @Override
    public void onNoLikedTracks(Throwable noLikedTracks) {

    }

    @Override
    public void onArtistTracksReceived(List<Track> artistTracks) {

    }

    @Override
    public void onNoArtistTracks(Throwable noArtistTracks) {

    }

    @Override
    public void onTrackUploaded(Track uploadedTrack) {

    }

    @Override
    public void onNoTrackUploaded(Throwable notUploaded) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onMusicPlayerPrepared() {

    }

    @Override
    public void setTracks(ArrayList<Track> playlist, int currentTrack) {
        musicService.setTracks(playlist, currentTrack);
    }

    @Override
    public void setTrack(Track song) {

    }

    @Override
    public void updatePlayPauseButton(boolean playing) {

    }

    @Override
    public void updateLikeButton(boolean liked) {

    }

    @Override
    public void updateSongTitle(String title) {

    }

    @Override
    public void updateArtist(String artistName) {

    }

    @Override
    public void updateSongImage(String imageURL) {

    }

    @Override
    public void updateSeekBar(int currentPosition) {

    }

    @Override
    public void updateMaxSeekBar(int currentPosition) {

    }

    @Override
    public void showShrinkedBar(boolean show) {

    }

    @Override
    public void setSongID(int songID) {

    }
}
