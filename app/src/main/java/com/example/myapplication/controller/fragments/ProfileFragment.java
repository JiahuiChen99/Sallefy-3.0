package com.example.myapplication.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.UserPlaylistAdapter;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;
import com.example.myapplication.utils.Sesion;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class ProfileFragment extends Fragment implements PlaylistCallback {

    private String user;
    private RecyclerView mArtistSongsRecyclerView;
    private RecyclerCoverFlow mArtistAlbumsRecyclerView;
    private ArrayList<Playlist> mAlbums;
    private ArrayList<Track> mSongs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mArtistAlbumsRecyclerView = (RecyclerCoverFlow) view.findViewById(R.id.profile_artist_albums);
        CoverFlowLayoutManger artistAlbumsManager = new CoverFlowLayoutManger(false, false, true, (float) 1);
        UserPlaylistAdapter artistAlbumsAdapter = new UserPlaylistAdapter(getContext(), null);
        mArtistAlbumsRecyclerView.setLayoutManager(artistAlbumsManager);
        mArtistAlbumsRecyclerView.setAdapter(artistAlbumsAdapter);

        this.user = Sesion.getInstance(getContext()).getUser().getLogin();
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
}
