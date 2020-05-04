package com.example.myapplication.controller.fragments;

import android.content.Context;
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
import com.example.myapplication.controller.adapters.SearchPlaylistAdapter;
import com.example.myapplication.controller.adapters.SearchUsersAdapter;
import com.example.myapplication.controller.music.MusicCallback;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.SearchResult;
import com.example.myapplication.model.Track;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.SearchCallback;
import com.example.myapplication.restapi.callback.UserCallback;
import com.example.myapplication.restapi.callback.UserResourcesCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;
import com.example.myapplication.restapi.manager.SearchManager;
import com.example.myapplication.restapi.manager.UserManager;
import com.example.myapplication.restapi.manager.UserResourcesManager;

import java.util.ArrayList;
import java.util.List;

public class SearchArtistFragment extends Fragment implements SearchCallback, UserResourcesCallback {

    private ArrayList<User> users;
    private RecyclerView msongListRecyclerView;
    private SearchArtistFragment instance;
    private String input;

    private MusicCallback sendTracksCallback;

    public SearchArtistFragment(String input) {
        this.input = input;
    }

    public static SearchArtistFragment getInstance(String input){
        return new SearchArtistFragment(input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_songs, container, false);

        msongListRecyclerView = (RecyclerView) v.findViewById(R.id.search_songs);
        LinearLayoutManager songsListManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        SearchPlaylistAdapter userPlaylistAdapter = new SearchPlaylistAdapter( getContext(), null);
        msongListRecyclerView.setLayoutManager(songsListManager);
        msongListRecyclerView.setAdapter(userPlaylistAdapter);


            getData(input);


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
        UserResourcesManager.getInstance(this.getActivity()).getSpecificUser(input,this);
    }

    public void getData(String input){
        SearchManager.getInstance(this.getActivity()).searchSong(input,this);
    }

    @Override
    public void onInfoReceived(SearchResult output) {
        this.users = (ArrayList)output.getUsers();
        SearchUsersAdapter adapter = new SearchUsersAdapter(getContext(), this.users, this);
        msongListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoInfo(Throwable throwable) {

    }

    @Override
    public void onUserReceived(User user) {
        SearchUsersAdapter adapter = new SearchUsersAdapter(getContext(), user, this);
        msongListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onUsersReceived(List<User> users) {

    }

    @Override
    public void onNoUsers(Throwable throwable) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onArtistsReceived(List<User> users) {
        this.users = (ArrayList) users;
        SearchUsersAdapter adapter = new SearchUsersAdapter(getContext(), this.users, this);
        msongListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoUserReceived(Throwable throwable) {

    }

    @Override
    public void onFollowingArtistsReceived(List<User> followingArtists) {

    }

    @Override
    public void onNoFollowingArtists(Throwable noFollowingArtists) {

    }

    @Override
    public void onUserFollowingReceived(List<User> followingArtists) {

    }

    @Override
    public void onNoUserFollowing(Throwable noFollowingArtists) {

    }

    @Override
    public void onUserFollowersReceived(List<User> followers) {

    }

    @Override
    public void onNoUserFollowers(Throwable noFollowers) {

    }

    @Override
    public void onUserFollowedUnfollowed(User user) {

    }

    @Override
    public void onNoUserFollowedUnfollowed(Throwable throwable) {

    }

    @Override
    public void onArtistClicked(User clickedArtist) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("artist", clickedArtist);
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.search_layout, profileFragment).commit();
    }
}
