package com.example.myapplication.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.controller.adapters.UserPlaylistAdapter;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;
import com.example.myapplication.utils.Sesion;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class ProfileFragment extends Fragment implements PlaylistCallback, TrackCallback {

    private String user;
    private RecyclerView mArtistSongsRecyclerView;
    private RecyclerCoverFlow mArtistAlbumsRecyclerView;
    private RecyclerView mArtistAlbumSongsRecyclerView;
    private ArrayList<Playlist> mAlbums;
    private ArrayList<Track> mAlbumSongs;
    private Integer playlistID = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mArtistAlbumsRecyclerView = (RecyclerCoverFlow) view.findViewById(R.id.profile_artist_albums);
        CoverFlowLayoutManger artistAlbumsManager = new CoverFlowLayoutManger(false, false, true, (float) 1);
        UserPlaylistAdapter artistAlbumsAdapter = new UserPlaylistAdapter(getContext(), null);
        mArtistAlbumsRecyclerView.setLayoutManager(artistAlbumsManager);
        mArtistAlbumsRecyclerView.setAdapter(artistAlbumsAdapter);
        mArtistAlbumsRecyclerView.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                playlistID = position;
                TrackListAdapter albumSongsListAdapter = new TrackListAdapter(ProfileFragment.this, getContext(), (ArrayList<Track>) mAlbums.get(position).getTracks());
                mArtistAlbumSongsRecyclerView.setAdapter(albumSongsListAdapter);
            }
        });

        mArtistAlbumSongsRecyclerView = (RecyclerView) view.findViewById(R.id.profile_artist_albums_songs);
        LinearLayoutManager artistAlbumsSongsManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mArtistAlbumSongsRecyclerView.setLayoutManager(artistAlbumsSongsManager);

        /*mArtistSongsRecyclerView =(RecyclerView) view.findViewById(R.id.profile_artist_songs);
        LinearLayoutManager artistSongsManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mArtistSongsRecyclerView.setLayoutManager(artistAlbumsManager);*/

        this.user = Sesion.getInstance(getContext()).getUser().getLogin();
        System.out.println("UserName: " + this.user);
        getData();
        return view;
    }

    private void getData(){
        PlaylistManager.getInstance(this.getActivity()).getSpecificUserPlaylists(user,this);
        mAlbums = new ArrayList<>();
    }

    @Override
    public void onPlaylistReceived(Playlist playlists) {

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
    public void onUserPlaylistsReceived(List<Playlist> playlists) {
        mAlbums = (ArrayList) playlists;
        UserPlaylistAdapter adapter = new UserPlaylistAdapter(getContext(), mAlbums);
        mArtistAlbumsRecyclerView.setAdapter(adapter);

        TrackListAdapter albumSongsListAdapter = new TrackListAdapter(ProfileFragment.this, getContext(), (ArrayList<Track>) mAlbums.get(0).getTracks());
        mArtistAlbumSongsRecyclerView.setAdapter(albumSongsListAdapter);
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
    public void onFailure(Throwable throwable) {

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
}
