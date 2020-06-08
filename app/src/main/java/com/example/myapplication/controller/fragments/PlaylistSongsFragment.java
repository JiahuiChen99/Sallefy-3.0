package com.example.myapplication.controller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.controller.music.MusicCallback;
import com.example.myapplication.model.Followed;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;

import java.util.ArrayList;
import java.util.List;

public class PlaylistSongsFragment extends Fragment implements PlaylistCallback, TrackCallback {

    private RecyclerView mRecyclerView;
    private TextView tvName;
    private Button bFollow;
    private ArrayList<Track> tracks;
    private Playlist playlist;
    private boolean mIsBound;

    private PlaylistManager mPlaylistManager;
    private MusicCallback sendTracksCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_songs, container, false);

        Bundle bundle = getArguments();
        playlist = (Playlist) bundle.getParcelableArrayList("playlist").get(0);
        tracks = (ArrayList<Track>) playlist.getTracks();

        tvName = (TextView) view.findViewById(R.id.playlist_name);
        tvName.setText(playlist.getName());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.playlist_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, getContext(), tracks);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        bFollow = (Button) view.findViewById(R.id.follow_playlist);
        checkFollow();
        bFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFollow();
            }
        });

        return view;
    }

    private void updateFollow() {
        mPlaylistManager.followPlaylist(playlist.getId(), this);
    }

    private void checkFollow() {
        mPlaylistManager = new PlaylistManager(getContext());
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
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            sendTracksCallback = (MusicCallback) context;
        }catch (ClassCastException e){
            System.out.println("Error, class doesn't implement the interface");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        sendTracksCallback = null;
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
    public void onPlaylistModified(Playlist playlist) {

    }
}
