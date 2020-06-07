package com.example.myapplication.controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.SearchPlaylistAdapter;
import com.example.myapplication.controller.music.MusicCallback;
import com.example.myapplication.model.Followed;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.SearchResult;
import com.example.myapplication.model.Track;
import com.example.myapplication.model.User;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.SearchCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;
import com.example.myapplication.restapi.manager.SearchManager;

import java.util.ArrayList;
import java.util.List;

public class SearchPlaylistsFragment extends Fragment implements SearchCallback, PlaylistCallback {

    private ArrayList<Track> tracks;
    private ArrayList<Playlist> playlists;
    private RecyclerView msongListRecyclerView;
    private SearchPlaylistsFragment instance;
    private String input;

    private MusicCallback sendTracksCallback;

    public SearchPlaylistsFragment(String input) {
        this.input = input;
    }

    public static SearchPlaylistsFragment getInstance(String input){
        return new SearchPlaylistsFragment(input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_songs, container, false);

        msongListRecyclerView = (RecyclerView) v.findViewById(R.id.search_songs);
        LinearLayoutManager songsListManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        SearchPlaylistAdapter userPlaylistAdapter = new SearchPlaylistAdapter( getContext(), null, this);
        msongListRecyclerView.setLayoutManager(songsListManager);
        msongListRecyclerView.setAdapter(userPlaylistAdapter);

        if (input == null) {
            getData();
        }else if (input.equals("")) {
            getData();
        }else {
            updateSongs(input);
        }

        return v;
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

    public void updateSongs(String input) {
        System.out.println(input);
        SearchManager.getInstance(this.getActivity()).searchSong(input,this);
    }

    public void getData(){
        PlaylistManager.getInstance(this.getActivity()).getAllPlaylists("0", this);
    }

    @Override
    public void onInfoReceived(SearchResult output) {
        this.playlists = (ArrayList)output.getPlaylists();
        SearchPlaylistAdapter adapter = new SearchPlaylistAdapter(getContext(), playlists, this);
        msongListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoInfo(Throwable throwable) {

    }

    @Override
    public void onPlaylistReceived(List<Playlist> playlists) {
        this.playlists = (ArrayList) playlists;
        SearchPlaylistAdapter adapter = new SearchPlaylistAdapter(getContext(), this.playlists, this);
        msongListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onPlaylistSelected(Integer id, String sectionId) {
        ArrayList<Playlist> pAux = new ArrayList<>();
        for (Playlist p : playlists) {
            if (p.getId().equals(id)) pAux.add(0, p);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("playlist", pAux);
        Fragment fragment = new PlaylistSongsFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.search_layout, fragment).commit();
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
    public void onErrorFollow(Throwable throwable) {

    }

    @Override
    public void onFollowReceived(Followed follow) {

    }
}
